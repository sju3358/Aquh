package com.ssafy.team8alette.domain.feed.model.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.team8alette.domain.feed.exception.FeedMemberNotMatchException;
import com.ssafy.team8alette.domain.feed.model.dao.FeedRepository;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedRepository feedRepository;
	private final MemberRepository memberRepository;
	private final MemberRecordService memberRecordService;
	private final FollowRepository followRepository;
	private final SymbolGrantRepository symbolGrantRepository;
	private final AmazonS3Client amazonS3Client;
	private final MemberRecordRepository memberRecordRepository;

	@Value("${spring.data.couchbase.bucket-name}/feed_img")
	private String bucket;

	public void registFeed(FeedEntity feedEntity, MultipartFile file) throws Exception {
		if (feedEntity.getMember() == null || feedEntity.getMember().getMemberNumber() == null) {
			throw new NullValueException("피드 작성자 정보가 없습니다.");
		}
		Member member = memberRepository.findById(feedEntity.getMember().getMemberNumber()).orElse(null);
		if (member == null) {
			throw new NullValueException("작성자 정보를 찾을 수 없습니다.");
		}
		if (!file.isEmpty()) {
			Date nowDate = new Date();
			// 파일명 : 현재일시_랜덤6자리
			String fileName = dateToString(nowDate) + '_' + getRandNum();

			// AWS S3 파일 저장
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());
			amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

			feedEntity.setFeedActive(true);
			feedEntity.setFeedLikeCnt(0);
			feedEntity.setFeedImgOrigin(file.getOriginalFilename());
			feedEntity.setFeedImgTrans(fileName);
			feedEntity.setCreateDate(nowDate);
			feedEntity.setMember(member);
			feedRepository.save(feedEntity);
			/* 기록 테이블 경험치 추가 */
			memberRecordService.updateMemberExp(member.getMemberNumber(), 50);
			memberRecordService.updateMemberFeedCnt(member.getMemberNumber(), 1);

		} else {
			Date nowDate = new Date();
			feedEntity.setFeedActive(true);
			feedEntity.setFeedLikeCnt(0);
			feedEntity.setCreateDate(nowDate);
			feedEntity.setMember(member);
			feedRepository.save(feedEntity);

			/* 기록 테이블 경험치 추가 */
			memberRecordService.updateMemberExp(member.getMemberNumber(), 20);
			memberRecordService.updateMemberFeedCnt(member.getMemberNumber(), 1);
		}
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

		if (existingFeedEntity.getFeedImgTrans().isEmpty()) {
			memberRecordService.updateMemberExp(existingFeedEntity.getMember().getMemberNumber(), -20);
			memberRecordService.updateMemberFeedCnt(existingFeedEntity.getMember().getMemberNumber(), -1);
		}
		memberRecordService.updateMemberExp(existingFeedEntity.getMember().getMemberNumber(), -50);
		memberRecordService.updateMemberFeedCnt(existingFeedEntity.getMember().getMemberNumber(), -1);

		feedRepository.save(existingFeedEntity);

	}

	// 피드 수정
	public FeedEntity modifyFeed(FeedEntity feedEntity, MultipartFile file) throws Exception {
		FeedEntity existingFeedEntity = feedRepository.findFeedByFeedNumber(feedEntity.getFeedNumber());

		if (existingFeedEntity.getMember().getMemberNumber() == feedEntity.getMember().getMemberNumber()) {
			existingFeedEntity.setTitle(feedEntity.getTitle());
			existingFeedEntity.setContent(feedEntity.getContent());

			if (file.isEmpty()) {
				existingFeedEntity.setFeedImgOrigin(null);
				existingFeedEntity.setFeedImgTrans(null);
				return feedRepository.save(existingFeedEntity);
			} else {
				Date nowDate = new Date();
				/* 파일명 : 현재일시_랜덤6자리*/
				String fileName = dateToString(nowDate) + '_' + getRandNum();

				// AWS S3 파일 저장
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(file.getContentType());
				metadata.setContentLength(file.getSize());
				amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

				existingFeedEntity.setFeedImgOrigin(file.getOriginalFilename()); //원본 이미지 이름명으로 저장
				existingFeedEntity.setFeedImgTrans(fileName); //멤버 이미지변환명으로 저장

				return feedRepository.save(existingFeedEntity);
			}
		} else {
			throw new FeedMemberNotMatchException("회원번호가 일치하지 않습니다.");
		}
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

	private String getRandNum() {
		Random generator = new java.util.Random();
		generator.setSeed(System.currentTimeMillis());
		return String.format("%06d", generator.nextInt(1000000) % 1000000);
	}

	private String dateToString(Date nowDate) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(nowDate);
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
		if (feedEntity.getFeedImgTrans() != null) {
			dto.setFeedImgTrans(
				"https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + feedEntity.getFeedImgTrans());
		}
		dto.setCreateDate(feedEntity.getCreateDate());
		dto.setNickName(feedEntity.getMember().getMemberNickname());
		List<Grant> list = symbolGrantRepository.findByMemberRecord_MemberNumberAndActiveStatusOrderBySymbolAsc(
			feedEntity.getFeedNumber(), true);

		List<String> symbolLinkList = new ArrayList<>();
		for (Grant grant : list) {
			String symbolImgLink =
				"https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/" + grant.getSymbol().getSymbolImgName();
			symbolLinkList.add(symbolImgLink);
		}
		// dto.setSymbolLink(list);
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
