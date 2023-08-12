package com.ssafy.team8alette.domain.bubble.tools.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.session.repository.BubbleListRepository;
import com.ssafy.team8alette.domain.bubble.session.repository.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.VoteQuestionRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.key.VoteID;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.request.VoteQuestionRequestDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.VoteQuestionResponseDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.VoteQuestionEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.VoteSelectEntity;
import com.ssafy.team8alette.domain.bubble.tools.repository.VoteSelectrRepository;
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
	public List<VoteQuestionRequestDTO> getVoteQuestions(Long bubble_number, Long member_number) {

		BubbleEntity bubbleEntity = bubbleRepository.findById(bubble_number).orElseThrow();
		Member member = memberRepository.findById(member_number).orElseThrow();
		VoteID voteID = new VoteID(bubbleEntity.getBubbleNumber(), member.getMemberNumber());

		List<VoteQuestionEntity> voteQuestionEntities = voteQuestionRepository.findAllByBubbleEntity(bubbleEntity);
		List<VoteQuestionRequestDTO> dtos = new ArrayList<>();

		for (VoteQuestionEntity voteQuestionEntity : voteQuestionEntities) {
			VoteQuestionRequestDTO dto = new VoteQuestionRequestDTO();
			dto.setVote_question_number(voteQuestionEntity.getVoteQuestionNumber());
			dto.setVote_question_context(voteQuestionEntity.getVoteQuestionContext());
			dto.set_my_pick(voteSelectrRepository.existsByVoteID(voteID));
			dto.setVote_answer_cnt(voteSelectrRepository.countAllByVoteQuestionEntity(voteQuestionEntity));
			dtos.add(dto);
		}

		return dtos;
	}

	// 투표 질문 등록
	public void registVoteQuestion(VoteQuestionResponseDTO voteQuestionResponseDTO) {
		BubbleEntity bubbleEntity = bubbleRepository.findById(voteQuestionResponseDTO.getBubble_number()).orElseThrow();
		VoteQuestionEntity voteQuestionEntity = new VoteQuestionEntity();
		voteQuestionEntity.setVoteQuestionContext(voteQuestionResponseDTO.getVote_question_context());
		voteQuestionEntity.setBubbleEntity(bubbleEntity);
		voteQuestionRepository.save(voteQuestionEntity);
	}

	// 투표 질문 삭제
	@Transactional
	public void deleteVoteQuestion(Long vote_question_number) {
		VoteQuestionEntity voteQuestionEntity = voteQuestionRepository.findById(vote_question_number).orElseThrow();
		voteSelectrRepository.deleteAllByVoteQuestionEntity(voteQuestionEntity);
		voteQuestionRepository.deleteById(vote_question_number);
	}

	// 투표 선택 등록
	public void registVoteSelect(Long vote_question_number, Long member_number) {
		VoteQuestionEntity voteQuestionEntity = voteQuestionRepository.findById(vote_question_number).orElseThrow();
		Member member = memberRepository.findById(member_number).orElseThrow();
		VoteID voteID = new VoteID(voteQuestionEntity.getVoteQuestionNumber(), member.getMemberNumber());

		VoteSelectEntity voteSelectEntity = new VoteSelectEntity();
		voteSelectEntity.setVoteID(voteID);
		voteSelectEntity.setVoteQuestionEntity(voteQuestionEntity);
		voteSelectEntity.setMember(member);

		voteSelectrRepository.save(voteSelectEntity);
	}

	// 투표 선택 삭제
	public void deleteVoteSelect(Long vote_question_number, Long member_number) {
		VoteQuestionEntity voteQuestionEntity = voteQuestionRepository.findById(vote_question_number).orElseThrow();
		Member member = memberRepository.findById(member_number).orElseThrow();
		VoteID voteID = new VoteID(voteQuestionEntity.getVoteQuestionNumber(), member.getMemberNumber());

		voteSelectrRepository.deleteById(voteID);
	}
}
