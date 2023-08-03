package com.ssafy.team8alette.follow.model.dto.Entity;

import java.io.Serializable;
import java.util.Date;

import com.ssafy.team8alette.follow.model.dto.Key.FollowID;
import com.ssafy.team8alette.member.model.dto.Member;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "follow")
public class Follow implements Serializable {

	@EmbeddedId
	private FollowID followID;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

	@MapsId("followerNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number")
	private Member followerMember;

	@MapsId("followingNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number")
	private Member followingMember;

	// @Id
	// private Long followerMemberNumber;
	//
	// @Column
	// private Long followingMemberNumber;
}
