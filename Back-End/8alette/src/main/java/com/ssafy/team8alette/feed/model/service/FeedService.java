package com.ssafy.team8alette.feed.model.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.team8alette.feed.exception.NotMatchException;
import com.ssafy.team8alette.feed.exception.NullValueException;
import com.ssafy.team8alette.feed.model.dao.FeedRepository;
import com.ssafy.team8alette.feed.model.dto.Feed;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedRepository feedRepository;

	//파일경로
	// private static String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
	private static String projectPath = "C:\\pictures";

	//피드 등록
	public void registFeed(Feed feed, MultipartFile file) throws Exception {

		if (!file.isEmpty()) {
			//랜덤 이미지 변환생성기
			java.util.Random generator = new java.util.Random();
			generator.setSeed(System.currentTimeMillis());
			int random = generator.nextInt(1000000) % 1000000;
			String randomNum = Integer.toString(random);
			//피드 생성일시
			Date nowDate = new Date();

			//피드 생성일시를 String으로 변환 -> 피드 변환명에 집어 넣음
			DateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String today = sdFormat.format(nowDate);
			String fileName = today + "_" + randomNum;

			//빈껍데기 생성해서 피드 저장소에 이미지 전달
			File saveFile = new File(projectPath, fileName);
			file.transferTo(saveFile);

			//멤버 피드활성화
			feed.setFeedActive(true);
			//멤버 좋아요갯수
			feed.setFeedLikeCnt(0);

			//원본 이미지 이름명으로 저장
			feed.setFeedImgOrigin(file.getOriginalFilename());
			//멤버 이미지변환명으로 저장
			feed.setFeedImgTrans(fileName);

			//멤버 현재시간 저장
			feed.setCreateDate(nowDate);

			feedRepository.save(feed);
		} else {
			//피드 생성일시
			Date nowDate = new Date();
			//멤버 피드활성화
			feed.setFeedActive(true);
			//멤버 좋아요갯수
			feed.setFeedLikeCnt(0);
			//멤버 현재시간 저장
			feed.setCreateDate(nowDate);
			feedRepository.save(feed);
		}

	}

	//피드 불러오기
	public List<Feed> getFeeds(String orderCriteria) {
		if (orderCriteria.equals("recent")) {
			List<Feed> list = feedRepository.findByFeedActiveOrderByFeedNumberDesc(true);
			return list;
		} else if (orderCriteria.equals("famous")) {
			Sort sort = Sort.by(Sort.Direction.DESC, "feedLikeCnt", "feedNumber");
			List<Feed> list = feedRepository.findByFeedActive(true, sort);
			return list;
		} else {
			List<Feed> list = feedRepository.findByFeedActiveOrderByFeedNumberDesc(true);
			if (list.isEmpty()) {
				throw new NullValueException("피드가 존재하지 않습니다");
			}
			return list;
		}
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
		if (existingFeed.getFeedImgTrans() != null) {
			File f = new File(projectPath, existingFeed.getFeedImgTrans());
			f.delete();
		}

		feedRepository.save(existingFeed);
	}

	// 피드 수정
	public Feed modifyFeed(Feed feed, MultipartFile file) throws Exception {
		Feed existingFeed = feedRepository.findFeedByFeedNumber(feed.getFeedNumber());
		if (existingFeed.getFeedCreatorNumber() == feed.getFeedCreatorNumber()) {
			existingFeed.setTitle(feed.getTitle());
			existingFeed.setContent(feed.getContent());
			if (file.isEmpty()) {
				//이 파일경로에 있는 원본 파일 삭제(있으면)
				if (existingFeed.getFeedImgTrans() != null) {
					File f = new File(projectPath, existingFeed.getFeedImgTrans());
					f.delete();
				}
				existingFeed.setFeedImgOrigin(null);
				existingFeed.setFeedImgTrans(null);
				return feedRepository.save(existingFeed);
			} else {
				//이 파일경로에 있는 원본 파일 삭제(있으면)
				if (existingFeed.getFeedImgTrans() != null) {
					File f = new File(projectPath, existingFeed.getFeedImgTrans());
					f.delete();
				}

				//랜덤 이미지 변환생성기
				java.util.Random generator = new java.util.Random();
				generator.setSeed(System.currentTimeMillis());
				int random = generator.nextInt(1000000) % 1000000;
				String randomNum = Integer.toString(random);

				//피드 수정 일시 -> 이미지 변환명에만 들어감
				Date nowDate = new Date();
				//피드 생성일시를 String으로 변환 -> 피드 변환명에 집어 넣음
				DateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String today = sdFormat.format(nowDate);
				String fileName = today + "_" + randomNum;
				//빈껍데기 생성해서 피드 저장소에 이미지 전달
				File saveFile = new File(projectPath, fileName);
				file.transferTo(saveFile);

				//원본 이미지 이름명으로 저장
				existingFeed.setFeedImgOrigin(file.getOriginalFilename());
				//멤버 이미지변환명으로 저장
				existingFeed.setFeedImgTrans(saveFile.getName());

				return feedRepository.save(existingFeed);
			}
		} else {
			throw new NotMatchException("회원번호가 일치하지 않습니다.");
		}
	}

	//피드 좋아요
	// public

	// 피드 하나 이름으로 불러오기
	// public Feed getFeedByCreatorNumber(Long creatorNumber) {
	// 	return feedRepository.findBycreatorNumber(creatorNumber);
	// }

}
