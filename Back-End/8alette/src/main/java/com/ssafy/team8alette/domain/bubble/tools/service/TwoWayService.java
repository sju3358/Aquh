package com.ssafy.team8alette.domain.bubble.tools.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.TwoWayAnswerRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.TwoWayQuestionRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.TwoWayAnswerEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.TwoWayQuestionEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.key.TwoWayAnswerID;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.request.TwoWayAnswerRequestDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.request.TwoWayQuestionRequestDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.TwoWayQuestionResponseDTO;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TwoWayService {
    private final TwoWayQuestionRepository twoWayQuestionRepository;
    private final TwoWayAnswerRepository twoWayAnswerRepository;
    private final BubbleRepository bubbleRepository;
    private final MemberRepository memberRepository;

    // 양자택일 질문 전체 조회
    public List<TwoWayQuestionResponseDTO> getTwoWayQuestions(Long bubbleNumber, Long memberNumber) {
        BubbleEntity bubbleEntity = bubbleRepository.findById(bubbleNumber).orElseThrow();
        List<TwoWayQuestionEntity> questions = twoWayQuestionRepository.findAllByBubbleEntity(bubbleEntity);
        List<TwoWayQuestionResponseDTO> response = new ArrayList<>();

        for (TwoWayQuestionEntity question : questions) {
            List<TwoWayAnswerEntity> answers = twoWayAnswerRepository.findAllByTwoWayQuestionEntity(question);

            int left_cnt = 0;
            int is_pick = 0;

            for (TwoWayAnswerEntity answer : answers) {
                if (answer.getMember().getMemberNumber() == memberNumber) {
                    is_pick = answer.isLeftPick() ? 1 : 2;
                }
                if (answer.isLeftPick()) {
                    left_cnt++;
                }
            }
            TwoWayQuestionResponseDTO dto = new TwoWayQuestionResponseDTO();
            dto.setTwo_way_question_number(question.getTwoWayQuestionNumber());
            dto.setLeft_context(question.getLeftContext());
            dto.setRight_context(question.getRightContext());
            dto.setPick_member_cnt(answers.size());
            dto.setLeft_cnt(left_cnt);
            dto.setMy_pick(is_pick);

            response.add(dto);
        }

        return response;
    }

    // 양자택일 질문 등록
    public void registTwoWayQuestion(TwoWayQuestionRequestDTO twoWayQuestionRequestDTO) {
        BubbleEntity bubbleEntity = bubbleRepository.findById(twoWayQuestionRequestDTO.getBubble_number())
                .orElseThrow();
        TwoWayQuestionEntity twoWayQuestionEntity = new TwoWayQuestionEntity();
        twoWayQuestionEntity.setBubbleEntity(bubbleEntity);
        twoWayQuestionEntity.setLeftContext(twoWayQuestionRequestDTO.getLeft_context());
        twoWayQuestionEntity.setRightContext(twoWayQuestionRequestDTO.getRight_context());
        twoWayQuestionRepository.save(twoWayQuestionEntity);
    }

    // 양자택일 질문 삭제
    public void deleteTwoWayQuestion(Long twoWayQuestionNumber) {
        twoWayQuestionRepository.deleteById(twoWayQuestionNumber);
    }

    // 양자택일 답변 등록/수정
    @Transactional
    public void registTwoWayAnswer(TwoWayAnswerRequestDTO twoWayAnswerRequestDTO) {
        TwoWayQuestionEntity twoWayQuestionEntity = twoWayQuestionRepository.findById(
                twoWayAnswerRequestDTO.getTwo_way_question_number()).orElseThrow();
        Member member = memberRepository.findById(twoWayAnswerRequestDTO.getMember_number()).orElseThrow();

        TwoWayAnswerID twoWayAnswerID = new TwoWayAnswerID();
        twoWayAnswerID.setTwoWayQuestionNumber(twoWayAnswerRequestDTO.getTwo_way_question_number());
        twoWayAnswerID.setMemberNumber(twoWayAnswerRequestDTO.getMember_number());

        if(twoWayAnswerRepository.existsByTwoWayAnswerID(twoWayAnswerID)) {
            twoWayAnswerRepository.deleteByTwoWayAnswerID(twoWayAnswerID);
        }

        TwoWayAnswerEntity twoWayAnswerEntity = new TwoWayAnswerEntity();
        twoWayAnswerEntity.setTwoWayQuestionEntity(twoWayQuestionEntity);
        twoWayAnswerEntity.setMember(member);
        twoWayAnswerEntity.setLeftPick(twoWayAnswerRequestDTO.isLeft_pick());
        twoWayAnswerEntity.setTwoWayAnswerID(twoWayAnswerID);

        twoWayAnswerRepository.save(twoWayAnswerEntity);
    }
}
