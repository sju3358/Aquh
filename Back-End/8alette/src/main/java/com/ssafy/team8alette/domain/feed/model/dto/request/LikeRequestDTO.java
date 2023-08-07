package com.ssafy.team8alette.domain.feed.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDTO {
	private Long feedNumber;
	private Long memberNumber;
}
