package com.ssafy.team8alette.domain.member.follow.model.dto.Entity;

import java.util.Date;

import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "follow")
@ToString
public class FollowEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "follow_number", nullable = false)
	private Long followNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower_number", nullable = false)
	private MemberEntity followerMemberNumberEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "following_number", nullable = false)
	private MemberEntity followingMemberNumberEntity;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

}
