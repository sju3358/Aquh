package com.ssafy.team8alette.member.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.member.model.service.MemberAuthService;
import com.ssafy.team8alette.member.model.service.MemberService;
import com.ssafy.team8alette.member.util.MailSenderUtil;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MemberAuthService memberAuthService;
	private final MailSenderUtil mailSenderUtil;

	@GetMapping("/{memberNumber}")
	public ResponseEntity<Map<String, Object>> getMemberInfoRequest(
		@PathVariable Long memberNumber,
		@RequestParam String name,
		@RequestParam String nickname,
		@RequestParam String email,
		@RequestParam String age,
		@RequestParam String intro) {

		Member member = memberService.getMemberInfo(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		data.put("member_state", member.getMemberState());

		if (name.trim().equals("Y")) {
			data.put("member_name", member.getMemberName());
		}
		if (nickname.trim().equals("Y")) {
			data.put("member_nickname", member.getMemberNickname());
		}
		if (email.trim().equals("Y")) {
			data.put("member_email", member.getMemberEmail());
		}
		if (age.trim().equals("Y")) {
			data.put("member_age", member.getMemberAge());
		}
		if (intro.trim().equals("Y")) {
			data.put("member_intro", member.getMemberIntro());
		}

		responseData.put("message", "success");
		responseData.put("data", data);
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> registerMemberRequest(@RequestBody Map<String, String> param) throws
		NoSuchAlgorithmException, IllegalAccessException {

		String memberEmail = param.get("member_email").trim();
		String memberNickname = param.get("member_nickname").trim();
		String memberPassword = param.get("member_password").trim();
		String memberPasswordRepeat = param.get("member_password_repeat").trim();

		Long memberNumber = memberService.register(
			memberEmail,
			memberNickname,
			memberPassword,
			memberPasswordRepeat
		);

		mailSenderUtil.sendVerifyEmailMessage(memberNumber, memberEmail);

		Map<String, Object> responseData = new HashMap<>();

		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> changeMemberInfoRequest(@RequestBody Map<String, String> param) {

		String memberEmail = param.get("member_email").trim();
		String memberNickname = param.get("member_nickname").trim();
		String memberIntro = param.get("member_intro");
		Long memberNumber = Long.parseLong(param.get("member_number").trim());

		memberService.editMemberInfo(memberNumber, memberEmail, memberNickname, memberIntro);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PutMapping("/{memberNumber}")
	public ResponseEntity<Map<String, Object>> deactivateMemberRequest(@PathVariable Long memberNumber) throws
		SQLException {

		memberService.deactivate(memberNumber);
		memberAuthService.logout(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PostMapping("/check")
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

	@PostMapping("/password")
	public ResponseEntity<Map<String, Object>> changeMemberPasswordRequest(
		@RequestBody Map<String, String> param) throws
		NoSuchAlgorithmException {

		Long memberNumber = Long.parseLong(param.get("member_number").trim());
		String memberNewPassword = param.get("new_password").trim();
		String memberNewPasswordRepeat = param.get("new_password_repeat");

		memberService.changeMemberPassword(memberNumber, memberNewPassword, memberNewPasswordRepeat);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
