package com.ssafy.team8alette.feed.model.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.team8alette.feed.exception.NotMatchException;
import com.ssafy.team8alette.feed.exception.NullValueException;
import com.ssafy.team8alette.feed.model.dao.FeedRepository;
import com.ssafy.team8alette.feed.model.dto.Feed.Entity.Feed;
import com.ssafy.team8alette.feed.model.dto.Feed.Response.FeedResponseDTO;
import com.ssafy.team8alette.member.model.dao.MemberRepository;
import com.ssafy.team8alette.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedRepository feedRepository;
	private final MemberRepository memberRepository;
	private final AmazonS3Client amazonS3Client;

	@Value("${spring.data.couchbase.bucket-name}")
	private String bucket;

	private static String projectPath = "/home/ubuntu/spring-upload-images";
	// private static String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\files";
	// FileSystemResource fileSystemResource = new FileSystemResource("resources/pictures/");

	public void registFeed(Feed feed, MultipartFile file) throws Exception {
		if (feed.getMember() == null || feed.getMember().getMemberNumber() == null) {
			throw new NullValueException("피드 작성자 정보가 없습니다.");
		}
		Member member = memberRepository.findById(feed.getMember().getMemberNumber()).orElse(null);
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

			feed.setFeedActive(true);
			feed.setFeedLikeCnt(0);

			//원본 이미지 이름명으로 저장
			feed.setFeedImgOrigin(file.getOriginalFilename());
			//멤버 이미지변환명으로 저장
			feed.setFeedImgTrans(fileName);
			feed.setCreateDate(nowDate);
			feed.setMember(member);

			feedRepository.save(feed);
		} else {
			Date nowDate = new Date();
			feed.setFeedActive(true);
			feed.setFeedLikeCnt(0);
			feed.setCreateDate(nowDate);
			feed.setMember(member);
			feedRepository.save(feed);
		}

	}

	public List<Feed> getFeeds(String orderCriteria) {
		List<Feed> list = null;

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
	public Feed getFeedById(Long feedNumber) {
		if (!feedRepository.existsByFeedNumberAndFeedActive(feedNumber, true)) {
			throw new NullValueException("피드가 존재하지 않습니다");
		}
		Feed existFeed = feedRepository.findFeedByFeedNumber(feedNumber);
		existFeed.setViewCnt(existFeed.getViewCnt() + 1);
		feedRepository.save(existFeed);
		return existFeed;
	}

	// 피드 삭제
	public void deleteFeed(Long feedNumber) {

		//파일도 삭제해주고 아이디에 관련된 데이터 삭제
		Feed existingFeed = feedRepository.findFeedByFeedNumber(feedNumber);
		existingFeed.setFeedActive(false);
		// if (existingFeed.getFeedImgTrans() != null) {
		// File f = new File(projectPath, existingFeed.getFeedImgTrans());
		// f.delete();
		// }
		feedRepository.save(existingFeed);
	}

	// 피드 수정
	public Feed modifyFeed(Feed feed, MultipartFile file) throws Exception {
		Feed existingFeed = feedRepository.findFeedByFeedNumber(feed.getFeedNumber());

		if (existingFeed.getMember().getMemberNumber() == feed.getMember().getMemberNumber()) {
			existingFeed.setTitle(feed.getTitle());
			existingFeed.setContent(feed.getContent());

			if (file.isEmpty()) {
				//이 파일경로에 있는 원본 파일 삭제(있으면)
				if (existingFeed.getFeedImgTrans() != null) {
					// File f = new File(projectPath, existingFeed.getFeedImgTrans());
					// f.delete();
					amazonS3Client.deleteObject(bucket, existingFeed.getFeedImgTrans());
				}
				existingFeed.setFeedImgOrigin(null);
				existingFeed.setFeedImgTrans(null);
				return feedRepository.save(existingFeed);
			} else {
				//이 파일경로에 있는 원본 파일 삭제(있으면)
				if (existingFeed.getFeedImgTrans() != null) {
					// File f = new File(projectPath, existingFeed.getFeedImgTrans());
					// f.delete();
					amazonS3Client.deleteObject(bucket, existingFeed.getFeedImgTrans());
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

				existingFeed.setFeedImgOrigin(file.getOriginalFilename()); //원본 이미지 이름명으로 저장
				existingFeed.setFeedImgTrans(fileName); //멤버 이미지변환명으로 저장

				return feedRepository.save(existingFeed);
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

	public FeedResponseDTO convertToDTO(Feed feed) {
		FeedResponseDTO dto = new FeedResponseDTO();
		dto.setFeedNumber(feed.getFeedNumber());
		dto.setFeedCreatorNumber(feed.getMember().getMemberNumber());
		dto.setTitle(feed.getTitle());
		dto.setContent(feed.getContent());
		dto.setFeedLikeCnt(feed.getFeedLikeCnt());
		dto.setViewCnt(feed.getViewCnt());
		dto.setFeedActive(feed.isFeedActive());
		dto.setFeedImgOrigin(feed.getFeedImgOrigin());
		dto.setFeedImgTrans(feed.getFeedImgTrans());
		dto.setCreateDate(feed.getCreateDate());
		return dto;
	}
}
