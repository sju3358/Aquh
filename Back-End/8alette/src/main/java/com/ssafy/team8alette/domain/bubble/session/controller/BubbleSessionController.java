package com.ssafy.team8alette.domain.bubble.session.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.team8alette.domain.bubble.session.service.BubbleSessionService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/bubble/bubble-session")
@RestControllerAdvice
@RequiredArgsConstructor
public class BubbleSessionController {

	private final JwtTokenProvider tokenProvider;
	private final BubbleSessionService bubbleSessionService;

	@PostMapping("/{bubbleNumber}")
	public ResponseEntity createBubbleSessionRequest(
		@PathVariable Long bubbleNumber) throws
		OpenViduJavaClientException,
		OpenViduHttpException {

		String token = bubbleSessionService.createHostBubbleSession(bubbleNumber);

		return ResponseEntity
			.status(200)
			.header("OpenviduToken", token)
			.build();
	}

	@PutMapping("/{bubbleNumber}")
	public ResponseEntity enterBubbleSessionRequest(
		@PathVariable Long bubbleNumber) throws OpenViduJavaClientException, OpenViduHttpException {

		String token = bubbleSessionService.createSubBubbleSession(bubbleNumber);

		return ResponseEntity
			.status(200)
			.header("OpenviduToken", token)
			.build();
	}

}
