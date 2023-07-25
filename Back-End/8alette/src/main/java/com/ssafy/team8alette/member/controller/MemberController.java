package com.ssafy.team8alette.member.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

@RequestMapping("/api/v1/member")
@RestController
public class MemberController {

	private final MemberService memberService;
	private final MemberAuthService memberAuthService;

	@Autowired
	public MemberController(
		MemberService memberService,
		MemberAuthService memberAuthService) {

		this.memberService = memberService;
		this.memberAuthService = memberAuthService;
	}

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

		if (name.trim().equals("Y"))
			data.put("member_name", member.getMemberName());
		if (nickname.trim().equals("Y"))
			data.put("member_nickname", member.getMemberNickname());
		if (email.trim().equals("Y"))
			data.put("member_email", member.getMemberEmail());
		if (age.trim().equals("Y"))
			data.put("member_age", member.getMemberAge());
		if (intro.trim().equals("Y"))
			data.put("member_intro", member.getMemberIntro());

		responseData.put("message", "OK");
		HttpStatus status = HttpStatus.ACCEPTED;
		responseData.put("data", data);

		return new ResponseEntity<Map<String, Object>>(responseData, status);
	}

	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> registerMemberRequest(@RequestBody Map<String, String> param) throws
		NoSuchAlgorithmException {

		memberService.register(param);

		Map<String, Object> responseData = new HashMap<>();

		responseData.put("message", "OK");
		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<>(responseData, status);
	}

	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> changeMemberInfoRequest(@RequestBody Map<String, String> param) {

		String memberEmail = param.get("member_email").trim();
		String memberNickname = param.get("member_nickname").trim();
		String memberIntro = param.get("member_intro");
		Long memberNumber = Long.parseLong(param.get("member_number").trim());

		memberService.editMemberInfo(memberNumber, memberEmail, memberNickname, memberIntro);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "OK");
		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<>(responseData, status);
	}

	@PutMapping("/{memberNumber}}")
	public ResponseEntity<Map<String, Object>> deactivateMemberRequest(@PathVariable Long memberNumber) throws
		SQLException {

		memberService.deactivate(memberNumber);
		memberAuthService.logout(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "OK");
		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<>(responseData, status);
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

		responseData.put("message", "OK");
		HttpStatus status = HttpStatus.ACCEPTED;
		responseData.put("data", data);

		return new ResponseEntity<>(responseData, status);
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
		responseData.put("message", "OK");
		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<>(responseData, status);
	}

}
