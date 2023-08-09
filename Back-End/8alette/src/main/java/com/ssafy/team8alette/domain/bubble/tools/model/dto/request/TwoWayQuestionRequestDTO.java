package com.ssafy.team8alette.domain.bubble.tools.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoWayQuestionRequestDTO {
	private Long bubble_number;
	private String left_context;
	private String right_context;

	@Override
	public String toString() {
		return "TwoWayQuestionRequestDTO{" +
			"bubble_number=" + bubble_number +
			", left_context='" + left_context + '\'' +
			", right_context='" + right_context + '\'' +
			'}';
	}
}
