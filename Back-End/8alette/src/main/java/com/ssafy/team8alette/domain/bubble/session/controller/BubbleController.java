package com.ssafy.team8alette.domain.bubble.session.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CreateBubbleRequest;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.EnterBubbleRequest;
import com.ssafy.team8alette.domain.bubble.session.model.dto.response.CreateBubbleResponse;
import com.ssafy.team8alette.domain.bubble.session.model.dto.response.EnterBubbleResponse;
import com.ssafy.team8alette.domain.bubble.session.model.service.BubbleParticipantService;
import com.ssafy.team8alette.domain.bubble.session.model.service.BubbleService;
import com.ssafy.team8alette.domain.bubble.session.model.service.BubbleSessionService;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/bubble")
public class BubbleController {

	private final BubbleService bubbleService;
	private final BubbleSessionService bubbleSessionService;
	private final BubbleParticipantService bubbleParticipantService;

	@PostMapping
	public ResponseEntity<CreateBubbleResponse> createBubbleTalkRequest(
		@RequestBody CreateBubbleRequest createBubbleRequest) throws
		OpenViduJavaClientException,
		OpenViduHttpException {

		Long bubbleNumber = bubbleService.createBubble(createBubbleRequest);

		bubbleParticipantService.createBubbleList(createBubbleRequest.getHostMemberNumber(), bubbleNumber);

		String sessionToken = bubbleSessionService.createHostBubbleSession(Long.toString(bubbleNumber));

		CreateBubbleResponse createBubbleResponse = CreateBubbleResponse.builder()
			.message("OK")
			.status(HttpStatus.OK)
			.token(sessionToken)
			.build();

		return new ResponseEntity<>(createBubbleResponse, HttpStatus.OK);
	}

	@PutMapping("/enter")
	public ResponseEntity<EnterBubbleResponse> enterBubbleTalkRequest(
		@RequestBody EnterBubbleRequest enterBubbleRequest) throws
		OpenViduJavaClientException,
		OpenViduHttpException {

		String sessionToken = bubbleSessionService.createSubBubbleSession(
			Long.toString(enterBubbleRequest.getBubbleNumber()));

		bubbleParticipantService.createBubbleList(enterBubbleRequest.getMemberNumber(),
			enterBubbleRequest.getBubbleNumber());

		EnterBubbleResponse enterBubbleResponse = EnterBubbleResponse.builder()
			.message("OK")
			.status(HttpStatus.OK)
			.token(sessionToken)
			.build();

		return new ResponseEntity<>(enterBubbleResponse, HttpStatus.OK);
	}
}
