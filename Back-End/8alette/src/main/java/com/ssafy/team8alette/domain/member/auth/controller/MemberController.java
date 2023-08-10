package com.ssafy.team8alette.domain.member.auth.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberAuthService;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
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
	private final JwtTokenProvider jwtTokenProvider;

	@LoginRequired
	@GetMapping
	public ResponseEntity<Map<String, Object>> getMemberInfoRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@RequestParam Optional<String> nickname,
		@RequestParam Optional<String> name,
		@RequestParam Optional<String> email,
		@RequestParam Optional<String> intro,
		@RequestParam Optional<String> state,
		@RequestParam Optional<String> type,
		@RequestParam Optional<String> emailReceive) throws ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);

		Member member = memberService.getMemberInfo(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		if (nickname.isEmpty() && email.isEmpty() && intro.isEmpty()
			&& state.isEmpty() && name.isEmpty() && type.isEmpty() && emailReceive.isEmpty()) {

			data.put("member_nickname", member.getMemberNickname());
			data.put("member_email", member.getMemberEmail());
			data.put("member_intro", member.getMemberIntro());
			data.put("member_state", member.getMemberState());
			data.put("member_name", member.getMemberName());
			data.put("member_type", member.getMemberType());
			data.put("member_emailReceive", member.isEmailReceive());

		} else {

			if (nickname.orElse("N").equals("Y")) {
				data.put("member_nickname", member.getMemberNickname());
			}
			if (name.orElse("N").equals("Y")) {
				data.put("member_name", member.getMemberName());
			}
			if (email.orElse("N").equals("Y")) {
				data.put("member_email", member.getMemberEmail());
			}
			if (intro.orElse("N").equals("Y")) {
				data.put("member_intro", member.getMemberIntro());
			}
			if (state.orElse("N").equals("Y")) {
				data.put("member_state", member.getMemberState());
			}
			if (type.orElse("N").equals("Y")) {
				data.put("member_type", member.getMemberType());
			}
			if (emailReceive.orElse("N").equals("Y")) {
				data.put("member_emailReceive", member.isEmailReceive());
			}

		}

		responseData.put("message", "success");
		responseData.put("data", data);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> registerMemberRequest(@RequestBody Map<String, String> param) throws
		NoSuchAlgorithmException, IllegalAccessException {

		Long memberNumber = memberService.register(param);

		Member member = memberService.getMemberInfo(memberNumber);

		mailSenderUtil.sendVerifyStateMessage(memberNumber, member.getMemberEmail());

		Map<String, Object> responseData = new HashMap<>();

		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PutMapping
	public ResponseEntity<Map<String, Object>> changeMemberInfoRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@RequestBody Map<String, String> param) throws
		IllegalAccessException, ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);

		memberService.editMemberInfo(memberNumber, param);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PutMapping("/deactivation")
	public ResponseEntity<Map<String, Object>> deactivateMemberRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken) throws ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);

		memberService.deactivate(memberNumber);
		memberAuthService.logout(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PostMapping("/password")
	public ResponseEntity<Map<String, Object>> changeMemberPasswordRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@RequestBody Map<String, String> param) throws NoSuchAlgorithmException, ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);

		memberService.changeMemberPassword(memberNumber, param);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
