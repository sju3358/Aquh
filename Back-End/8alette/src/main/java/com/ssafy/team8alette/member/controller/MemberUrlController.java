package com.ssafy.team8alette.member.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.member.model.service.MemberService;
import com.ssafy.team8alette.member.util.MailSenderUtil;
import com.ssafy.team8alette.member.util.PasswordUtil;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member/url")
@RestController
@RequiredArgsConstructor
public class MemberUrlController {

	private final MemberService memberService;
	private final MailSenderUtil mailSenderUtil;
	private final PasswordUtil passwordUtil;

	@PostMapping("/password")
	public ResponseEntity<Map<String, Object>> findPasswordRequest(
		@RequestBody Map<String, String> param) throws NoSuchAlgorithmException, IllegalAccessException {

		String memberEmail = param.get("member_email").trim();
		String newTempPassword = passwordUtil.getRandomPassword();
		memberService.changeMemberPassword(memberEmail, newTempPassword);

		mailSenderUtil.sendTempPasswordEmail(memberEmail, newTempPassword);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@GetMapping("/email-certification")
	public ResponseEntity<Map<String, Object>> verifyMemberRequest(
		@RequestParam Long member_number) {

		Long memberNumber = member_number;
		memberService.verifyMember(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
