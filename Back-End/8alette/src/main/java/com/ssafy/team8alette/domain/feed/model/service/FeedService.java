package com.ssafy.team8alette.domain.feed.model.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.team8alette.domain.feed.model.dao.FeedRepository;
import com.ssafy.team8alette.domain.feed.model.dto.entity.FeedEntity;
import com.ssafy.team8alette.domain.feed.model.dto.response.FeedResponseDTO;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.follow.model.dao.FollowRepository;
import com.ssafy.team8alette.domain.member.record.model.dao.MemberRecordRepository;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolRepository;
import com.ssafy.team8alette.global.exception.NotMatchException;
import com.ssafy.team8alette.global.exception.NullValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedRepository feedRepository;
	private final MemberRepository memberRepository;
	private final MemberRecordRepository memberRecordRepository;
	private final FollowRepository followRepository;
	private final SymbolRepository symbolRepository;

	private final AmazonS3Client amazonS3Client;

	@Value("${spring.data.couchbase.bucket-name}/feed_img")
	private String bucket;

	private static String projectPath = "/home/ubuntu/spring-upload-images";
	// private static String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\files";
	// FileSystemResource fileSystemResource = new FileSystemResource("resources/pictures/");

	public void registFeed(FeedEntity feedEntity, MultipartFile file) throws Exception {
		if (feedEntity.getMember() == null || feedEntity.getMember().getMemberNumber() == null) {
			throw new NullValueException("피드 작성자 정보가 없습니다.");
		}
		Member member = memberRepository.findById(feedEntity.getMember().getMemberNumber()).orElse(null);
		if (member == null) {
			throw new NullValueException("작성자 정보를 찾을 수 없습니다.");
		}

		// 이미지 데이터 처리
		if (!file.isEmpty()) {

			Date nowDate = new Date(); // 현재 일시

			// 파일명 : 현재일시_랜덤6자리
			String fileName = dateToString(nowDate) + '_' + getRandNum();

			//빈껍데기 생성해서 피드 저장소에 이미지 전달
			// File saveFile = new File(projectPath, fileName);

			// File foler = new File(projectPath);
			// if (foler.exists() != true)
			// 	foler.mkdirs();
			//
			// File saveFile = new File(projectPath, fileName.toString());
			//
			// file.transferTo(saveFile);

			// AWS S3 파일 저장
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());
			amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

			feedEntity.setFeedActive(true);
			feedEntity.setFeedLikeCnt(0);

			//원본 이미지 이름명으로 저장
			feedEntity.setFeedImgOrigin(file.getOriginalFilename());
			//멤버 이미지변환명으로 저장
			feedEntity.setFeedImgTrans(fileName);
			feedEntity.setCreateDate(nowDate);
			feedEntity.setMember(member);

			feedRepository.save(feedEntity);
		} else {
			Date nowDate = new Date();
			feedEntity.setFeedActive(true);
			feedEntity.setFeedLikeCnt(0);
			feedEntity.setCreateDate(nowDate);
			feedEntity.setMember(member);
			feedRepository.save(feedEntity);
		}

	}

	public List<FeedEntity> getFeeds(String orderCriteria) {
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

		return list;
	}

	// 피드 상세글 불러오기
	public FeedEntity getFeedById(Long feedNumber) {
		if (!feedRepository.existsByFeedNumberAndFeedActive(feedNumber, true)) {
			throw new NullValueException("피드가 존재하지 않습니다");
		}
		FeedEntity existFeedEntity = feedRepository.findFeedByFeedNumber(feedNumber);
		existFeedEntity.setViewCnt(existFeedEntity.getViewCnt() + 1);
		feedRepository.save(existFeedEntity);
		return existFeedEntity;
	}

	// 피드 삭제
	public void deleteFeed(Long feedNumber) {

		//파일도 삭제해주고 아이디에 관련된 데이터 삭제
		FeedEntity existingFeedEntity = feedRepository.findFeedByFeedNumber(feedNumber);
		existingFeedEntity.setFeedActive(false);
		// if (existingFeed.getFeedImgTrans() != null) {
		// File f = new File(projectPath, existingFeed.getFeedImgTrans());
		// f.delete();
		// }
		feedRepository.save(existingFeedEntity);
	}

	// 피드 수정
	public FeedEntity modifyFeed(FeedEntity feedEntity, MultipartFile file) throws Exception {
		FeedEntity existingFeedEntity = feedRepository.findFeedByFeedNumber(feedEntity.getFeedNumber());

		if (existingFeedEntity.getMember().getMemberNumber() == feedEntity.getMember().getMemberNumber()) {
			existingFeedEntity.setTitle(feedEntity.getTitle());
			existingFeedEntity.setContent(feedEntity.getContent());

			if (file.isEmpty()) {
				//이 파일경로에 있는 원본 파일 삭제(있으면)
				if (existingFeedEntity.getFeedImgTrans() != null) {
					// File f = new File(projectPath, existingFeed.getFeedImgTrans());
					// f.delete();
					// amazonS3Client.deleteObject(bucket, existingFeed.getFeedImgTrans());
				}
				existingFeedEntity.setFeedImgOrigin(null);
				existingFeedEntity.setFeedImgTrans(null);
				return feedRepository.save(existingFeedEntity);
			} else {
				//이 파일경로에 있는 원본 파일 삭제(있으면)
				if (existingFeedEntity.getFeedImgTrans() != null) {
					// File f = new File(projectPath, existingFeed.getFeedImgTrans());
					// f.delete();
					// amazonS3Client.deleteObject(bucket, existingFeed.getFeedImgTrans());
				}

				Date nowDate = new Date(); // 현재 일시

				// 파일명 : 현재일시_랜덤6자리
				String fileName = dateToString(nowDate) + '_' + getRandNum();

				// AWS S3 파일 저장
				ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentType(file.getContentType());
				metadata.setContentLength(file.getSize());
				amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

				//빈껍데기 생성해서 피드 저장소에 이미지 전달
				// File saveFile = new File(projectPath, fileName);
				// file.transferTo(saveFile);

				existingFeedEntity.setFeedImgOrigin(file.getOriginalFilename()); //원본 이미지 이름명으로 저장
				existingFeedEntity.setFeedImgTrans(fileName); //멤버 이미지변환명으로 저장

				return feedRepository.save(existingFeedEntity);
			}
		} else {
			throw new NotMatchException("회원번호가 일치하지 않습니다.");
		}
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
		dto.setFeedImgTrans("https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/" + feedEntity.getFeedImgTrans());
		dto.setCreateDate(feedEntity.getCreateDate());
		dto.setNickName(feedEntity.getMember().getMemberNickname());

		//사실 이부분은 무조건 기록되어져야함 없으면 오류
		dto.setFollowingCnt(followRepository.countByFollowingMemberNumber(feedEntity.getMember()));
		int exp = memberRecordRepository.findMemberRecordByMemberNumber(feedEntity.getMember().getMemberNumber())
			.getMemberExpCnt();
		String imgLink = "";
		imgLink = "https://aquh.s3.ap-northeast-2.amazonaws.com/feed_img/"
			+ symbolRepository.findFirstBySymbolConditionCntIsLessThanEqualOrderBySymbolConditionCntDesc(exp)
			.getSymbolImgName();
		dto.setCharacterName(imgLink);
		dto.setExp(exp);
		return dto;
	}
}
