package com.ssafy.team8alette.domain.bubble.session.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.exception.BubbleNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleListRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleListEntity;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BubbleListService {

	private final MemberRepository memberRepository;
	private final BubbleRepository bubbleRepository;
	private final BubbleListRepository bubbleListRepository;

	public void createBubbleList(Long memberNumber, Long bubbleNumber) {

		Member member = memberRepository.findMemberByMemberNumber(memberNumber);
		BubbleEntity bubble = bubbleRepository.findBubbleEntityByBubbleNumber(bubbleNumber)
			.orElseThrow(() -> new BubbleNotFoundException());

		BubbleListEntity bubbleListEntity = BubbleListEntity.builder()
			.bubbleEntity(bubble)
			.member(member)
			.build();

		bubbleListRepository.save(bubbleListEntity);
	}
}
