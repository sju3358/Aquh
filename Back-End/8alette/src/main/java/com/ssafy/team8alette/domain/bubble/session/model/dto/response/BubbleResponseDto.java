package com.ssafy.team8alette.domain.bubble.session.model.dto.response;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BubbleResponseDto {

	@Nullable
	private Object data;

	private String message;

}
