package com.ssafy.team8alette.member.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
	@GeneratedValue
	private Long memberNumber;

	@Column(name = "exp_cnt")
	private int memberExpCnt;

	@Column(name = "comment_cnt")
	private int memberCommentCnt;

	@Column(name = "room_join_cnt")
	private int memberRoomJoinCnt;

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

	@OneToOne(mappedBy = "memberRecord")
	private Member member;
}
