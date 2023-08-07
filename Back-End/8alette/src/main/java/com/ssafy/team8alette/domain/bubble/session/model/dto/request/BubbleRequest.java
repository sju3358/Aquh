package com.ssafy.team8alette.domain.bubble.session.model.dto.request;

import java.util.Date;

import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.CategoryEntity;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

public class BubbleRequest {

	private Member hostMemberNumber;

	private CategoryEntity categoryEntity;

	private boolean bubbleType;

	private String bubbleTitle;

	private String bubbleContent;

	private String bubbleThumbnail;

	private Date planOpenDate;

	private Date planCloseDate;

}
