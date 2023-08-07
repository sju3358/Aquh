package com.ssafy.team8alette.domain.member.record.model.service;

import org.springframework.stereotype.Component;

import com.ssafy.team8alette.domain.member.record.model.dao.MemberRecordRepository;
import com.ssafy.team8alette.domain.member.record.model.dto.MemberRecord;
import com.ssafy.team8alette.global.exception.MemberNotExistException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberRecordService {

	private final MemberRecordRepository memberRecordRepository;

	public MemberRecord getMemberRecord(Long memberNumber) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);

		if (memberRecord == null)
			throw new MemberNotExistException();

		return memberRecord;
	}

	public void updateMemberExp(Long memberNumber, int expCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setMemberExpCnt(memberRecord.getMemberExpCnt() + expCnt);
		memberRecordRepository.save(memberRecord);
	}

	public void updateMemberComment(Long memberNumber, int commentCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setMemberCommentCnt(memberRecord.getMemberCommentCnt() + commentCnt);
		memberRecordRepository.save(memberRecord);
	}

	public void updateMemberRoomJoinCnt(Long memberNumber, int roomJoinCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setMemberRoomJoinCnt(memberRecord.getMemberRoomJoinCnt() + roomJoinCnt);
		memberRecordRepository.save(memberRecord);
	}

	public void updateMemberLikeGiveCnt(Long memberNumber, int likeGiveCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setMemberLikeGiveCnt(memberRecord.getMemberLikeGiveCnt() + likeGiveCnt);
		memberRecordRepository.save(memberRecord);
	}

	public void updateMemberReceiveCnt(Long memberNumber, int likeReceiveCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setMemberExpCnt(memberRecord.getMemberExpCnt() + likeReceiveCnt);
		memberRecordRepository.save(memberRecord);
	}

	public void updateMemberBestCnt(Long memberNumber, int bestCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setMemberBestCnt(memberRecord.getMemberBestCnt() + bestCnt);
		memberRecordRepository.save(memberRecord);
	}

	public void updateMemberFollowingCnt(Long memberNumber, int followingCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setMemberFollowerCnt(memberRecord.getMemberFollowerCnt() + followingCnt);
		memberRecordRepository.save(memberRecord);
	}

	public void updateMemberFollowerCnt(Long memberNumber, int followerCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setMemberFollowerCnt(memberRecord.getMemberFollowerCnt() + followerCnt);
		memberRecordRepository.save(memberRecord);
	}

}
