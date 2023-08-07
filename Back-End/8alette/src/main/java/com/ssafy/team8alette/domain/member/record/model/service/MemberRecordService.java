package com.ssafy.team8alette.domain.member.record.model.service;

import org.springframework.stereotype.Component;

import com.ssafy.team8alette.domain.member.record.model.dao.MemberRecordRepository;
import com.ssafy.team8alette.domain.member.record.model.dto.MemberRecordEntity;
import com.ssafy.team8alette.global.exception.MemberNotExistException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberRecordService {

	private final MemberRecordRepository memberRecordRepository;

	public MemberRecordEntity getMemberRecord(Long memberNumber) {
		MemberRecordEntity memberRecordEntity = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);

		if (memberRecordEntity == null)
			throw new MemberNotExistException();

		return memberRecordEntity;
	}

	public void updateMemberExp(Long memberNumber, int expCnt) {
		MemberRecordEntity memberRecordEntity = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecordEntity.setMemberExpCnt(memberRecordEntity.getMemberExpCnt() + expCnt);
		memberRecordRepository.save(memberRecordEntity);
	}

	public void updateMemberComment(Long memberNumber, int commentCnt) {
		MemberRecordEntity memberRecordEntity = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecordEntity.setMemberCommentCnt(memberRecordEntity.getMemberCommentCnt() + commentCnt);
		memberRecordRepository.save(memberRecordEntity);
	}

	public void updateMemberRoomJoinCnt(Long memberNumber, int roomJoinCnt) {
		MemberRecordEntity memberRecordEntity = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecordEntity.setBubbleJoinCnt(memberRecordEntity.getBubbleJoinCnt() + roomJoinCnt);
		memberRecordRepository.save(memberRecordEntity);
	}

	public void updateMemberLikeGiveCnt(Long memberNumber, int likeGiveCnt) {
		MemberRecordEntity memberRecordEntity = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecordEntity.setMemberLikeGiveCnt(memberRecordEntity.getMemberLikeGiveCnt() + likeGiveCnt);
		memberRecordRepository.save(memberRecordEntity);
	}

	public void updateMemberReceiveCnt(Long memberNumber, int likeReceiveCnt) {
		MemberRecordEntity memberRecordEntity = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecordEntity.setMemberExpCnt(memberRecordEntity.getMemberExpCnt() + likeReceiveCnt);
		memberRecordRepository.save(memberRecordEntity);
	}

	public void updateMemberBestCnt(Long memberNumber, int bestCnt) {
		MemberRecordEntity memberRecordEntity = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecordEntity.setMemberBestCnt(memberRecordEntity.getMemberBestCnt() + bestCnt);
		memberRecordRepository.save(memberRecordEntity);
	}

	public void updateMemberFollowingCnt(Long memberNumber, int followingCnt) {
		MemberRecordEntity memberRecordEntity = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecordEntity.setMemberFollowerCnt(memberRecordEntity.getMemberFollowerCnt() + followingCnt);
		memberRecordRepository.save(memberRecordEntity);
	}

	public void updateMemberFollowerCnt(Long memberNumber, int followerCnt) {
		MemberRecordEntity memberRecordEntity = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecordEntity.setMemberFollowerCnt(memberRecordEntity.getMemberFollowerCnt() + followerCnt);
		memberRecordRepository.save(memberRecordEntity);
	}

}
