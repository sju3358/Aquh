package com.ssafy.team8alette.follow.model.dto.Entity;

import java.util.Date;

import com.ssafy.team8alette.member.model.dto.Member;

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
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "follow_number", nullable = false)
	private Long followNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower_number", nullable = false)
	private Member followerMemberNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "following_number", nullable = false)
	private Member followingMemberNumber;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;
	
}
