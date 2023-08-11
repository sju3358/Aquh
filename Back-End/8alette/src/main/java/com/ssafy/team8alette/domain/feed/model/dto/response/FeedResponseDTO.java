package com.ssafy.team8alette.domain.feed.model.dto.response;

import java.util.Date;
import java.util.List;

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
	private List<String> symbolLink;
	private int followingCnt;
	private String nickName;
	private int level;
}
