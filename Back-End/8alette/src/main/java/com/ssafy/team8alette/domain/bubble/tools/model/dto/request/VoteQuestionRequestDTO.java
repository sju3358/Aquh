package com.ssafy.team8alette.domain.bubble.tools.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteQuestionRequestDTO {
	private Long vote_question_number;
	private String vote_question_context;
	private boolean is_my_pick;
	private int vote_answer_cnt;

	@Override
	public String toString() {
		return "VoteQuestionResponseDTO{" +
			"vote_question_number=" + vote_question_number +
			", vote_question_context='" + vote_question_context + '\'' +
			", is_my_pick=" + is_my_pick +
			", vote_answer_cnt=" + vote_answer_cnt +
			'}';
	}
}
