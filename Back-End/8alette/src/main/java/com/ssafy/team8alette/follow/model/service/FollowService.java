package com.ssafy.team8alette.follow.model.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.follow.exception.FollowNotFoundException;
import com.ssafy.team8alette.follow.model.dao.FollowRepository;
import com.ssafy.team8alette.follow.model.dto.Entity.Follow;
import com.ssafy.team8alette.member.model.dao.MemberRepository;
import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.member.model.service.MemberRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

	private final FollowRepository followRepository;
	private final MemberRepository memberRepository;
	private final MemberRecordService memberRecordService;

	public List<Long> getFollowerMemberList(Long memberNumber) {
		Member member = memberRepository.findById(memberNumber).orElseThrow();
		List<Follow> followerList = followRepository.findAllByFollowingMemberNumber(member);

		if (followerList == null)
			throw new FollowNotFoundException("팔로우 리스트가 존재하지 않습니다.");

		List<Long> followers = new LinkedList<>();
		for (Follow followInfo : followerList) {
			followers.add(followInfo.getFollowerMemberNumber().getMemberNumber());
		}
		return followers;
	}

	public List<Long> getFollowingMemberList(Long memberNumber) {
		Member member = memberRepository.findById(memberNumber).orElseThrow();

		List<Follow> followingList = followRepository.findAllByFollowerMemberNumber(member);

		if (followingList == null)
			throw new FollowNotFoundException("팔로잉 리스트가 존재하지 않습니다.");

		List<Long> followings = new LinkedList<>();
		for (Follow followInfo : followingList) {
			followings.add(followInfo.getFollowingMemberNumber().getMemberNumber());
		}
		return followings;
	}

	public void followMember(Long memberNumber, Long targetMemberNumber) {
		Member followerMember = memberRepository.findById(memberNumber).orElseThrow();
		Member followingMember = memberRepository.findById(targetMemberNumber).orElseThrow();
		if (followerMember.getMemberNumber() == followingMember.getMemberNumber()) {
			throw new FollowNotFoundException("같은 회원에 대해서는 팔로우 할 수 없습니다.");
		}
		Follow followCheck = followRepository.findByFollowerMemberNumberAndFollowingMemberNumber(
			followerMember, followingMember);
		if (followCheck != null) {
			throw new FollowNotFoundException("이미 팔로우 하고 있습니다.");
		} else {
			Follow follow = new Follow();
			follow.setFollowerMemberNumber(followerMember);
			follow.setFollowingMemberNumber(followingMember);
			follow.setCreateDate(new Date());

			followRepository.save(follow);

			//멤버기록에서 수정해야함
			// memberRecordService.updateMemberFollowingCnt(memberNumber, 1);
			// memberRecordService.updateMemberFollowerCnt(targetMemberNumber, 1);

			/* 업적에 맞는지 확인 후 심볼 획득 */
		}
	}

	public void unfollowMember(Long memberNumber, Long targetMemberNumber) {
		Member followerMember = memberRepository.findById(memberNumber).orElseThrow();
		Member followingMember = memberRepository.findById(targetMemberNumber).orElseThrow();

		Follow follow = followRepository.findByFollowerMemberNumberAndFollowingMemberNumber(followerMember,
			followingMember);

		if (follow == null)
			throw new FollowNotFoundException("팔로우 정보가 존재하지 않습니다.");

		followRepository.delete(follow);

		//멤버기록에서 수정
		// memberRecordService.updateMemberFollowingCnt(memberNumber, -1);
		// memberRecordService.updateMemberFollowerCnt(targetMemberNumber, -1);

	}
}
