package com.ssafy.team8alette.domain.bubble.tools.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoWayQuestionAnswerDTO {
	private Long two_way_question_number;
	private Long member_number;
	private boolean is_pick_right;

	@Override
	public String toString() {
		return "TwoWayQuestionAnswerDTO{" +
			"two_way_question_number=" + two_way_question_number +
			", member_number=" + member_number +
			", is_pick_right=" + is_pick_right +
			'}';
	}
}
