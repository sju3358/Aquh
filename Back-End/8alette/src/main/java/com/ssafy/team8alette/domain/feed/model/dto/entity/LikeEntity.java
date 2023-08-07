package com.ssafy.team8alette.domain.feed.model.dto.entity;

import java.io.Serializable;
import java.util.Date;

import com.ssafy.team8alette.domain.feed.model.dto.key.LikeID;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;

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
public class LikeEntity implements Serializable {

	@EmbeddedId
	private LikeID likeID;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

	@MapsId("likeFeedNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "like_feed_number", nullable = false)
	private FeedEntity feedEntity;

	@MapsId("likeMemberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "like_member_number", nullable = false)
	private MemberEntity memberEntity;

	public LikeEntity(FeedEntity feedEntity, MemberEntity memberEntity) {
		this.feedEntity = feedEntity;
		this.memberEntity = memberEntity;
	}

}
