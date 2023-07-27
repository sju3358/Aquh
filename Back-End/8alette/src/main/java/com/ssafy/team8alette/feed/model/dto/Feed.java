package com.ssafy.team8alette.feed.model.dto;

import java.util.Date;

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

	@Column(name = "feed_content", nullable = false)
	private String content;

	@Column(name = "feed_like_cnt", nullable = false)
	private int feedLikeCnt;

	@Column(name = "feed_view_cnt", nullable = false)
	private int viewCnt;

	@Column(name = "feed_active", nullable = false)
	private boolean feedActive;

	@Column(name = "feed_img_origin")
	private String feedImgOrigin;

	@Column(name = "feed_img_trans")
	private String feedImgTrans;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

	@Column(name = "delete_dttm")
	private Date deleteDate;

}
