package com.ssafy.team8alette.feed.model.dto.feed.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDTO {
	private Long feedNumber;
	private Long feedCreatorNumber;
	private String title;
	private String content;
	private int feedLikeCnt;
	private int viewCnt;
	private boolean feedActive;
	private String feedImgOrigin;
	private String feedImgTrans;
	private Date createDate;
	private Date deleteDate;
	//여기서부터 다른 엔티티
	private int exp;
	private long symbolNumber;
	private int followingCnt;
	private String nickName;
}
