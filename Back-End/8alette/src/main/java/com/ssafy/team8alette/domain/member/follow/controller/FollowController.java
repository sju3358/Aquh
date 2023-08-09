package com.ssafy.team8alette.domain.member.follow.controller;

import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.team8alette.domain.member.follow.model.service.FollowService;
import com.ssafy.team8alette.global.annotation.LoginRequired;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/follow")
@RestController
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;

	@LoginRequired
	@GetMapping("/{memberNumber}")
	public ResponseEntity<Map<String, Object>> getFollowListRequest(
		@PathVariable Long memberNumber,
		@RequestParam String filter) throws InvalidPropertiesFormatException {

		Map<String, List<Long>> followList = new HashMap<>();

		if (filter.equals("following")) {
			followList.put("following_list", followService.getFollowingMemberList(memberNumber));
		} else if (filter.equals("follower")) {
			followList.put("follower_list", followService.getFollowerMemberList(memberNumber));
		} else {
			throw new InvalidPropertiesFormatException("filter 값이 잘못되었습니다.");
		}

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");
		responseData.put("data", followList);

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@PostMapping
	public ResponseEntity<Map<String, Object>> followRequest(@RequestBody Map<String, String> param) {

		Long followerMemberNumber = Long.parseLong(param.get("member_follower").toString());
		Long followingMemberNumber = Long.parseLong(param.get("member_following").toString());

		followService.followMember(followerMemberNumber, followingMemberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@LoginRequired
	@DeleteMapping
	public ResponseEntity<Map<String, Object>> unfollowRequest(@RequestBody Map<String, String> param) {

		Long followerMemberNumber = Long.parseLong(param.get("member_follower").toString());
		Long followingMemberNumber = Long.parseLong(param.get("member_following").toString());

		followService.unfollowMember(followerMemberNumber, followingMemberNumber);

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", "success");

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
