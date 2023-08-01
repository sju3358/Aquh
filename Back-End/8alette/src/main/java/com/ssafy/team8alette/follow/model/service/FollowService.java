package com.ssafy.team8alette.follow.model.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.follow.model.dao.FollowRepository;
import com.ssafy.team8alette.follow.model.dto.Follow;
import com.ssafy.team8alette.member.model.service.MemberRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

	private FollowRepository followRepository;
	private MemberRecordService memberRecordService;

	public List<Long> getFollowerMemberList(Long memberNumber) {
		List<Follow> followerList = followRepository.findAllByFollowingMemberNumber(memberNumber);

		List<Long> followers = new LinkedList<>();
		for (Follow followInfo : followerList) {
			followers.add(followInfo.getFollowerMemberNumber());
		}
		return followers;
	}

	public List<Long> getFollowingMemberList(Long memberNumber) {
		List<Follow> followingList = followRepository.findAllByFollowerMemberNumber(memberNumber);

		List<Long> followings = new LinkedList<>();
		for (Follow followInfo : followingList) {
			followings.add(followInfo.getFollowerMemberNumber());
		}
		return followings;
	}

	public void followMember(Long memberNumber, Long targetMemberNumber) {
		Follow follow = new Follow();
		follow.setFollowerMemberNumber(memberNumber);
		follow.setFollowingMemberNumber(targetMemberNumber);
		followRepository.save(follow);

		memberRecordService.updateMemberFollowingCnt(memberNumber, 1);
		memberRecordService.updateMemberFollowerCnt(targetMemberNumber, 1);

		/* 업적에 맞는지 확인 후 심볼 획득 */
	}

	public void unfollowMember(Long memberNumber, Long targetMemberNumber) {
		Follow follow = followRepository.findFollowByFollowerMemberNumberAndFollowingMemberNumber(memberNumber,
			targetMemberNumber);

		followRepository.delete(follow);

		memberRecordService.updateMemberFollowingCnt(memberNumber, -1);
		memberRecordService.updateMemberFollowerCnt(targetMemberNumber, -1);

	}
}
