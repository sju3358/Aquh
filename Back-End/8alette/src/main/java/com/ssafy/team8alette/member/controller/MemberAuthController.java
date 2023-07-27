package com.ssafy.team8alette.member.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.common.annotation.LoginRequired;
import com.ssafy.team8alette.member.exception.InvalidTokenException;
import com.ssafy.team8alette.member.model.dto.MemberLoginInfo;
import com.ssafy.team8alette.member.model.service.MemberAuthService;
import com.ssafy.team8alette.member.model.service.MemberService;
import com.ssafy.team8alette.member.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member/auth")
@RestController
@RequiredArgsConstructor
public class MemberAuthController {

	private final MemberAuthService memberAuthService;
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/auth")
	public ResponseEntity<Map<String, Object>> loginRequest(@RequestBody Map<String, String> param) throws
		SQLException,
		UnsupportedEncodingException, NoSuchAlgorithmException {

		String memberEmail = param.get("member_email");
		String memberPassword = param.get("member_password");
		Long memberNumber = memberAuthService.loginCheck(memberEmail, memberPassword);

		Map<String, Object> loginData = new HashMap<>();
		Map<String, Object> tokens = jwtTokenProvider.getTokens(memberEmail);

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
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@DeleteMapping("/auth/{memberNumber}")
	ResponseEntity<Map<String, Object>> logoutRequest(@PathVariable Long memberNumber) throws SQLException {

		memberAuthService.logout(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PostMapping("/refresh")
	public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody Map<String, String> param) throws
		SQLException, UnsupportedEncodingException {

		Long memberNumber = Long.parseLong(param.get("member_number"));
		String refreshToken = param.get("refresh_token");

		MemberLoginInfo memberLoginInfo = memberAuthService.getLoginMemberInfo(memberNumber);

		jwtTokenProvider.checkToken(refreshToken);
		if (memberLoginInfo.getRefreshToken().equals(refreshToken) != true) {
			throw new InvalidTokenException("리프레쉬 토큰이 일치하지 않습니다");
		}

		String memberEmail = memberService.getMemberInfo(memberNumber).getMemberEmail();

		Map<String, Object> tokens = jwtTokenProvider.getTokens(memberEmail);

		String newAccessToken = tokens.get("accessToken").toString();
		String newRefreshToken = tokens.get("refreshToken").toString();
		memberAuthService.login(memberNumber, refreshToken);

		Map<String, Object> loginData = new HashMap<>();
		loginData.put("member_number", memberNumber);
		loginData.put("access_token", newAccessToken);
		loginData.put("refresh_token", newRefreshToken);
		loginData.put("isSocialLogin", memberLoginInfo.isSocialLogin());

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("data", loginData);
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
