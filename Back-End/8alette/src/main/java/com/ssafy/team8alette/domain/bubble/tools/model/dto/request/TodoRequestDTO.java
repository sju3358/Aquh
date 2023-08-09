package com.ssafy.team8alette.domain.bubble.tools.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequestDTO {
	private Long bubble_number;
	private String todo_context;

	@Override
	public String toString() {
		return "TodoRequestDTO{" +
			"bubble_number=" + bubble_number +
			", todo_context=" + todo_context +
			'}';
	}
}
