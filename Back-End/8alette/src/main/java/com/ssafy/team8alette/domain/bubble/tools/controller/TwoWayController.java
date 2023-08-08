package com.ssafy.team8alette.domain.bubble.tools.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.tools.service.TwoWayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/twoway")
public class TwoWayController {
	private final TwoWayService twoWayService;
	// private final BubbleService bubbleService;

	// @GetMapping("/question/{bubble_number")
	// public ResponseEntity<Map<String, Object>> findALlTwoWayQuestions(@PathVariable Long bubble_number) {
	// 	Map<String, Object> responseData = new HashMap<>();
	// 	Map<String, Object> data = new HashMap<>();
	//
	// 	responseData.put("message", "success");
	// 	responseData.put("status", 200);
	// 	return new ResponseEntity<>(responseData, HttpStatus.OK);
	// }
	//
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