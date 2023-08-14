package com.ssafy.team8alette.domain.bubble.session.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/{sessionId}")
	public ResponseEntity createBubbleSessionRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable String sessionId) throws
		OpenViduJavaClientException,
		OpenViduHttpException, ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);
		String token = bubbleSessionService.createHostBubbleSession(sessionId, memberNumber);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("token", token);

		return ResponseEntity
			.status(200)
			.body(responseData);
	}

	@PutMapping("/{sessionId}")
	public ResponseEntity enterBubbleSessionRequest(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken,
		@PathVariable String sessionId) throws OpenViduJavaClientException, OpenViduHttpException, ParseException {

		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);
		String token = bubbleSessionService.createSubBubbleSession(sessionId, memberNumber);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("token", token);

		return ResponseEntity
			.status(200)
			.body(responseData);

		// 경험치 부여, 알림 부여를 어디서 해야하는지 물어보기
		// boolean bubblingOrBubbleTalk = bubbleService.getBubbleInfo(bubbleNumber).isBubbleType();
		// 	if (bubblingOrBubbleTalk) {
		// 	memberRecordService.updateMemberExp(memberNumber, 30);
		// 	memberRecordService.updateMemberRoomJoinCnt(memberNumber, 1);
		// 	Member member = memberService.getMemberInfo(memberNumber);
		// 	alarmService.requestAlarm(member, "bubbling", member.getMemberNickname() + "님이 버블톡방에 참여하였습니다.", 0);
		//
		// }
		// 	memberRecordService.updateMemberExp(memberNumber, 100);
		// 	memberRecordService.updateMemberRoomJoinCnt(memberNumber, 1);
		// Member member = memberService.getMemberInfo(memberNumber);
		// 	alarmService.requestAlarm(member, "bubbling", member.getMemberNickname() + "님이 버블링방에 참여하였습니다.", 0);

	}

}
