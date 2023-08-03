package com.ssafy.team8alette.feed.model.dto;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likes")
@ToString
public class Like implements Serializable {

	@EmbeddedId
	private LikeID likeID;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

	@MapsId("likeFeedNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	private Feed feed;

	// @ManyToOne(fetch = FetchType.LAZY)
	// private Feed feed;

	// @ManyToOne(fetch = FetchType.LAZY)
	// private Member member;
	// public Like(Feed feed, Member member){
	// 	this.feed = feed;
	// 	this.member = memeber;
	// }

}
