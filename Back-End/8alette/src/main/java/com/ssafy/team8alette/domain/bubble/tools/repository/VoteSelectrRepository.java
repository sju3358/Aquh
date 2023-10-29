package com.ssafy.team8alette.domain.bubble.tools.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.key.VoteID;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.VoteQuestionEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.VoteSelectEntity;

@Repository
public interface VoteSelectrRepository extends JpaRepository<VoteSelectEntity, VoteID> {
	boolean existsByVoteID(VoteID voteID);

	void save(VoteID voteID);

	int countAllByVoteQuestionEntity(VoteQuestionEntity voteQuestionEntity);

	void deleteAllByVoteQuestionEntity(VoteQuestionEntity voteQuestionEntity);
}
