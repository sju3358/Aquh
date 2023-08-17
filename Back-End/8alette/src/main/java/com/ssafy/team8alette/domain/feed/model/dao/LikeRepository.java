package com.ssafy.team8alette.domain.feed.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.feed.model.dto.entity.LikeEntity;
import com.ssafy.team8alette.domain.feed.model.dto.key.LikeID;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, LikeID> {

	List<LikeEntity> findByLikeIDLikeFeedNumberAndLikeIDLikeMemberNumber(Long feedNumber, Long memberNumber);

	boolean existsByLikeIDLikeFeedNumberAndLikeIDLikeMemberNumber(Long feedNumber, Long memberNumber);

}
