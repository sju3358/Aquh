package com.ssafy.team8alette.domain.member.auth.controller;

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

import com.ssafy.team8alette.domain.member.auth.model.service.MemberAuthGoogleService;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member/auth/google")
@RestController
@RequiredArgsConstructor
public class MemberAuthGoogleController {

	private final String googleClientId = "953911532873-0ve3ob0gtc2eq0fdp8ui67mue02pufpr.apps.googleusercontent.com";
	private final String googleClientSecret = "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";

	private final MemberAuthGoogleService memberAuthGoogleService;
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping
	public ResponseEntity<Map<String, Object>> googleLoginRequest(@RequestBody Map<String, String> param) throws
		Exception {

		String code = param.get("code");

		String jsonData = requestAccessToken(generateAuthCodeRequest(code)).getBody();
		Map<String, String> tokenInfo = getTokenInfo(jsonData);
		String googleAccessToken = tokenInfo.get("accessToken");
		String googleIdToken = tokenInfo.get("idToken");

		ResponseEntity<String> profileInfo = requestProfile(googleAccessToken, googleIdToken);

		JSONParser parser = new JSONParser();
		JSONObject profileData = (JSONObject)parser.parse(profileInfo.getBody());

		String googleResponseData = profileData.toString();
		JSONObject googleMemberData = (JSONObject)parser.parse(googleResponseData);

		Long memberNumber = memberAuthGoogleService.register(googleMemberData);

		Map tokens = jwtTokenProvider.getTokens(memberNumber);
		String accessToken = tokens.get("accessToken").toString();
		String refreshToken = tokens.get("refreshToken").toString();
		memberAuthGoogleService.login(memberNumber, refreshToken);

		Map<String, Object> loginData = new HashMap<>();
		loginData.put("member_number", memberNumber);
		loginData.put("access_token", accessToken);
		loginData.put("refresh_token", refreshToken);
		loginData.put("isSocialLogin", true);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("data", loginData);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	private Map<String, String> getTokenInfo(String json) throws ParseException {
		Map<String, String> tokens = new HashMap<>();

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(json);

		tokens.put("accessToken", jsonObject.get("access_token").toString());
		tokens.put("idToken", jsonObject.get("id_token").toString());
		return tokens;
	}

	private HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", googleClientId);
		params.add("client_secret", googleClientSecret);
		params.add("code", code);
		params.add("redirect_uri", "https://i9b108.p.ssafy.io/redirectG");
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

	public ResponseEntity<String> requestProfile(String accessToken, String idToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + idToken);

		HttpEntity request = new HttpEntity(headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(
			"https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken,
			HttpMethod.GET,
			request,
			String.class
		);

		return response;
	}

}
