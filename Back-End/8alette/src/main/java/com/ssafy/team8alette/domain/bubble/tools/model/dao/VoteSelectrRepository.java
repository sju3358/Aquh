package com.ssafy.team8alette.domain.bubble.tools.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.VoteQuestionEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.VoteSelectEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.key.VoteID;

@Repository
public interface VoteSelectrRepository extends JpaRepository<VoteSelectEntity, VoteID> {
	boolean existsByVoteID(VoteID voteID);

	int countAllByVoteQuestionEntity(VoteQuestionEntity voteQuestionEntity);
}
