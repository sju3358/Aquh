package com.ssafy.team8alette.domain.bubble.tools.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoWayAnswerRequestDTO {
	private Long two_way_question_number;
	private boolean left_pick;

	@Override
	public String toString() {
		return "TwoWayQuestionAnswerDTO{" +
			"two_way_question_number=" + two_way_question_number +
			", left_pick=" + left_pick +
			'}';
	}
}
