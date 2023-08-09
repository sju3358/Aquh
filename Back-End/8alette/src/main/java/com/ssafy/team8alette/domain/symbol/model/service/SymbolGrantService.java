package com.ssafy.team8alette.domain.symbol.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolGrantRepository;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.entity.Grant;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.request.SymbolGrantRequestDTO;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.response.GrantResponseDTO;
import com.ssafy.team8alette.global.exception.NullValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SymbolGrantService {

	private final SymbolGrantRepository symbolGrantRepository;
	private final MemberRepository memberRepository;

	public List<GrantResponseDTO> getGrantList(Long memberNumber) {
		Member member = memberRepository.findById(memberNumber)
			.orElseThrow(() -> new NullValueException("회원 정보가 존재하지 않습니다."));

		List<Grant> list = symbolGrantRepository.findByMemberRecord_MemberNumber(memberNumber);
		List<GrantResponseDTO> dtoList = list.stream()
			.map(grant -> new GrantResponseDTO(grant.getSymbol().getSymbolNumber(), grant.getSymbol().getSymbolName(),
				"https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/" + grant.getSymbol().getSymbolImgName(),
				grant.getSymbol().getSymbolCode(),
				grant.getSymbol().getSymbolConditionCnt(), grant.isActiveStatus(), grant.getDate()))
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

}
