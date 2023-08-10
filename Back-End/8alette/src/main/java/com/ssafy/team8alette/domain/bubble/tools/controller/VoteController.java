package com.ssafy.team8alette.domain.bubble.tools.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.tools.service.VoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bubble/vote")
public class VoteController {
	private final VoteService voteService;

	@GetMapping("/question")
	public ResponseEntity<Map<String, Object>> findAllVoteQuestions(
		@RequestParam Long bubble_number, @RequestParam Long member_number) {
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "vote question list 조회 성공");
		responseData.put("status", 200);
		responseData.put("vote_questions", voteService.getVoteQuestions(bubble_number,member_number));

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
