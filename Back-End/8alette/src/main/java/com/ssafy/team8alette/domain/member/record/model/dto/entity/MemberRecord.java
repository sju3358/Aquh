package com.ssafy.team8alette.domain.member.record.model.dto.entity;

import java.util.Date;

import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "record")
public class MemberRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_number")
	private Long memberNumber;

	@Column(name = "exp_cnt")
	private int memberExpCnt;

	@Column(name = "feed_cnt")
	private int memberFeedCnt;

	@Column(name = "bubble_join_cnt")
	private int bubbleJoinCnt;

	@Column(name = "like_give_cnt")
	private int memberLikeGiveCnt;

	@Column(name = "like_receive_cnt")
	private int memberLikeReceiveCnt;

	@Column(name = "best_cnt")
	private int memberBestCnt;

	@Column(name = "following_cnt")
	private int memberFollowingCnt;

	@Column(name = "follower_cnt")
	private int memberFollowerCnt;

	@Column(name = "record_update_dttm")
	private Date date;

	// 이 부분이 member 매핑
	@MapsId
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "member_number", referencedColumnName = "member_number")
	private Member member;

	// , referencedColumnName = "member_number"
}
