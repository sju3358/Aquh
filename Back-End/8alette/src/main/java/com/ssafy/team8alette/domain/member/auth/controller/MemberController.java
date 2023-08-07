package com.ssafy.team8alette.domain.member.auth.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberAuthService;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberService;
import com.ssafy.team8alette.domain.member.auth.util.MailSenderUtil;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MemberAuthService memberAuthService;
	private final MailSenderUtil mailSenderUtil;

	@LoginRequired
	@GetMapping("/{memberNumber}")
	public ResponseEntity<Map<String, Object>> getMemberInfoRequest(
		@PathVariable Long memberNumber,
		@RequestParam Optional<String> nickname,
		@RequestParam Optional<String> name,
		@RequestParam Optional<String> email,
		@RequestParam Optional<String> intro,
		@RequestParam Optional<String> state,
		@RequestParam Optional<String> type,
		@RequestParam Optional<String> emailReceive) {

		MemberEntity memberEntity = memberService.getMemberInfo(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		if (nickname.isEmpty() && email.isEmpty() && intro.isEmpty()
			&& state.isEmpty() && name.isEmpty() && type.isEmpty() && emailReceive.isEmpty()) {

			data.put("member_nickname", memberEntity.getMemberNickname());
			data.put("member_email", memberEntity.getMemberEmail());
			data.put("member_intro", memberEntity.getMemberIntro());
			data.put("member_state", memberEntity.getMemberState());
			data.put("member_name", memberEntity.getMemberName());
			data.put("member_type", memberEntity.getMemberType());
			data.put("member_emailReceive", memberEntity.isEmailReceive());

		} else {

			if (nickname.orElse("N").equals("Y")) {
				data.put("member_nickname", memberEntity.getMemberNickname());
			}
			if (name.orElse("N").equals("Y")) {
				data.put("member_name", memberEntity.getMemberName());
			}
			if (email.orElse("N").equals("Y")) {
				data.put("member_email", memberEntity.getMemberEmail());
			}
			if (intro.orElse("N").equals("Y")) {
				data.put("member_intro", memberEntity.getMemberIntro());
			}
			if (state.orElse("N").equals("Y")) {
				data.put("member_state", memberEntity.getMemberState());
			}
			if (type.orElse("N").equals("Y")) {
				data.put("member_type", memberEntity.getMemberType());
			}
			if (emailReceive.orElse("N").equals("Y")) {
				data.put("member_emailReceive", memberEntity.isEmailReceive());
			}

		}

		responseData.put("message", "success");
		responseData.put("data", data);
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> registerMemberRequest(@RequestBody Map<String, String> param) throws
		NoSuchAlgorithmException, IllegalAccessException {

		Long memberNumber = memberService.register(param);

		MemberEntity memberEntity = memberService.getMemberInfo(memberNumber);

		mailSenderUtil.sendVerifyStateMessage(memberNumber, memberEntity.getMemberEmail());

		Map<String, Object> responseData = new HashMap<>();

		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PutMapping
	public ResponseEntity<Map<String, Object>> changeMemberInfoRequest(@RequestBody Map<String, String> param) throws
		IllegalAccessException {

		memberService.editMemberInfo(param);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
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

	@LoginRequired
	@PostMapping("/password")
	public ResponseEntity<Map<String, Object>> changeMemberPasswordRequest(
		@RequestBody Map<String, String> param) throws
		NoSuchAlgorithmException {

		memberService.changeMemberPassword(param);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
