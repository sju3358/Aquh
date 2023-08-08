package com.ssafy.team8alette.domain.bubble.tools.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoWayQuestionResponseDTO {
	private long two_way_question_number;
	private String left_context;
	private String right_context;
	private int pick_member_cnt;
	private int left_cnt;
	private int is_pick;

	@Override
	public String toString() {
		return "TwoWayQuestionResponseDTO{" +
			"two_way_question_number=" + two_way_question_number +
			", left_context='" + left_context + '\'' +
			", right_context='" + right_context + '\'' +
			", pick_member_cnt=" + pick_member_cnt +
			", left_cnt=" + left_cnt +
			", is_pick=" + is_pick +
			'}';
	}
}
