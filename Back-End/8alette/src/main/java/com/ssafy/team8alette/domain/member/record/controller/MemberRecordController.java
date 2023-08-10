package com.ssafy.team8alette.domain.member.record.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.domain.member.record.model.dto.entity.MemberRecord;
import com.ssafy.team8alette.domain.member.record.model.dto.response.MemberRecordDTO;
import com.ssafy.team8alette.domain.member.record.model.service.MemberRecordService;
import com.ssafy.team8alette.domain.symbol.model.service.SymbolGrantService;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member/record")
@RestController
@RequiredArgsConstructor
public class MemberRecordController {

	private final MemberRecordService memberRecordService;
	private final JwtTokenProvider jwtTokenProvider;
	private final SymbolGrantService symbolGrantService;

	@LoginRequired
	@GetMapping("/{memberNumber}")
	public ResponseEntity<Map<String, Object>> getMemberInfoRequest(
		@PathVariable Long memberNumber,
		@RequestParam Optional<String> exp_cnt,
		@RequestParam Optional<String> feed_cnt,
		@RequestParam Optional<String> room_join_cnt,
		@RequestParam Optional<String> like_give_cnt,
		@RequestParam Optional<String> best_cnt,
		@RequestParam Optional<String> following_cnt,
		@RequestParam Optional<String> follower_cnt) {

		MemberRecord memberRecord = memberRecordService.getMemberRecord(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		if (exp_cnt.isEmpty() && feed_cnt.isEmpty() && room_join_cnt.isEmpty()
			&& like_give_cnt.isEmpty() && best_cnt.isEmpty() && following_cnt.isEmpty() && follower_cnt.isEmpty()) {

			data.put("exp_cnt", memberRecord.getMemberExpCnt());
			data.put("feed_cnt", memberRecord.getMemberFeedCnt());
			data.put("room_join_cnt", memberRecord.getBubbleJoinCnt());
			data.put("like_give_cnt", memberRecord.getMemberLikeGiveCnt());
			data.put("best_cnt", memberRecord.getMemberBestCnt());
			data.put("following_cnt", memberRecord.getMemberFollowingCnt());
			data.put("follower_cnt", memberRecord.getMemberFollowerCnt());

		} else {

			if (exp_cnt.orElse("N").equals("Y")) {
				data.put("exp_cnt", memberRecord.getMemberExpCnt());
			}
			if (feed_cnt.orElse("N").equals("Y")) {
				data.put("comment_cnt", memberRecord.getMemberFeedCnt());
			}
			if (room_join_cnt.orElse("N").equals("Y")) {
				data.put("room_join_cnt", memberRecord.getBubbleJoinCnt());
			}
			if (like_give_cnt.orElse("N").equals("Y")) {
				data.put("like_give_cnt", memberRecord.getMemberLikeGiveCnt());
			}
			if (best_cnt.orElse("N").equals("Y")) {
				data.put("best_cnt", memberRecord.getMemberBestCnt());
			}
			if (following_cnt.orElse("N").equals("Y")) {
				data.put("following_cnt", memberRecord.getMemberFollowingCnt());
			}
			if (follower_cnt.orElse("N").equals("Y")) {
				data.put("follower_cnt", memberRecord.getMemberFollowerCnt());
			}

		}

		responseData.put("message", "success");
		responseData.put("data", data);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getMemberPage(
		@RequestHeader(value = "AUTH-TOKEN") String jwtToken) throws
		ParseException {
		Long memberNumber = jwtTokenProvider.getMemberNumber(jwtToken);
		
		MemberRecordDTO memberRecordDTO = memberRecordService.getMemberRecordDetail(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("data", memberRecordDTO);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
