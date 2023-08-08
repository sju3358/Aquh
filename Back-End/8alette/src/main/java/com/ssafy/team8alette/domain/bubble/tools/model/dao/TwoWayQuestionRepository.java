package com.ssafy.team8alette.domain.bubble.tools.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.TwoWayQuestionEntity;

public interface TwoWayQuestionRepository extends JpaRepository<TwoWayQuestionEntity, Long> {
	/**
	 * [버블번호로 양자택일 질문 전체 조회]
	 * @param bubbleNumber 버블번호
	 * @return List<TwoWayQuestionEntity> : 양자택일 질문 리스트
	 */
	List<TwoWayQuestionEntity> findTwoWayQuestionByBubbleNumber(Long bubbleNumber);

}
