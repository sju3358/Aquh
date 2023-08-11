package com.ssafy.team8alette.domain.member.auth.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.member.auth.model.dto.MemberLoginInfo;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberAuthService;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.global.annotation.LoginRequired;
import com.ssafy.team8alette.global.exception.InvalidTokenException;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member/auth")
@RestController
@RequiredArgsConstructor
public class MemberAuthController {

	private final MemberAuthService memberAuthService;
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping
	public ResponseEntity<Map<String, Object>> loginRequest(@RequestBody Map<String, String> param) throws
		SQLException,
		UnsupportedEncodingException, NoSuchAlgorithmException {

		String memberEmail = param.get("member_email");
		String memberPassword = param.get("member_password");
		Long memberNumber = memberAuthService.loginCheck(memberEmail, memberPassword);

		Map<String, Object> loginData = new HashMap<>();
		Map<String, Object> tokens = jwtTokenProvider.getTokens(memberNumber);

		String accessToken = tokens.get("accessToken").toString();
		String refreshToken = tokens.get("refreshToken").toString();
		memberAuthService.login(memberNumber, refreshToken);

		loginData.put("member_number", memberNumber);
		loginData.put("access_token", accessToken);
		loginData.put("refresh_token", refreshToken);
		loginData.put("isSocialLogin", false);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("data", loginData);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@DeleteMapping
	ResponseEntity<Map<String, Object>> logoutRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken) throws SQLException, ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);

		memberAuthService.logout(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PostMapping("/check_token")
	public ResponseEntity<Map<String, Object>> checkTokenRequest() {

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PostMapping("/refresh_token")
	public ResponseEntity<Map<String, Object>> refreshTokenRequest(
		@RequestHeader(value = "AUTH-TOKEN") String refreshToken) throws
		SQLException, UnsupportedEncodingException, ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(refreshToken);

		MemberLoginInfo memberLoginInfo = memberAuthService.getLoginMemberInfo(memberNumber);

		System.out.println("현재 토큰 : " + memberLoginInfo.getRefreshToken());

		if (memberLoginInfo.getRefreshToken().equals(refreshToken) != true) {
			throw new InvalidTokenException("리프레쉬 토큰이 일치하지 않습니다");
		}

		Map<String, Object> tokens = jwtTokenProvider.getTokens(memberNumber);

		String newAccessToken = tokens.get("accessToken").toString();
		String newRefreshToken = tokens.get("refreshToken").toString();

		memberAuthService.refreshToken(memberNumber, newRefreshToken);

		Map<String, Object> newTokens = new HashMap<>();

		newTokens.put("access_token", newAccessToken);
		newTokens.put("refresh_token", newRefreshToken);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("data", newTokens);

		System.out.println("토큰 재발급 : " + newRefreshToken);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
