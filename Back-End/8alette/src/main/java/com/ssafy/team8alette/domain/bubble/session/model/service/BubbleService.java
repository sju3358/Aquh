package com.ssafy.team8alette.domain.bubble.session.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.exception.BubbleNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.exception.CategoryNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CreateBubbleRequest;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.CategoryRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.CategoryEntity;
import com.ssafy.team8alette.domain.member.auth.exception.MemberNotExistException;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.global.exception.UnAuthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BubbleService {

	private final BubbleRepository bubbleRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;

	public Long createBubble(CreateBubbleRequest createBubbleRequest) {

		Long hostMemberNumber = createBubbleRequest.getHostMemberNumber();
		Long categoryNumber = createBubbleRequest.getCategoryNumber();

		Member member = memberRepository.findMemberByMemberNumber(hostMemberNumber)
			.orElseThrow(() -> new MemberNotExistException());

		CategoryEntity category = categoryRepository.findCategoryEntityByCategoryNumber(categoryNumber)
			.orElseThrow(() -> new CategoryNotFoundException());

		//오픈예정시간, 종료예정시간 추가해야함
		BubbleEntity bubble = BubbleEntity.builder()
			.bubbleTitle(createBubbleRequest.getBubbleTitle())
			.bubbleContent((createBubbleRequest.getBubbleContent()))
			.bubbleThumbnail(createBubbleRequest.getBubbleThumbnail())
			.bubbleType(createBubbleRequest.isBubbleType())
			.bubbleState(true)
			.categoryEntity(category)
			.hostMember(member)
			.build();

		bubbleRepository.save(bubble);

		return bubble.getBubbleNumber();
	}

	public void closeBubble(Long bubbleNumber, Long hostMemberNumber) {

		BubbleEntity bubble = bubbleRepository.findBubbleEntityByBubbleNumber(bubbleNumber)
			.orElseThrow(() -> new BubbleNotFoundException());

		if (bubble.getHostMember().getMemberNumber() != hostMemberNumber)
			throw new UnAuthorizedException();

		bubble.setBubbleState(false);
		bubble.setCloseDate(LocalDateTime.now());
	}

	public BubbleEntity getBubbleInfo(Long bubbleNumber) {
		return bubbleRepository.findBubbleEntityByBubbleNumber(bubbleNumber).orElseThrow(
			() -> new BubbleNotFoundException());
	}

	public List<BubbleEntity> getBubbleTalkList() {

		return bubbleRepository.findBubbleEntitiesByBubbleTypeIsTrue()
			.orElse(new ArrayList<>());
	}

	public List<BubbleEntity> getBubblingList() {

		return bubbleRepository.findBubbleEntitiesByBubbleTypeIsFalse()
			.orElse(new ArrayList<>());
	}

}
