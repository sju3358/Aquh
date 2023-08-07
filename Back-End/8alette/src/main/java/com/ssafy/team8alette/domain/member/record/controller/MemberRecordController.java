package com.ssafy.team8alette.domain.member.record.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.member.record.model.dto.MemberRecordEntity;
import com.ssafy.team8alette.domain.member.record.model.service.MemberRecordService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/member/record")
@RestController
@RequiredArgsConstructor
public class MemberRecordController {

	private final MemberRecordService memberRecordService;

	// @LoginRequired
	@GetMapping("/{memberNumber}")
	public ResponseEntity<Map<String, Object>> getMemberInfoRequest(
		@PathVariable Long memberNumber,
		@RequestParam Optional<String> exp_cnt,
		@RequestParam Optional<String> comment_cnt,
		@RequestParam Optional<String> room_join_cnt,
		@RequestParam Optional<String> like_give_cnt,
		@RequestParam Optional<String> best_cnt,
		@RequestParam Optional<String> following_cnt,
		@RequestParam Optional<String> follower_cnt) {

		MemberRecordEntity memberRecordEntity = memberRecordService.getMemberRecord(memberNumber);

		Map<String, Object> responseData = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		if (exp_cnt.isEmpty() && comment_cnt.isEmpty() && room_join_cnt.isEmpty()
			&& like_give_cnt.isEmpty() && best_cnt.isEmpty() && following_cnt.isEmpty() && follower_cnt.isEmpty()) {

			data.put("exp_cnt", memberRecordEntity.getMemberExpCnt());
			data.put("comment_cnt", memberRecordEntity.getMemberCommentCnt());
			data.put("room_join_cnt", memberRecordEntity.getBubbleJoinCnt());
			data.put("like_give_cnt", memberRecordEntity.getMemberLikeGiveCnt());
			data.put("best_cnt", memberRecordEntity.getMemberBestCnt());
			data.put("following_cnt", memberRecordEntity.getMemberFollowingCnt());
			data.put("follower_cnt", memberRecordEntity.getMemberFollowerCnt());

		} else {

			if (exp_cnt.orElse("N").equals("Y")) {
				data.put("exp_cnt", memberRecordEntity.getMemberExpCnt());
			}
			if (comment_cnt.orElse("N").equals("Y")) {
				data.put("comment_cnt", memberRecordEntity.getMemberCommentCnt());
			}
			if (room_join_cnt.orElse("N").equals("Y")) {
				data.put("room_join_cnt", memberRecordEntity.getBubbleJoinCnt());
			}
			if (like_give_cnt.orElse("N").equals("Y")) {
				data.put("like_give_cnt", memberRecordEntity.getMemberLikeGiveCnt());
			}
			if (best_cnt.orElse("N").equals("Y")) {
				data.put("best_cnt", memberRecordEntity.getMemberBestCnt());
			}
			if (following_cnt.orElse("N").equals("Y")) {
				data.put("following_cnt", memberRecordEntity.getMemberFollowingCnt());
			}
			if (follower_cnt.orElse("N").equals("Y")) {
				data.put("follower_cnt", memberRecordEntity.getMemberFollowerCnt());
			}

		}

		responseData.put("message", "success");
		responseData.put("data", data);
		responseData.put("status", 200);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

}
