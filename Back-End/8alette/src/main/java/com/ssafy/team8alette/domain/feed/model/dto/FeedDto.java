package com.ssafy.team8alette.domain.feed.model.dto;

import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedDto {
	
	private Long feedNumber;

	private Member member;

	private String title;

	private String content;

}
