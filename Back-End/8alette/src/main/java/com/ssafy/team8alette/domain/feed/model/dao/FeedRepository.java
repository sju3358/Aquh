package com.ssafy.team8alette.domain.feed.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.feed.model.dto.entity.FeedEntity;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

@Repository
public interface FeedRepository extends JpaRepository<FeedEntity, Long> {

	FeedEntity findFeedByFeedNumber(Long feedNumber);

	List<FeedEntity> findByFeedActiveOrderByFeedNumberDesc(boolean feedActive);

	@Query("SELECT f FROM FeedEntity f WHERE f.feedActive = true ORDER BY f.feedLikeCnt DESC, f.feedNumber DESC")
	List<FeedEntity> findByFeedActiveOrderByFeedLikeCntDescAndFeedNumberDesc(boolean feedActive);

	boolean existsByFeedNumberAndFeedActive(Long feedNumber, boolean feedActive);

	List<FeedEntity> findByMemberOrderByFeedNumberDesc(Member member);

}
