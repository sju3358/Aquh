package com.ssafy.team8alette.domain.bubble.session.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.exception.BubbleNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.exception.BubbleParticipantsNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleListRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleParticipantEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.key.GroupID;
import com.ssafy.team8alette.domain.member.auth.exception.MemberNotExistException;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BubbleParticipantService {

	private final MemberRepository memberRepository;
	private final BubbleRepository bubbleRepository;
	private final BubbleListRepository bubbleListRepository;

	public void createBubbleList(Long memberNumber, Long bubbleNumber) {

		Member member = memberRepository.findMemberByMemberNumber(memberNumber)
			.orElseThrow(() -> new MemberNotExistException());

		BubbleEntity bubble = bubbleRepository.findBubbleEntityByBubbleNumber(bubbleNumber)
			.orElseThrow(() -> new BubbleNotFoundException());

		GroupID groupID = new GroupID();
		groupID.setBubbleNumber(bubbleNumber);
		groupID.setMemberNumber(memberNumber);

		BubbleParticipantEntity bubbleParticipantEntity = BubbleParticipantEntity.builder()
			.groupID(groupID)
			.bubbleEntity(bubble)
			.member(member)
			.build();

		bubbleListRepository.save(bubbleParticipantEntity);
	}

	public void removeBubbleList(Long memberNumber, Long bubbleNumber) {

		GroupID groupID = new GroupID();
		groupID.setBubbleNumber(bubbleNumber);
		groupID.setMemberNumber(memberNumber);

		BubbleParticipantEntity bubbleParticipant = bubbleListRepository.findBubbleListEntitiesByGroupID(groupID)
			.orElseThrow(() -> new BubbleParticipantsNotFoundException());

		bubbleListRepository.delete(bubbleParticipant);
	}

}
