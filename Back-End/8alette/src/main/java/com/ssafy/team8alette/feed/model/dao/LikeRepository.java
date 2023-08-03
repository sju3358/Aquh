package com.ssafy.team8alette.feed.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.feed.model.dto.Like.Like;
import com.ssafy.team8alette.feed.model.dto.Like.LikeID;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeID> {

	List<Like> findByLikeIDLikeFeedNumberAndLikeIDLikeMemberNumber(Long feedNumber, Long memberNumber);

}
