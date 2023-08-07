package com.ssafy.team8alette.domain.member.follow.model.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;
import com.ssafy.team8alette.domain.member.follow.exception.FollowNotFoundException;
import com.ssafy.team8alette.domain.member.follow.model.dao.FollowRepository;
import com.ssafy.team8alette.domain.member.follow.model.dto.Entity.FollowEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

	private final FollowRepository followRepository;
	private final MemberRepository memberRepository;
	// private final MemberRecordService memberRecordService;

	public List<Long> getFollowerMemberList(Long memberNumber) {
		MemberEntity memberEntity = memberRepository.findById(memberNumber).orElseThrow();
		List<FollowEntity> followerList = followRepository.findAllByFollowingMemberNumber(memberEntity);

		if (followerList == null)
			throw new FollowNotFoundException("팔로우 리스트가 존재하지 않습니다.");

		List<Long> followers = new LinkedList<>();
		for (FollowEntity followEntityInfo : followerList) {
			followers.add(followEntityInfo.getFollowerMemberNumberEntity().getMemberNumber());
		}
		return followers;
	}

	public List<Long> getFollowingMemberList(Long memberNumber) {
		MemberEntity memberEntity = memberRepository.findById(memberNumber).orElseThrow();

		List<FollowEntity> followingList = followRepository.findAllByFollowerMemberNumber(memberEntity);

		if (followingList == null)
			throw new FollowNotFoundException("팔로잉 리스트가 존재하지 않습니다.");

		List<Long> followings = new LinkedList<>();
		for (FollowEntity followEntityInfo : followingList) {
			followings.add(followEntityInfo.getFollowingMemberNumberEntity().getMemberNumber());
		}
		return followings;
	}

	public void followMember(Long memberNumber, Long targetMemberNumber) {
		MemberEntity followerMemberEntity = memberRepository.findById(memberNumber).orElseThrow();
		MemberEntity followingMemberEntity = memberRepository.findById(targetMemberNumber).orElseThrow();
		if (followerMemberEntity.getMemberNumber() == followingMemberEntity.getMemberNumber()) {
			throw new FollowNotFoundException("같은 회원에 대해서는 팔로우 할 수 없습니다.");
		}
		FollowEntity followEntityCheck = followRepository.findByFollowerMemberNumberAndFollowingMemberNumber(
			followerMemberEntity, followingMemberEntity);
		if (followEntityCheck != null) {
			throw new FollowNotFoundException("이미 팔로우 하고 있습니다.");
		} else {
			FollowEntity followEntity = new FollowEntity();
			followEntity.setFollowerMemberNumberEntity(followerMemberEntity);
			followEntity.setFollowingMemberNumberEntity(followingMemberEntity);
			followEntity.setCreateDate(new Date());

			followRepository.save(followEntity);

			//멤버기록에서 수정해야함
			// memberRecordService.updateMemberFollowingCnt(memberNumber, 1);
			// memberRecordService.updateMemberFollowerCnt(targetMemberNumber, 1);

			/* 업적에 맞는지 확인 후 심볼 획득 */
		}
	}

	public void unfollowMember(Long memberNumber, Long targetMemberNumber) {
		MemberEntity followerMemberEntity = memberRepository.findById(memberNumber).orElseThrow();
		MemberEntity followingMemberEntity = memberRepository.findById(targetMemberNumber).orElseThrow();

		FollowEntity followEntity = followRepository.findByFollowerMemberNumberAndFollowingMemberNumber(
			followerMemberEntity,
			followingMemberEntity);

		if (followEntity == null)
			throw new FollowNotFoundException("팔로우 정보가 존재하지 않습니다.");

		followRepository.delete(followEntity);

		//멤버기록에서 수정
		// memberRecordService.updateMemberFollowingCnt(memberNumber, -1);
		// memberRecordService.updateMemberFollowerCnt(targetMemberNumber, -1);

	}
}
