package com.ssafy.team8alette.domain.bubble.tools.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteQuestionResponseDTO {
	private Long bubble_number;
	private String vote_question_context;

	@Override
	public String toString() {
		return "VoteQuestionResponseDTO{" +
			"bubble_number=" + bubble_number +
			", vote_question_context='" + vote_question_context + '\'' +
			'}';
	}
}
