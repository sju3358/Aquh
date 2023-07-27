package com.ssafy.team8alette.feed.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feed")
public class Feed {

	@Id
	@GeneratedValue
	@Column(name = "feed_number", nullable = false)
	private Long feedNumber;

	@Column(name = "feed_creator_number", nullable = false)
	private Long feedCreatorNumber;

	@Column(name = "feed_title", nullable = false)
	private String title;

	@Column(name = "feed_content")
	private String content;

	@Column(name = "feed_like_cnt")
	private int feedLikeCnt;

	@Column(name = "feed_view_cnt")
	private int viewCnt;

	@Column(name = "feed_active")
	private boolean feedActive;

	@Column(name = "feed_img_origin")
	private String feedImgOrigin;

	@Column(name = "feed_img_trans")
	private String feedImgTrans;

}
