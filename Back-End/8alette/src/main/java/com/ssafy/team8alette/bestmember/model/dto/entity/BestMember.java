package com.ssafy.team8alette.bestmember.model.dto.entity;

import java.io.Serializable;
import java.util.Date;

import com.ssafy.team8alette.bestmember.model.dto.key.BestMemberID;
import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.room.model.dto.Room;

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
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "best_member")
public class BestMember implements Serializable {

	@EmbeddedId
	private BestMemberID bestMemberID;

	@MapsId("roomNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_number", nullable = false)
	private Room room;

	@MapsId("memberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "best_member_number", nullable = false)
	private Member bestMember;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

}
