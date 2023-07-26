package com.ssafy.team8alette.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ssafy.team8alette.member.exception.NullValueException;
import com.ssafy.team8alette.member.model.service.MemberAuthNaverService;
import com.ssafy.team8alette.member.model.service.MemberService;
import com.ssafy.team8alette.member.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member/auth/naver")
@RestController
@RequiredArgsConstructor
public class MemberAuthNaverController {

	private final String CLIENT_ID = "0jXgPVDyLQu_ekRssB20";
	private final String CLIENT_SECRET = "3MjVF30KIu";

	private final MemberAuthNaverService memberNaverService;
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/auth")
	public ResponseEntity<Map<String, Object>> naverLoginRequest(@RequestBody Map<String, String> param) throws
		Exception {

		System.out.println("네이버로그인");

		String code = param.get("code").toString();
		String state = param.get("state").toString();

		HttpEntity<MultiValueMap<String, String>> AuthCodeRequest = generateAuthCodeRequest(code, state);
		ResponseEntity<String> AuthCodeResponse = requestAccessToken(AuthCodeRequest);
		String jsonData = AuthCodeResponse.getBody().toString();
		Map<String, String> tokenInfo = getTokenInfo(jsonData);
		String NaverAccessToken = tokenInfo.get("access_token");
		HttpEntity<MultiValueMap<String, String>> profileRequest = generateProfileRequest(NaverAccessToken);
		ResponseEntity<String> profileInfo = requestProfile(profileRequest);

		JSONParser parser = new JSONParser();
		JSONObject profileData = (JSONObject)parser.parse(profileInfo.getBody().toString());

		String naverResponseData = profileData.get("response").toString();
		JSONObject naverMemberData = (JSONObject)parser.parse(naverResponseData);

		String naverMemberEmail = naverMemberData.get("").toString().trim();
		String naverMemberName = naverMemberData.get("").toString().trim();
		String naverMemberNickname = naverMemberData.get("").toString().trim();
		int naverMemberAge = Integer.parseInt(naverMemberData.get("").toString().trim());

		Long memberNumber = -1L;
		try {
			memberNumber = memberService.getMemberInfo(naverMemberEmail).getMemberNumber();
		} catch (NullValueException exception) {
			memberNaverService.register(
				naverMemberEmail,
				naverMemberName,
				naverMemberNickname,
				naverMemberAge
			);
			memberNumber = memberService.getMemberInfo(naverMemberEmail).getMemberNumber();
		}

		Map tokens = jwtTokenProvider.getTokens(naverMemberEmail);
		String accessToken = tokens.get("accessToken").toString();
		String refreshToken = tokens.get("refreshToken").toString();
		memberNaverService.login(memberNumber, refreshToken);

		Map<String, Object> loginData = new HashMap<>();
		loginData.put("member_number", memberNumber);
		loginData.put("access_token", accessToken);
		loginData.put("refresh_token", refreshToken);
		loginData.put("isSocialLogin", true);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("data", loginData);
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	private Map<String, String> getTokenInfo(String json) throws ParseException {
		Map<String, String> tokens = new HashMap<>();

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(json);

		tokens.put("access_token", jsonObject.get("access_token").toString());
		tokens.put("refresh_token", jsonObject.get("refresh_token").toString());
		tokens.put("expires_in", jsonObject.get("expires_in").toString());
		tokens.put("token_type", jsonObject.get("token_type").toString());
		return tokens;
	}

	private HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String authCode, String state) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);
		params.add("client_secret", CLIENT_SECRET);
		params.add("code", authCode);
		params.add("state", state);
		return new HttpEntity<>(params, headers);
	}

	private ResponseEntity<String> requestAccessToken(HttpEntity request) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange(
			"https://nid.naver.com/oauth2.0/token",
			HttpMethod.POST,
			request,
			String.class
		);
	}

	private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("Authorization", "Bearer " + accessToken);
		return new HttpEntity<>(headers);
	}

	private ResponseEntity<String> requestProfile(HttpEntity request) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange(
			"https://openapi.naver.com/v1/nid/me",
			HttpMethod.GET,
			request,
			String.class
		);
	}

}
