package com.ssafy.team8alette.domain.member.record.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ssafy.team8alette.domain.member.auth.exception.MemberNotExistException;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.record.model.dao.MemberRecordRepository;
import com.ssafy.team8alette.domain.member.record.model.dto.entity.MemberRecord;
import com.ssafy.team8alette.domain.member.record.model.dto.response.MemberRecordDTO;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolGrantRepository;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.entity.Grant;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.key.GrantID;
import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberRecordService {

	private final MemberRecordRepository memberRecordRepository;
	private final MemberRepository memberRepository;
	private final SymbolGrantRepository symbolGrantRepository;

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

	public void updateMemberFeedCnt(Long memberNumber, int feedCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setMemberFeedCnt(memberRecord.getMemberFeedCnt() + feedCnt);
		memberRecordRepository.save(memberRecord);
	}

	public void updateMemberRoomJoinCnt(Long memberNumber, int roomJoinCnt) {
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		memberRecord.setBubbleJoinCnt(memberRecord.getBubbleJoinCnt() + roomJoinCnt);
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

	public MemberRecordDTO getMemberRecordDetail(Long memberNumber) {
		Member member = memberRepository.findMemberByMemberNumber(memberNumber)
			.orElseThrow(() -> new MemberNotExistException());
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(memberNumber);
		List<Grant> grantMember = symbolGrantRepository.findByGrantIDGrantedMemberNumberOrderBySymbolAsc(memberNumber);
		List<Symbol> symbolsWithSymbolNumberFive = new ArrayList<>();

		for (Grant grant : grantMember) {
			GrantID grantID = grant.getGrantID();
			if (grantID.getSymbolNumber() <= 5) {
				symbolsWithSymbolNumberFive.add(grant.getSymbol());
			}
		}
		Symbol latestSymbol = null;
		if (!symbolsWithSymbolNumberFive.isEmpty()) {
			latestSymbol = symbolsWithSymbolNumberFive.get(symbolsWithSymbolNumberFive.size() - 1);
		}

		MemberRecordDTO dto = new MemberRecordDTO();
		dto.setMemberNickName(member.getMemberNickname());
		dto.setMemberIntro(member.getMemberIntro());
		dto.setLevel(latestSymbol.getSymbolNumber());

		if (memberRecord.getMemberExpCnt() < 1000) {
			dto.setRemainingExp(1000 - memberRecord.getMemberExpCnt());
			dto.setMaxExp(1000);
		} else if (memberRecord.getMemberExpCnt() >= 1000 && memberRecord.getMemberExpCnt() < 2500) {
			dto.setRemainingExp(2500 - memberRecord.getMemberExpCnt());
			dto.setMaxExp(2500);
		} else if (memberRecord.getMemberExpCnt() >= 2500 && memberRecord.getMemberExpCnt() < 4500) {
			dto.setRemainingExp(4500 - memberRecord.getMemberExpCnt());
			dto.setMaxExp(4500);
		} else if (memberRecord.getMemberExpCnt() >= 4500 && memberRecord.getMemberExpCnt() < 7000) {
			dto.setRemainingExp(7000 - memberRecord.getMemberExpCnt());
			dto.setMaxExp(7000);
		} else if (memberRecord.getMemberExpCnt() >= 7000 && memberRecord.getMemberExpCnt() < 10000) {
			dto.setRemainingExp(10000 - memberRecord.getMemberExpCnt());
			dto.setMaxExp(10000);
		} else if (memberRecord.getMemberExpCnt() >= 10000) {
			dto.setRemainingExp(100000 - memberRecord.getMemberExpCnt());
			dto.setLevel(6L);
			dto.setMaxExp(100000);
		}
		dto.setPresentExp(memberRecord.getMemberExpCnt());

		return dto;
	}

	// 레벨 구하는 서비스 메서드
	public int getMemberLevel(Long memberNumber) {
		Member member = memberRepository.findMemberByMemberNumber(memberNumber)
			.orElseThrow(() -> new MemberNotExistException());
		MemberRecord memberRecord = memberRecordRepository.findMemberRecordByMemberNumber(member.getMemberNumber());

		int exp = memberRecord.getMemberExpCnt();

		if (exp < 1000) {
			return 1;
		} else if (exp >= 1000 && exp < 2500) {
			return 2;
		} else if (exp >= 2500 && exp < 4500) {
			return 3;
		} else if (exp >= 4500 && exp < 7000) {
			return 4;
		} else return 6;
	}
}
