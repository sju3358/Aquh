package com.ssafy.team8alette.feed.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.feed.exception.NullValueException;
import com.ssafy.team8alette.feed.model.dao.FeedRepository;
import com.ssafy.team8alette.feed.model.dto.Feed;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedRepository feedRepository;

	//피드 등록
	public void registFeed(Feed feed) {

		//랜덤 이미지 변환생성기
		java.util.Random generator = new java.util.Random();
		generator.setSeed(System.currentTimeMillis());
		int random = generator.nextInt(1000000) % 1000000;
		String randomNum = Integer.toString(random);
		//피드 생성일시
		Date date = new Date();
		//멤버 피드활성화
		feed.setFeedActive(true);
		//멤버 좋아요갯수
		feed.setFeedLikeCnt(0);
		//멤버 이미지변환
		feed.setFeedImgTrans(randomNum);
		feed.setCreateDate(date);
		feedRepository.save(feed);

	}

	// // 피드 전체 불러오기
	public List<Feed> getFeeds() {
		List<Feed> list = feedRepository.findAll();
		if (list.isEmpty()) {
			throw new NullValueException("피드가 존재하지 않습니다");
		}
		return list;
	}

	// 피드 상세글 불러오기
	public Feed getFeedById(Long feedNumber) {
		return feedRepository.findFeedByFeedNumber(feedNumber);
	}

	// 피드 하나 이름으로 불러오기
	// public Feed getFeedByCreatorNumber(Long creatorNumber) {
	// 	return feedRepository.findBycreatorNumber(creatorNumber);
	// }

	// 피드 삭제
	public void deleteFeed(Long id) {
		feedRepository.deleteById(id);
	}

	// 피드 수정
	// public Feed modifyFeed(Feed feed) {
	// 	Feed existingFeed = feedRepository.findFeedByFeedNumber(feed.getFeedNumber());
	// 	existingFeed.setTitle(feed.getTitle());
	// 	existingFeed.setContent(feed.getContent());
	// 	existingFeed.setFeedImgOrigin(feed.getFeedImgOrigin());
	// 	return feedRepository.save(existingFeed);
	// }

}
