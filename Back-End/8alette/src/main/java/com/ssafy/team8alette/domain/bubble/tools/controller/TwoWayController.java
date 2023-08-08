package com.ssafy.team8alette.domain.bubble.tools.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.tools.service.TwoWayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/twoway")
public class TwoWayController {
	private final TwoWayService twoWayService;

	@GetMapping("/question")
	public ResponseEntity<Map<String, Object>> findALlTwoWayQuestions(
		@RequestParam Long member_number,
		@RequestParam long bubble_number) {
		Map<String, Object> responseData = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		responseData.put("message", "success");
		responseData.put("status", 200);
		responseData.put("data", twoWayService.getTwoWayQuestions(bubble_number, member_number));

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// @PutMapping("/{feed_number}")
	// public ResponseEntity<?> deleteFeed(@PathVariable Long feed_number) {
	//
	// 	feedService.deleteFeed(feed_number);
	// 	Map<String, Object> responseData = new HashMap<>();
	// 	responseData.put("message", "success");
	// 	responseData.put("status", 200);
	// 	return new ResponseEntity<>(responseData, HttpStatus.OK);
	// }
}