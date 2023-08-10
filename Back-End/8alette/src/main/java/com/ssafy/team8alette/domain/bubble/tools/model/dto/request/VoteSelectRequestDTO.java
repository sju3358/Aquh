package com.ssafy.team8alette.domain.bubble.tools.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteSelectRequestDTO {
	private Long vote_question_number;
	private Long member_number;

	@Override
	public String toString() {
		return "VoteSelectRequestDTO{" +
			"vote_question_number=" + vote_question_number +
			", member_number=" + member_number +
			'}';
	}
}
