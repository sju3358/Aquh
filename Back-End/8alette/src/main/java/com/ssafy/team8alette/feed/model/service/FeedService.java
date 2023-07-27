package com.ssafy.team8alette.feed.model.service;

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
		//여기 멤버에서
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
	//
	// // 피드 하나 불러오기
	// // 사실여기도 Long으로 바꾸던가
	// public Feed getFeedById(Long id) {
	// 	return feedRepository.findFeedByFeedNumber(id);
	// }
	//
	// // 피드 하나 이름으로 불러오기
	// public Feed getFeedByCreatorNumber(Long creatorNumber) {
	// 	return feedRepository.findBycreatorNumber(creatorNumber);
	// }
	//
	// // 피드 삭제
	// public String deleteFeed(Long id) {
	// 	feedRepository.deleteById(id);
	// 	return "feed removed !!" + id;
	// }
	//
	// // 피드 수정
	// public Feed modifyFeed(Feed feed) {
	// 	Feed existingFeed = feedRepository.findFeedByFeedNumber(feed.getFeedNumber());
	// 	existingFeed.setTitle(feed.getTitle());
	// 	existingFeed.setContent(feed.getContent());
	// 	existingFeed.setFeedImgOrigin(feed.getFeedImgOrigin());
	// 	return feedRepository.save(existingFeed);
	// }

}
