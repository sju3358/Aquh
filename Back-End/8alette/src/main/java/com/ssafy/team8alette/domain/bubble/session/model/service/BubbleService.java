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

	public void closeBubble() {
		//버블 객체 찾아서 state false로 변경
		//버블 세션 모두 삭제
		//버블 참가자 리스트 삭제??????
	}

}
