package com.ssafy.team8alette.domain.bubble.tools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.key.TwoWayAnswerID;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.TwoWayAnswerEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.TwoWayQuestionEntity;

public interface TwoWayAnswerRepository extends JpaRepository<TwoWayAnswerEntity, Long> {
	/**
	 * [양자택일 질문 객체로 양자택일 답변 전체 조회]
	 *
	 * @param twoWayQuestionEntity 양자택일 질문 객체
	 * @return List<TwoWayAnswerEntity> : 양자택일 답변 객체 리스트
	 */
	List<TwoWayAnswerEntity> findAllByTwoWayQuestionEntity(TwoWayQuestionEntity twoWayQuestionEntity);

	boolean existsByTwoWayAnswerID(TwoWayAnswerID twoWayAnswerID);

	void deleteByTwoWayAnswerID(TwoWayAnswerID twoWayAnswerID);
}
