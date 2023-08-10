package com.ssafy.team8alette.domain.bubble.tools.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleListRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.TodoRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.VoteQuestionRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.VoteSelectrRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.TodoEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.entity.VoteQuestionEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.key.VoteID;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.request.TodoRequestDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.TodoResponseDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.VoteQuestionResponseDTO;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteQuestionRepository voteQuestionRepository;
    private final VoteSelectrRepository voteSelectrRepository;
    private final BubbleRepository bubbleRepository;
    private final BubbleListRepository bubbleListRepository;
    private final MemberRepository memberRepository;

    // 투표 질문 전체 조회
    public List<VoteQuestionResponseDTO> getVoteQuestions(Long bubble_number, Long member_number) {

        BubbleEntity bubbleEntity = bubbleRepository.findById(bubble_number).orElseThrow();
        Member member = memberRepository.findById(member_number).orElseThrow();
        VoteID voteID = new VoteID(bubbleEntity.getBubbleNumber(), member.getMemberNumber());

        List<VoteQuestionEntity> voteQuestionEntities = voteQuestionRepository.findAllByBubbleEntity(bubbleEntity);
        List<VoteQuestionResponseDTO> dtos = new ArrayList<>();

        for (VoteQuestionEntity voteQuestionEntity : voteQuestionEntities) {
            VoteQuestionResponseDTO dto = new VoteQuestionResponseDTO();
            dto.setVote_question_number(voteQuestionEntity.getVoteQuestionNumber());
            dto.setVote_question_context(voteQuestionEntity.getVoteQuestionContext());
            dto.set_my_pick(voteSelectrRepository.existsByVoteID(voteID));
            dto.setVote_answer_cnt(voteSelectrRepository.countAllByVoteQuestionEntity(voteQuestionEntity));
            dtos.add(dto);
        }

        return dtos;
    }
    //
    // // 투두 등록
    // public void registTodo(TodoRequestDTO todoRequestDTO) {
    //     TodoEntity todoEntity = new TodoEntity();
    //     todoEntity.setBubbleEntity(bubbleRepository.findById(todoRequestDTO.getBubble_number()).orElseThrow());
    //     todoEntity.setTodoContext(todoRequestDTO.getTodo_context());
    //     todoEntity.setTodoDoneStatus(false);
    //     todoRepository.save(todoEntity);
    // }
    //
    // // 투두 상태 변경
    // public void updateTodo(Long todo_number) {
    //     TodoEntity todoEntity = todoRepository.findById(todo_number).orElseThrow();
    //     todoEntity.setTodoDoneStatus(!todoEntity.isTodoDoneStatus());
    //     todoRepository.save(todoEntity);
    // }
    //
    // // 양자택일 질문 삭제
    // public void deleteTodo(Long todo_number) {
    //     if(todoRepository.existsById(todo_number))
    //         todoRepository.deleteById(todo_number);
    // }
}
