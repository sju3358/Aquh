package com.ssafy.team8alette.domain.bubble.tools.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.request.TwoWayAnswerRequestDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.request.TwoWayQuestionRequestDTO;
import com.ssafy.team8alette.domain.bubble.tools.service.TwoWayService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bubble/twoway")
public class TwoWayController {
	private final TwoWayService twoWayService;
	private final JwtTokenProvider jwtTokenProvider;

	// 양자택일 질문 전체 조회
	@LoginRequired
	@GetMapping("/question/{bubble_number}")
	public ResponseEntity<Map<String, Object>> findALlTwoWayQuestions(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable Long bubble_number) throws ParseException {

		Long member_number = jwtTokenProvider.getMemberNumber(jwtToken);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "양자택일 질문 조회 성공");
		responseData.put("status", 200);
		responseData.put("data", twoWayService.getTwoWayQuestions(bubble_number, member_number));

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 양자택일 질문 생성
	@LoginRequired
	@PostMapping("/question")
	public ResponseEntity<Map<String, Object>> createTwoWayQuestion(
		@RequestBody TwoWayQuestionRequestDTO twoWayQuestionRequestDTO) {
		Map<String, Object> responseData = new HashMap<>();

		twoWayService.registTwoWayQuestion(twoWayQuestionRequestDTO);

		responseData.put("message", "양자택일 질문 등록 성공");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 양자택일 질문 삭제
	@LoginRequired
	@DeleteMapping("/question/{question_number}")
	public ResponseEntity<Map<String, Object>> createTwoWayQuestion(
		@PathVariable Long question_number) {
		Map<String, Object> responseData = new HashMap<>();

		twoWayService.deleteTwoWayQuestion(question_number);

		responseData.put("message", "양자택일 질문 삭제 완료");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 양자택일 답변 생성
	@LoginRequired
	@PostMapping("/answer")
	public ResponseEntity<Map<String, Object>> createTwoWayAnswer(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@RequestBody TwoWayAnswerRequestDTO twoWayAnswerRequestDTO) throws ParseException {
		Map<String, Object> responseData = new HashMap<>();

		Long member_number = jwtTokenProvider.getMemberNumber(jwtToken);
		twoWayService.registTwoWayAnswer(twoWayAnswerRequestDTO, member_number);

		responseData.put("message", "양자택일 답변 등록 성공");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}