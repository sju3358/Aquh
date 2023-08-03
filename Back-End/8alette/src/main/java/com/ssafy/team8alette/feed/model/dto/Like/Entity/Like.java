package com.ssafy.team8alette.feed.model.dto.Like.Entity;

import java.io.Serializable;
import java.util.Date;

import com.ssafy.team8alette.feed.model.dto.Feed.Entity.Feed;
import com.ssafy.team8alette.feed.model.dto.Like.Key.LikeID;
import com.ssafy.team8alette.member.model.dto.Member;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
	@JoinColumn(name = "like_feed_number")
	private Feed feed;

	@MapsId("likeMemberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "like_member_number")
	private Member member;

	public Like(Feed feed, Member member) {
		this.feed = feed;
		this.member = member;
	}

}
