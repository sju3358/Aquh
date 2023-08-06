package com.ssafy.team8alette.member.model.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	@Column(name = "member_number")
	private Long memberNumber;

	@Column(name = "exp_cnt", nullable = false)
	private int memberExpCnt;

	@Column(name = "comment_cnt", nullable = false)
	private int memberCommentCnt;

	@Column(name = "room_join_cnt", nullable = false)
	private int memberRoomJoinCnt;

	@Column(name = "like_give_cnt", nullable = false)
	private int memberLikeGiveCnt;

	@Column(name = "like_receive_cnt", nullable = false)
	private int memberLikeReceiveCnt;

	@Column(name = "best_cnt", nullable = false)
	private int memberBestCnt;

	@Column(name = "following_cnt", nullable = false)
	private int memberFollowingCnt;

	@Column(name = "follower_cnt", nullable = false)
	private int memberFollowerCnt;

	@Column(name = "record_update_dttm")
	private Date date;

	// 이 부분이 member 매핑
	@OneToOne
	@MapsId
	@JoinColumn(name = "member_number")
	private Member member;

}
