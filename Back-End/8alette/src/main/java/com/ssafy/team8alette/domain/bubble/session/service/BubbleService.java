package com.ssafy.team8alette.domain.bubble.session.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.exception.BubbleNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.exception.CategoryNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.model.dto.BubbleDto;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CreateBubbleRequestDto;
import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleParticipantEntity;
import com.ssafy.team8alette.domain.bubble.session.repository.BubbleListRepository;
import com.ssafy.team8alette.domain.bubble.session.repository.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.CategoryEntity;
import com.ssafy.team8alette.domain.bubble.tools.repository.CategoryRepository;
import com.ssafy.team8alette.domain.member.auth.exception.MemberNotExistException;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.global.exception.UnAuthorizedException;
import com.ssafy.team8alette.global.util.S3FileManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BubbleService {

	private final BubbleRepository bubbleRepository;
	private final BubbleListRepository bubbleListRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final S3FileManager s3FileManager;

	public Long createBubble(CreateBubbleRequestDto createBubbleRequestDto) {

		Long hostMemberNumber = createBubbleRequestDto.getHostMemberNumber();
		Long categoryNumber = createBubbleRequestDto.getCategoryNumber();

		String[] fileNames = s3FileManager.saveThumbnailImage(createBubbleRequestDto.getBubbleThumbnail());

		Member member = memberRepository.findMemberByMemberNumber(hostMemberNumber)
			.orElseThrow(() -> new MemberNotExistException());

		CategoryEntity category = categoryRepository.findCategoryEntityByCategoryNumber(categoryNumber)
			.orElseThrow(() -> new CategoryNotFoundException());

		BubbleEntity bubble = BubbleEntity.builder()
			.bubbleTitle(createBubbleRequestDto.getBubbleTitle())
			.bubbleContent((createBubbleRequestDto.getBubbleContent()))
			.bubbleThumbnail(fileNames[1])
			.bubbleType(createBubbleRequestDto.isBubbleType())
			.bubbleState(true)
			.categoryEntity(category)
			.hostMember(member)
			.planOpenDate(LocalDateTime.parse(createBubbleRequestDto.getPlanOpenDate()))
			.planCloseDate(LocalDateTime.parse(createBubbleRequestDto.getPlanCloseDate()))
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

		bubbleRepository.save(bubble);
	}

	public BubbleDto getBubbleInfo(Long bubbleNumber) {

		BubbleEntity bubble = bubbleRepository.findBubbleEntityByBubbleNumber(bubbleNumber).orElseThrow(
			() -> new BubbleNotFoundException());

		return bubble.convertToDto();
	}

	public List<BubbleDto> getBubbleTalkList() {

		List<BubbleEntity> bubbleTalkListEntity = bubbleRepository.findBubbleEntitiesByBubbleTypeIsTrue()
			.orElse(new ArrayList<>());

		List<BubbleDto> bubbleTalkList = new ArrayList<>();

		for (BubbleEntity bubbleEntity : bubbleTalkListEntity) {
			bubbleTalkList.add(bubbleEntity.convertToDto());
		}

		return bubbleTalkList;
	}

	public List<BubbleDto> getBubblingList() {

		List<BubbleEntity> bubblingListEntity = bubbleRepository.findBubbleEntitiesByBubbleTypeIsFalse()
			.orElse(new ArrayList<>());

		List<BubbleDto> bubblingList = new ArrayList<>();

		for (BubbleEntity bubbleEntity : bubblingListEntity) {
			bubblingList.add(bubbleEntity.convertToDto());
		}

		return bubblingList;
	}

	public List<BubbleDto> getBubblingList(Long memberNumber) {

		List<BubbleParticipantEntity> myBubblingList =
			bubbleListRepository.findBubbleParticipantEntitiesByGroupID_MemberNumber(memberNumber)
				.orElse(new ArrayList<>());

		List<BubbleDto> bubblingList = new ArrayList<>();

		for (BubbleParticipantEntity bubbling : myBubblingList) {
			BubbleDto bubble = bubbleRepository
				.findBubbleEntityByBubbleNumber(bubbling.getBubbleEntity().getBubbleNumber()).get().convertToDto();

			if (bubble.isBubbleState() != true)
				bubblingList.add(bubble);
		}

		bubblingList.sort(
			(bubble1, bubble2) -> bubble1.getPlanOpenDate().isAfter(bubble2.getPlanOpenDate()) == true ? 1 : -1);

		return bubblingList;
	}

	public List<BubbleDto> getAllBubbleRoomList() {

		List<BubbleEntity> allBubbleListEntity = bubbleRepository.findBubbleEntitiesByOrderByCreateDateDesc()
			.orElse(new ArrayList<>());

		List<BubbleDto> bubblingList = new ArrayList<>();

		for (BubbleEntity bubbleEntity : allBubbleListEntity) {
			bubblingList.add(bubbleEntity.convertToDto());
		}

		return bubblingList;

	}
	//
}
