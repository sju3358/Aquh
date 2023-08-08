package com.ssafy.team8alette.domain.symbol.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolGrantRepository;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.entity.Grant;
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
				grant.getSymbol().getSymbolImgName(), grant.getSymbol().getSymbolCode(),
				grant.getSymbol().getSymbolConditionCnt(), grant.getDate()))
			.collect(
				Collectors.toList());
		return dtoList;
	}

}
