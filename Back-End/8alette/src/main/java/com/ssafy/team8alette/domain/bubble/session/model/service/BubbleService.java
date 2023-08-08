package com.ssafy.team8alette.domain.bubble.session.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.exception.CategoryNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.CategoryEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CreateBubbleRequest;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.CategoryRepository;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

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

		Member member = memberRepository.findMemberByMemberNumber(hostMemberNumber);

		CategoryEntity category = categoryRepository.findCategoryEntityByCategoryNumber(categoryNumber)
			.orElseThrow(() -> new CategoryNotFoundException());

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

	private void openBubble() {

	}

	private void closeBubble() {

	}

}
