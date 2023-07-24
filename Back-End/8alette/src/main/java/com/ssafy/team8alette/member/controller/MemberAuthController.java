package com.ssafy.team8alette.member.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.member.model.service.MemberAuthService;
import com.ssafy.team8alette.member.util.JwtTokenProvider;

@RequestMapping("/api/v1/member/auth")
@RestController
public class MemberAuthController {

	private final MemberAuthService memberAuthService;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public MemberAuthController(
		MemberAuthService memberAuthService,
		JwtTokenProvider jwtTokenProvider) {

		this.memberAuthService = memberAuthService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

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

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "OK");
		responseData.put("data", loginData);
		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Map<String, Object>>(responseData, status);
	}

	@DeleteMapping("/auth/{memberNumber}")
	ResponseEntity<Map<String, Object>> logoutRequest(@PathVariable Long memberNumber) throws SQLException {

		memberAuthService.logout(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "OK");
		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Map<String, Object>>(responseData, status);
	}

}
