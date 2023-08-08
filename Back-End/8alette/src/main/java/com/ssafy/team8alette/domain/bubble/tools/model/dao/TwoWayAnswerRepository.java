package com.ssafy.team8alette.domain.bubble.tools.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.TwoWayAnswerEntity;

public interface TwoWayAnswerRepository extends JpaRepository<TwoWayAnswerEntity, Long> {
	/**
	 * [양자택일 질문 번호로 답변 전체 조회]
	 * @param twoWayQuestionNumber 양자택일 질문 번호
	 * @return List<TwoWayAnswerEntity> : 양자택일 답변 리스트
	 */
	// List<TwoWayAnswerEntity> findTwoWayAnswerByBubbleNumber(Long twoWayQuestionNumber);

}
