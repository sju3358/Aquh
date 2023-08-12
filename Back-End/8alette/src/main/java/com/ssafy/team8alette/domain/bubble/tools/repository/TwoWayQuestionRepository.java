package com.ssafy.team8alette.domain.bubble.tools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.TwoWayQuestionEntity;

@Repository
public interface TwoWayQuestionRepository extends JpaRepository<TwoWayQuestionEntity, Long> {
	/**
	 * [버블 객체로 양자택일 질문 전체 조회]
	 *
	 * @param bubbleEntity 버블번호
	 * @return List<TwoWayQuestionEntity> : 양자택일 질문 리스트
	 */
	List<TwoWayQuestionEntity> findAllByBubbleEntity(BubbleEntity bubbleEntity);
}
