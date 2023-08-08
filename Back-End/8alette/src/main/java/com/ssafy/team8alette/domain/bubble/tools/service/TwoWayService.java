package com.ssafy.team8alette.domain.bubble.tools.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.tools.model.dao.TwoWayAnswerRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.TwoWayQuestionRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.TwoWayQuestionEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TwoWayService {
	private final TwoWayQuestionRepository twoWayQuestionRepository;
	private final TwoWayAnswerRepository twoWayAnswerRepository;

	// 양자택일 질문 전체 조회
	public List<TwoWayQuestionEntity> getTwoWayQuestions(Long bubbleNumber) {
		return twoWayQuestionRepository.findTwoWayQuestionByBubbleNumber(bubbleNumber);
	}

	// 양자택일 질문 등록
	public void registTwoWayQuestion(TwoWayQuestionEntity twoWayQuestionEntity) {
		twoWayQuestionRepository.save(twoWayQuestionEntity);
	}

	// 양자택일 질문 삭제
	public void deleteTwoWayQuestion(Long twoWayQuestionNumber) {
		twoWayQuestionRepository.deleteById(twoWayQuestionNumber);
	}
}
