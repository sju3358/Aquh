package com.ssafy.team8alette.domain.bubble.session.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CreateBubbleSessionRequestDto;
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

	@PostMapping
	public String createBubbleSessionRequest(
		@RequestBody CreateBubbleSessionRequestDto requestDto) throws
		OpenViduJavaClientException,
		OpenViduHttpException {

		String token = bubbleSessionService.createHostBubbleSession(requestDto.getBubbleNumber());

		return token;
	}
}
