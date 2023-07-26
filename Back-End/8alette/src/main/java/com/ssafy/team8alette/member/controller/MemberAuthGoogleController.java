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

@RequestMapping("/api/v1/member/auth/google")
@RestController
@RequiredArgsConstructor
public class MemberAuthGoogleController {

	private final String CLIENT_ID = "953911532873-0ve3ob0gtc2eq0fdp8ui67mue02pufpr.apps.googleusercontent.com";
	private final String CLIENT_SECRET = "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";

	private final MemberAuthNaverService memberNaverService;
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping()
	public ResponseEntity<Map<String, Object>> googleLoginRequest(@RequestBody Map<String, String> param) throws
		Exception {

		String code = param.get("code").toString();
		String redirect_uri = "http://localhost:8080";

		HttpEntity<MultiValueMap<String, String>> AuthCodeRequest = generateAuthCodeRequest(code, redirect_uri);
		ResponseEntity<String> AuthCodeResponse = requestAccessToken(AuthCodeRequest);
		String jsonData = AuthCodeResponse.getBody().toString();
		Map<String, String> tokenInfo = getTokenInfo(jsonData);
		String googleAccessToken = tokenInfo.get("access_token");

		ResponseEntity<String> profileInfo = requestProfile(googleAccessToken);

		JSONParser parser = new JSONParser();
		JSONObject profileData = (JSONObject)parser.parse(profileInfo.getBody().toString());

		String googleResponseData = profileData.get("response").toString();
		JSONObject googleMemberData = (JSONObject)parser.parse(googleResponseData);

		String googleMemberEmail = googleMemberData.get("").toString().trim();
		String googleMemberName = googleMemberData.get("").toString().trim();
		String googleMemberNickname = googleMemberData.get("").toString().trim();
		int googleMemberAge = Integer.parseInt(googleMemberData.get("").toString().trim());

		Long memberNumber = -1L;
		try {
			memberNumber = memberService.getMemberInfo(googleMemberEmail).getMemberNumber();
		} catch (NullValueException exception) {
			memberNaverService.register(
				googleMemberEmail,
				googleMemberName,
				googleMemberNickname,
				googleMemberAge
			);
			memberNumber = memberService.getMemberInfo(googleMemberEmail).getMemberNumber();
		}

		Map tokens = jwtTokenProvider.getTokens(googleMemberEmail);
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
		tokens.put("expires_in", jsonObject.get("expires_in").toString());
		tokens.put("token_type", jsonObject.get("token_type").toString());
		return tokens;
	}

	private HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String authCode, String redirect_uri) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);
		params.add("client_secret", CLIENT_SECRET);
		params.add("code", authCode);
		params.add("redirect_uri", redirect_uri);
		return new HttpEntity<>(params, headers);
	}

	private ResponseEntity<String> requestAccessToken(HttpEntity request) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.exchange(
			"https://oauth2.googleapis.com/token",
			HttpMethod.POST,
			request,
			String.class
		);
	}

	public ResponseEntity<String> requestProfile(String accessToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		System.out.println("Authorization: " + "Bearer " + accessToken);

		HttpEntity request = new HttpEntity(headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(
			"https://www.googleapis.com/oauth2/v3/userinfo",
			HttpMethod.GET,
			request,
			String.class
		);

		System.out.println("response.getBody() = " + response.getBody());
		return response;
	}

}
