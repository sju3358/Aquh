package com.ssafy.team8alette.domain.bubble.session.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.session.model.dto.BubbleDto;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CloseBubbleRequestDto;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CreateBubbleRequestDto;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.EnterBubbleRequest;
import com.ssafy.team8alette.domain.bubble.session.model.dto.response.BubbleResponseDto;
import com.ssafy.team8alette.domain.bubble.session.service.BubbleParticipantService;
import com.ssafy.team8alette.domain.bubble.session.service.BubbleService;
import com.ssafy.team8alette.domain.bubble.session.service.BubbleSessionService;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberLoginInfo;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberAuthService;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/bubble")
public class BubbleController {

	private final BubbleService bubbleService;
	private final BubbleParticipantService bubbleParticipantService;
	private final MemberAuthService memberAuthService;

	@LoginRequired
	@GetMapping("/{bubbleNumber}")
	public BubbleResponseDto getBubbleInfoRequest(
		@PathVariable Long bubbleNumber) {

		BubbleDto bubble = bubbleService.getBubbleInfo(bubbleNumber);

		return BubbleResponseDto.builder()
			.data(bubble)
			.message("success")
			.build();
	}
	@LoginRequired
	@PostMapping
	public BubbleResponseDto createBubbleRequest(
		@RequestBody CreateBubbleRequestDto createBubbleRequestDto) {

		Long bubbleNumber = bubbleService.createBubble(createBubbleRequestDto);

		bubbleParticipantService.createBubbleList(createBubbleRequestDto.getHostMemberNumber(), bubbleNumber);

		return BubbleResponseDto.builder()
			.data(bubbleNumber)
			.message("success")
			.build();
	}
	@LoginRequired
	@PutMapping
	public BubbleResponseDto closeBubbleRequest(
		@RequestHeader(value = "AUTH-TOKEN") String accessToken,
		@RequestBody CloseBubbleRequestDto closeBubbleRequestDto){

		Long bubbleNumber = closeBubbleRequestDto.getBubbleNumber();

		BubbleDto bubble = bubbleService.getBubbleInfo(bubbleNumber);
		Long hostNumber = bubble.getHostMemberNumber();

		memberAuthService.getLoginMemberInfo()

	}
	@LoginRequired
	@PutMapping
	public BubbleResponseDto enterBubbleRequest(
		@RequestBody EnterBubbleRequest enterBubbleRequest) {

		Long bubbleNumber = bubbleService.(createBubbleRequestDto);

		bubbleParticipantService.createBubbleList(enterBubbleRequest.getMemberNumber(),
			enterBubbleRequest.getBubbleNumber());

		return BubbleResponseDto.builder()
			.data()
			.message("success")
			.build();

	}

	@GetMapping("/bubblings")
	public BubbleResponseDto getBubblingListRequest(){
		List<BubbleDto> bubblings = bubbleService.getBubblingList();

		return BubbleResponseDto.builder()
			.data(bubblings)
			.message("success")
			.build();
	}

	@GetMapping("/bubbletalks")
	public BubbleResponseDto getBubbleTalkRequest(){
		List<BubbleDto> bubbleTalks = bubbleService.getBubbleTalkList();

		return BubbleResponseDto.builder()
			.data(bubbleTalks)
			.message("success")
			.build();
	}
}
