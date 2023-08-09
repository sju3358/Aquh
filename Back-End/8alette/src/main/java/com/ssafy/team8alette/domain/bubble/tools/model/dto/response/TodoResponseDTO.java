package com.ssafy.team8alette.domain.bubble.tools.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponseDTO {
	private long todo_number;
	private String todo_context;
	private boolean todo_done_state;

	@Override
	public String toString() {
		return "TodoResponseDTO{" +
			"todo_number=" + todo_number +
			", todo_context='" + todo_context + '\'' +
			", todo_done_state=" + todo_done_state +
			'}';
	}
}
