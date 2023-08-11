package com.ssafy.team8alette.domain.bubble.session.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.session.service.BubbleService;
import com.ssafy.team8alette.domain.bubble.session.service.BubbleSessionService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.domain.member.record.model.service.MemberRecordService;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/bubble-session")
@RestController
@RequiredArgsConstructor
public class BubbleSessionController {

	private final JwtTokenProvider tokenProvider;
	private final BubbleSessionService bubbleSessionService;
	private final MemberRecordService memberRecordService;
	private final BubbleService bubbleService;

	@PostMapping("/{bubbleNumber}")
	public ResponseEntity createBubbleSessionRequest(
		@PathVariable Long bubbleNumber) throws
		OpenViduJavaClientException,
		OpenViduHttpException {

		String token = bubbleSessionService.createHostBubbleSession(bubbleNumber);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("token", token);

		return ResponseEntity
			.status(200)
			.body(responseData);
	}

	@PutMapping("/{bubbleNumber}")
	public ResponseEntity enterBubbleSessionRequest(
		@PathVariable Long bubbleNumber) throws OpenViduJavaClientException, OpenViduHttpException {

		String token = bubbleSessionService.createSubBubbleSession(bubbleNumber);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("token", token);

		return ResponseEntity
			.status(200)
			.body(responseData);
	}

}
