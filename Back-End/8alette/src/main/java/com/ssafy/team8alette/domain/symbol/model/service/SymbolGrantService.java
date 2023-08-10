package com.ssafy.team8alette.domain.symbol.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.record.model.dto.entity.MemberRecord;
import com.ssafy.team8alette.domain.member.record.model.service.MemberRecordService;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolGrantRepository;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolRepository;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.entity.Grant;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.key.GrantID;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.request.SymbolGrantRequestDTO;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.GrantResponseDTO;
import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;
import com.ssafy.team8alette.global.exception.NullValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SymbolGrantService {

	private final SymbolGrantRepository symbolGrantRepository;
	private final SymbolRepository symbolRepository;
	private final MemberRepository memberRepository;
	private final MemberRecordService memberRecordService;

	public List<GrantResponseDTO> getGrantList(Long memberNumber) {
		Member member = memberRepository.findById(memberNumber)
			.orElseThrow(() -> new NullValueException("회원 정보가 존재하지 않습니다."));

		//심볼 부여
		putSymbolGrant(memberNumber);

		List<Grant> list = symbolGrantRepository.findByMemberRecord_MemberNumber(memberNumber);
		List<GrantResponseDTO> dtoList = list.stream()
			.map(grant -> new GrantResponseDTO(grant.getSymbol().getSymbolNumber(), grant.getSymbol().getSymbolName(),
				"https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/" + grant.getSymbol().getSymbolImgName(),
				grant.getSymbol().getSymbolCode(),
				grant.getSymbol().getSymbolConditionCnt(), grant.isActiveStatus()))
			.collect(
				Collectors.toList());
		return dtoList;
	}

	public void putActiveTrue(SymbolGrantRequestDTO symbolGrantRequestDTO) {
		Long memberNumber = symbolGrantRequestDTO.getMemberNumber();
		//
		List<Grant> list = symbolGrantRepository.findByGrantIDGrantedMemberNumber(memberNumber);
		if (list != null) {
			for (Grant setList : list) {
				setList.setActiveStatus(false);
				symbolGrantRepository.save(setList);
			}
		}
		Long one = symbolGrantRequestDTO.getSelectNumberOne();
		if (one != 0) {
			Grant grant = symbolGrantRepository.findByGrantIDGrantedMemberNumberAndGrantIDSymbolNumber(memberNumber,
				one);
			grant.setActiveStatus(true);
			symbolGrantRepository.save(grant);
		}
		Long two = symbolGrantRequestDTO.getSelectNumberTwo();
		if (two != 0) {
			Grant grant = symbolGrantRepository.findByGrantIDGrantedMemberNumberAndGrantIDSymbolNumber(memberNumber,
				two);
			grant.setActiveStatus(true);
			symbolGrantRepository.save(grant);
		}
		Long three = symbolGrantRequestDTO.getSelectNumberThree();
		if (three != 0) {
			Grant grant = symbolGrantRepository.findByGrantIDGrantedMemberNumberAndGrantIDSymbolNumber(memberNumber,
				three);
			grant.setActiveStatus(true);
			symbolGrantRepository.save(grant);
		}
		Long four = symbolGrantRequestDTO.getSelectNumberFour();
		if (four != 0) {
			Grant grant = symbolGrantRepository.findByGrantIDGrantedMemberNumberAndGrantIDSymbolNumber(memberNumber,
				four);
			grant.setActiveStatus(true);
			symbolGrantRepository.save(grant);
		}
		Long five = symbolGrantRequestDTO.getSelectNumberFive();
		if (five != 0) {
			Grant grant = symbolGrantRepository.findByGrantIDGrantedMemberNumberAndGrantIDSymbolNumber(memberNumber,
				five);
			grant.setActiveStatus(true);
			symbolGrantRepository.save(grant);
		}
	}

	// public void putSymbolGrant(Long memberNumber, Long symbolNumber) {
	// 	Grant symbolGrant = new Grant();
	// 	GrantID grantID = new GrantID();
	// 	grantID.setGrantedMemberNumber(memberNumber);
	// 	grantID.setSymbolNumber(symbolNumber);
	// 	symbolGrant.setGrantID(grantID);
	// 	symbolGrantRepository.save(symbolGrant);
	// }

	public void putSymbolGrant(Long memberNumber) {
		//기록을 뽑아오고
		MemberRecord memberRecord = memberRecordService.getMemberRecord(memberNumber);

		int[] valuesToCheck = {
			memberRecord.getMemberExpCnt(),
			memberRecord.getBubbleJoinCnt(),
			memberRecord.getMemberBestCnt(),
			memberRecord.getMemberFeedCnt(),
			memberRecord.getMemberLikeGiveCnt(),
			memberRecord.getMemberLikeReceiveCnt(),
			memberRecord.getMemberFollowingCnt(),
			memberRecord.getMemberFollowerCnt()
		};

		List<Long> giveSymbolNumber = new ArrayList<>();

		String[] symbolCode = {
			"exp_cnt", "bubble_join_cnt", "best_cnt",
			"feed_cnt", "like_give_cnt", "like_receive_cnt",
			"following_cnt", "follower_cnt"};

		for (int i = 0; i < symbolCode.length; i++) {
			if (valuesToCheck[i] != 0) {
				symbolRepository.findAllBySymbolCodeAndAndSymbolConditionCntLessThan(
					symbolCode[i],
					valuesToCheck[i]).ifPresent(symbol -> {
					Long symbolNumber = symbol.getSymbolNumber();
					giveSymbolNumber.add(symbolNumber);
				});
			}
		}
		int ExpCnt = 0;
		for (Long symbolNumber : giveSymbolNumber) {
			if (symbolGrantRepository.findByGrantIDGrantedMemberNumberAndGrantIDSymbolNumber(memberNumber, symbolNumber)
				== null) {
				ExpCnt++;
				Symbol symbol = symbolRepository.findSymbolBySymbolNumber(symbolNumber);

				GrantID grantID = new GrantID();
				grantID.setGrantedMemberNumber(memberNumber);
				grantID.setSymbolNumber(symbolNumber);

				Grant newSymbolGrant = new Grant();
				newSymbolGrant.setGrantID(grantID);
				newSymbolGrant.setMemberRecord(memberRecord);
				newSymbolGrant.setSymbol(symbol);
				newSymbolGrant.setActiveStatus(false);

				symbolGrantRepository.save(newSymbolGrant);

			}
		}
		memberRecordService.updateMemberExp(memberNumber, ExpCnt * 100);

	}

}
