package com.ssafy.team8alette.feed.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.feed.model.dto.Feed;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

	//피드만든사람 이름으로 불러오기
	// Feed findBycreatorNumber(Long creatorNumber);
	//Long으로 구현해야함
	Feed findFeedByFeedNumber(Long feedNumber);

	List<Feed> findAllByOrderByFeedLikeCntDesc();
	
	//Long으로 삭제
	// void deleteById(Long id);
}
