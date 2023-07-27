package com.ssafy.team8alette.member.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member/check")
@RestController
@RequiredArgsConstructor
public class MemberCheckController {

	private final MemberService memberService;

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
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}

	@PostMapping("/email")
	public ResponseEntity<Map<String, Object>> memberEmailDuplicatedCheckRequest(
		@RequestBody Map<String, String> param) {

		String memberEmail = param.get("member_email").trim();

		boolean isExistSameEmail;
		if (memberService.getMemberInfo(memberEmail) == null) {
			isExistSameEmail = true;
		} else {
			isExistSameEmail = false;
		}
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("isExistSameEmail", isExistSameEmail);
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}

	@PostMapping("/nickname")
	public ResponseEntity<Map<String, Object>> memberNicknameDuplicatedCheckRequest(
		@RequestBody Map<String, String> param) {

		String memberNickname = param.get("member_nickname").trim();

		boolean isExistSameNickname;
		if (memberService.getMemberInfoByNickname(memberNickname) == null) {
			isExistSameNickname = true;
		} else {
			isExistSameNickname = false;
		}
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("isExistSameNickname", isExistSameNickname);
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);

	}

}
