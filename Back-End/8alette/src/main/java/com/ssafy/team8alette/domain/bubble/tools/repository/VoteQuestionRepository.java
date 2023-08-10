package com.ssafy.team8alette.domain.bubble.tools.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.VoteQuestionEntity;

@Repository
public interface VoteQuestionRepository extends JpaRepository<VoteQuestionEntity, Long> {
	List<VoteQuestionEntity> findAllByBubbleEntity(BubbleEntity bubbleEntity);
}
