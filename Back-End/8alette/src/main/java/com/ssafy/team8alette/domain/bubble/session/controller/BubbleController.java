package com.ssafy.team8alette.domain.bubble.session.controller;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.bubble.session.model.dto.BubbleDto;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CreateBubbleRequestDto;
import com.ssafy.team8alette.domain.bubble.session.model.dto.response.BubbleResponseDto;
import com.ssafy.team8alette.domain.bubble.session.service.BubbleParticipantService;
import com.ssafy.team8alette.domain.bubble.session.service.BubbleService;
import com.ssafy.team8alette.domain.member.auth.model.service.MemberAuthService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.domain.member.record.model.service.MemberRecordService;
import com.ssafy.team8alette.global.annotation.LoginRequired;
import com.ssafy.team8alette.global.exception.UnAuthorizedException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bubble")
public class BubbleController {

	private final BubbleService bubbleService;
	private final BubbleParticipantService bubbleParticipantService;
	private final MemberAuthService memberAuthService;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRecordService memberRecordService;

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
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@RequestBody CreateBubbleRequestDto requestDto) throws ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);
		Long bubbleNumber = bubbleService.createBubble(requestDto);

		if (memberNumber != requestDto.getHostMemberNumber())
			throw new UnAuthorizedException("본인만 방을 만들 수 있습니다");

		bubbleParticipantService.createBubbleList(memberNumber, bubbleNumber);

		return BubbleResponseDto.builder()
			.data(bubbleNumber)
			.message("success")
			.build();
	}

	@LoginRequired
	@PutMapping("/{bubbleNumber}/enter")
	public BubbleResponseDto closeBubbleRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable Long bubbleNumber) throws ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);

		bubbleService.closeBubble(bubbleNumber, memberNumber);

		return BubbleResponseDto.builder()
			.message("success")
			.build();
	}

	@LoginRequired
	@PutMapping("/{bubbleNumber}")
	public BubbleResponseDto enterBubbleRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable Long bubbleNumber) throws ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);

		bubbleParticipantService.createBubbleList(bubbleNumber, memberNumber);

		return BubbleResponseDto.builder()
			.message("success")
			.build();

	}

	//
	@GetMapping("/bubblings")
	public BubbleResponseDto getBubblingListRequest() {
		List<BubbleDto> bubblings = bubbleService.getBubblingList();

		return BubbleResponseDto.builder()
			.data(bubblings)
			.message("success")
			.build();
	}

	@GetMapping("/bubbletalks")
	public BubbleResponseDto getBubbleTalkRequest() {
		List<BubbleDto> bubbleTalks = bubbleService.getBubbleTalkList();

		return BubbleResponseDto.builder()
			.data(bubbleTalks)
			.message("success")
			.build();
	}

	@GetMapping("/bubblings/my")
	public BubbleResponseDto getMyBubblingListRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken) throws ParseException {
		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);
		List<BubbleDto> bubblings = bubbleService.getBubblingList(memberNumber);

		return BubbleResponseDto.builder()
			.data(bubblings)
			.message("success")
			.build();
	}
}
