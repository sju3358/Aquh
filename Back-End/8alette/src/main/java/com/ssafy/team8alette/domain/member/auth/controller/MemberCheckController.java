package com.ssafy.team8alette.domain.member.auth.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.member.auth.model.service.MemberService;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member/check")
@RestController
@RequiredArgsConstructor
public class MemberCheckController {

	private final MemberService memberService;

	@LoginRequired
	@PostMapping("/match")
	public ResponseEntity<Map<String, Object>> checkMemberInfoRequest(@RequestBody Map<String, String> param) throws
		NoSuchAlgorithmException {

		String memberEmail = param.get("member_email").trim();
		String memberPassword = param.get("member_password").trim();

		boolean isValid = memberService.checkValid(memberEmail, memberPassword);
		Map<String, Object> data = new HashMap<>();
		data.put("isValid", isValid);

		Map<String, Object> responseData = new HashMap<>();

		responseData.put("message", "success");
		responseData.put("data", data);

		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}

	@PostMapping("/email")
	public ResponseEntity<Map<String, Object>> memberEmailDuplicatedCheckRequest(
		@RequestBody Map<String, String> param) {

		String memberEmail = param.get("member_email").trim();

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("isExistSameEmail", memberService.isExistMemberId(memberEmail));

		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}

	@PostMapping("/nickname")
	public ResponseEntity<Map<String, Object>> memberNicknameDuplicatedCheckRequest(
		@RequestBody Map<String, String> param) {

		String memberNickname = param.get("member_nickname").trim();

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("isExistSameNickname", memberService.isExistMemberNickname(memberNickname));

		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}

}
