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
import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.GrantResponseDTO;
import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;
import com.ssafy.team8alette.global.exception.NullValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SymbolService {

	private final SymbolRepository symbolRepository;
	private final SymbolGrantRepository symbolGrantRepository;
	private final MemberRepository memberRepository;
	private final MemberRecordService memberRecordService;

	public List<GrantResponseDTO> getRequiredSymbolList(Long memberNumber) {
		Member member = memberRepository.findById(memberNumber)
			.orElseThrow(() -> new NullValueException("회원 정보가 존재하지 않습니다."));

		//심볼 부여
		putSymbolGrant(memberNumber);

		List<Grant> list = symbolGrantRepository.findByMemberRecord_MemberNumberOrderBySymbolAsc(memberNumber);
		List<GrantResponseDTO> dtoList = list.stream()
			.map(grant -> new GrantResponseDTO(grant.getSymbol().getSymbolNumber(), grant.getSymbol().getSymbolName(),
				"https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/" + grant.getSymbol().getSymbolImgName(),
				grant.getSymbol().getSymbolCode(),
				grant.getSymbol().getSymbolConditionCnt(), grant.isActiveStatus()))
			.collect(
				Collectors.toList());
		return dtoList;
	}

	public List<GrantResponseDTO> getRemainList(Long memberNumber) {
		Member member = memberRepository.findById(memberNumber)
			.orElseThrow(() -> new NullValueException("회원 정보가 존재하지 않습니다."));

		List<Grant> list = symbolGrantRepository.findByMemberRecord_MemberNumberOrderBySymbolAsc(memberNumber);
		List<Long> usedSymbolNumbers = list.stream()
			.map(grant -> grant.getSymbol().getSymbolNumber())
			.collect(Collectors.toList());

		List<Symbol> remainList = symbolRepository.findAll().stream()
			.filter(symbol -> !usedSymbolNumbers.contains(symbol.getSymbolNumber()))
			.collect(Collectors.toList());

		List<GrantResponseDTO> remainDtoList = remainList.stream()
			.map(symbol -> new GrantResponseDTO(symbol.getSymbolNumber(), symbol.getSymbolName(),
				"https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/" + symbol.getSymbolImgName(),
				symbol.getSymbolCode(), symbol.getSymbolConditionCnt(), false))
			.collect(Collectors.toList());

		return remainDtoList;
	}

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
				List<Symbol> symbols = symbolRepository.findAllBySymbolCodeAndAndSymbolConditionCntLessThanEqual(
					symbolCode[i],
					valuesToCheck[i]);

				int ExpCnt = 0;
				for (Symbol symbol : symbols) {
					Long symbolNumber = symbol.getSymbolNumber();
					giveSymbolNumber.add(symbolNumber);
					//
					if (symbolGrantRepository.findByGrantIDGrantedMemberNumberAndGrantIDSymbolNumber(memberNumber,
						symbolNumber) == null) {
						ExpCnt++;
						Symbol putSymbol = symbolRepository.findSymbolBySymbolNumber(symbolNumber);

						GrantID grantID = new GrantID();
						grantID.setGrantedMemberNumber(memberNumber);
						grantID.setSymbolNumber(symbolNumber);

						Grant newSymbolGrant = new Grant();
						newSymbolGrant.setGrantID(grantID);
						newSymbolGrant.setMemberRecord(memberRecord);
						newSymbolGrant.setSymbol(putSymbol);
						newSymbolGrant.setActiveStatus(false);

						symbolGrantRepository.save(newSymbolGrant);
					}

				}
				memberRecordService.updateMemberExp(memberNumber, ExpCnt * 100);

			}
		}
	}
}
