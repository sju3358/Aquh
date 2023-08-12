package com.ssafy.team8alette.domain.feed.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.team8alette.domain.feed.exception.FeedMemberNotMatchException;
import com.ssafy.team8alette.domain.feed.model.dao.FeedRepository;
import com.ssafy.team8alette.domain.feed.model.dto.FeedDto;
import com.ssafy.team8alette.domain.feed.model.dto.entity.FeedEntity;
import com.ssafy.team8alette.domain.feed.model.dto.response.FeedResponseDTO;
import com.ssafy.team8alette.domain.member.auth.exception.MemberNotExistException;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.follow.model.dao.FollowRepository;
import com.ssafy.team8alette.domain.member.record.model.dao.MemberRecordRepository;
import com.ssafy.team8alette.domain.member.record.model.service.MemberRecordService;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolGrantRepository;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.entity.Grant;
import com.ssafy.team8alette.global.exception.NullValueException;
import com.ssafy.team8alette.global.util.S3FileManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedRepository feedRepository;
	private final MemberRepository memberRepository;
	private final MemberRecordService memberRecordService;
	private final FollowRepository followRepository;
	private final SymbolGrantRepository symbolGrantRepository;
	private final MemberRecordRepository memberRecordRepository;
	private final S3FileManager s3FileManager;

	public void registFeed(FeedDto feedDto, MultipartFile file) throws Exception {

		if (feedDto.getMember() == null || feedDto.getMember().getMemberNumber() == null) {
			throw new NullValueException("피드 작성자 정보가 없습니다.");
		}

		Member member = memberRepository.findById(feedDto.getMember().getMemberNumber())
			.orElseThrow(() -> new NullValueException("작성자 정보를 찾을 수 없습니다."));

		String[] fileNames = new String[2];

		if (file != null && file.getName().equals("empty") != true)
			fileNames = s3FileManager.saveFeedImage(file);

		FeedEntity feedEntity = FeedEntity.builder()
			.member(member)
			.title(feedDto.getTitle())
			.content(feedDto.getContent())
			.feedLikeCnt(0)
			.feedImgOrigin(fileNames[0])
			.feedImgTrans(fileNames[1])
			.feedActive(true)
			.build();
		feedRepository.save(feedEntity);

		// 이미지 파일 있을때 없을때
		int exp = fileNames[0].equals("") ? 20 : 50;

		memberRecordService.updateMemberExp(member.getMemberNumber(), exp);
		memberRecordService.updateMemberFeedCnt(member.getMemberNumber(), 1);

	}

	public List<FeedResponseDTO> getFeeds(String orderCriteria) {
		List<FeedEntity> list = null;

		if (orderCriteria.equals("recent")) {
			list = feedRepository.findByFeedActiveOrderByFeedNumberDesc(true);
		} else if (orderCriteria.equals("famous")) {
			list = feedRepository.findByFeedActiveOrderByFeedLikeCntDescAndFeedNumberDesc(true);
		} else {
			list = feedRepository.findByFeedActiveOrderByFeedNumberDesc(true);
		}

		if (list == null || list.isEmpty()) {
			throw new NullValueException("피드가 존재하지 않습니다");
		}
		List<FeedResponseDTO> dtoList = list.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());

		return dtoList;
	}

	// 피드 상세글 불러오기
	public FeedEntity getFeedById(Long feedNumber) {
		if (!feedRepository.existsByFeedNumberAndFeedActive(feedNumber, true)) {
			throw new NullValueException("피드가 존재하지 않습니다");
		}
		FeedEntity existFeedEntity = feedRepository.findFeedByFeedNumber(feedNumber);
		existFeedEntity.setViewCnt(existFeedEntity.getViewCnt() + 1);
		feedRepository.save(existFeedEntity);

		/* 기록 테이블 경험치 추가 */
		memberRecordService.updateMemberExp(existFeedEntity.getMember().getMemberNumber(), 20);
		return existFeedEntity;
	}

	// 피드 삭제
	public void deleteFeed(Long feedNumber) {
		FeedEntity existingFeedEntity = feedRepository.findFeedByFeedNumber(feedNumber);

		existingFeedEntity.setFeedActive(false);

		if (existingFeedEntity.getFeedImgTrans() == null || existingFeedEntity.getFeedImgTrans().isEmpty() == true) {
			memberRecordService.updateMemberExp(existingFeedEntity.getMember().getMemberNumber(), -20);
			memberRecordService.updateMemberFeedCnt(existingFeedEntity.getMember().getMemberNumber(), -1);
		}
		memberRecordService.updateMemberExp(existingFeedEntity.getMember().getMemberNumber(), -50);
		memberRecordService.updateMemberFeedCnt(existingFeedEntity.getMember().getMemberNumber(), -1);
		//
		feedRepository.save(existingFeedEntity);
	}

	public FeedEntity modifyFeed(FeedDto feedDto, MultipartFile file) throws Exception {
		FeedEntity existingFeedEntity = feedRepository.findFeedByFeedNumber(feedDto.getFeedNumber());

		if (existingFeedEntity.getMember().getMemberNumber() != feedDto.getMember().getMemberNumber())
			throw new FeedMemberNotMatchException("회원번호가 일치하지 않습니다.");

		existingFeedEntity.setTitle(feedDto.getTitle());
		existingFeedEntity.setContent(feedDto.getContent());

		String[] fileNames = s3FileManager.saveFeedImage(file);

		existingFeedEntity.setFeedImgOrigin(fileNames[0]);
		existingFeedEntity.setFeedImgTrans(fileNames[1]);

		return feedRepository.save(existingFeedEntity);

	}

	public List<FeedResponseDTO> getFeedsByMemberNumber(Long memberNumber) {
		List<FeedEntity> list = null;

		Member member = memberRepository.findMemberByMemberNumber(memberNumber)
			.orElseThrow(() -> new MemberNotExistException());

		list = feedRepository.findByMemberOrderByFeedNumberDesc(member);

		if (list == null || list.isEmpty()) {
			throw new NullValueException("피드가 존재하지 않습니다");
		}

		List<FeedResponseDTO> responseDTOList = list.stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());

		return responseDTOList;
	}

	public Member getFeedCreatorNumber(Long feedNumber) {
		Member member = feedRepository.findFeedByFeedNumber(feedNumber).getMember();
		return member;
	}

	//심볼 은 추가기능
	public FeedResponseDTO convertToDTO(FeedEntity feedEntity) {

		FeedResponseDTO dto = new FeedResponseDTO();
		dto.setFeedNumber(feedEntity.getFeedNumber());
		dto.setFeedCreatorNumber(feedEntity.getMember().getMemberNumber());
		dto.setTitle(feedEntity.getTitle());
		dto.setContent(feedEntity.getContent());
		dto.setFeedLikeCnt(feedEntity.getFeedLikeCnt());
		dto.setViewCnt(feedEntity.getViewCnt());
		dto.setFeedActive(feedEntity.isFeedActive());
		dto.setFeedImgOrigin(feedEntity.getFeedImgOrigin());
		if (feedEntity.getFeedImgTrans() != null && !feedEntity.getFeedImgTrans().equals("")) {
			dto.setFeedImgTrans(
				"https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + feedEntity.getFeedImgTrans());
		}
		// dto.setCreateDate(feedEntity.getCreateDate());
		dto.setNickName(feedEntity.getMember().getMemberNickname());
		List<Grant> list = symbolGrantRepository.findByMemberRecord_MemberNumberAndActiveStatusOrderBySymbolAsc(
			feedEntity.getMember().getMemberNumber(), true);

		List<String> symbolLinkList = new ArrayList<>();
		for (Grant grant : list) {
			String symbolImgLink =
				"https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/" + grant.getSymbol().getSymbolImgName();
			symbolLinkList.add(symbolImgLink);
		}
		dto.setSymbolLink(symbolLinkList);
		dto.setFollowingCnt(followRepository.countByFollowingMemberNumber(feedEntity.getMember()));
		int exp = memberRecordRepository.findMemberRecordByMemberNumber(feedEntity.getMember().getMemberNumber())
			.getMemberExpCnt();
		int level = convertExpToLevel(exp);
		dto.setLevel(level);
		dto.setExp(exp);
		return dto;
	}

	private int convertExpToLevel(int exp) {
		int level = 1;
		if (exp >= 1000 && exp < 2500) {
			level = 2;
		} else if (exp >= 2500 && exp < 4500) {
			level = 3;
		} else if (exp >= 4500 && exp < 7000) {
			level = 4;
		} else if (exp >= 7000 && exp < 10000) {
			level = 5;
		} else if (exp >= 10000) {
			level = 6;
		}
		return level;
	}

}
