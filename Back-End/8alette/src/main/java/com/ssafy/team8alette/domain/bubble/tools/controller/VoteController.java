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

import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.VoteQuestionResponseDTO;
import com.ssafy.team8alette.domain.bubble.tools.service.VoteService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bubble/vote")
public class VoteController {
	private final VoteService voteService;
	private final JwtTokenProvider jwtTokenProvider;

	// 투표 질문 리스트 조회
	@LoginRequired
	@GetMapping("/question/{bubble_number}")
	public ResponseEntity<Map<String, Object>> findAllVoteQuestions(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable Long bubble_number) throws ParseException{

		Long member_number = jwtTokenProvider.getMemberNumber(jwtToken);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "vote question list 조회 성공");
		responseData.put("status", 200);
		responseData.put("vote_questions", voteService.getVoteQuestions(bubble_number, member_number));

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 투표 질문 생성
	@LoginRequired
	@PostMapping("/question")
	public ResponseEntity<Map<String, Object>> createVoteQuestion(
		@RequestBody VoteQuestionResponseDTO voteQuestionResponseDTO) {

		voteService.registVoteQuestion(voteQuestionResponseDTO);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "vote question 등록 성공");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 투표 질문 삭제
	@LoginRequired
	@DeleteMapping("/question/{vote_question_number}")
	public ResponseEntity<Map<String, Object>> deleteVoteQuestion(
		@PathVariable Long vote_question_number) {

		voteService.deleteVoteQuestion(vote_question_number);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "vote question 삭제 성공");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 투표 선택 생성
	@LoginRequired
	@PostMapping("/answer/{vote_question_number}")
	public ResponseEntity<Map<String, Object>> createVoteQuestion(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable Long vote_question_number) throws ParseException {

		Long member_number = jwtTokenProvider.getMemberNumber(jwtToken);

		voteService.registVoteSelect(vote_question_number, member_number);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "vote select 등록 성공");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 투표 선택 생성
	@LoginRequired
	@DeleteMapping("/answer/{vote_question_number}")
	public ResponseEntity<Map<String, Object>> deleteVoteQuestion(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable Long vote_question_number) throws ParseException {

		Long member_number = jwtTokenProvider.getMemberNumber(jwtToken);
		voteService.deleteVoteSelect(vote_question_number, member_number);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "vote select 삭제 성공");
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
