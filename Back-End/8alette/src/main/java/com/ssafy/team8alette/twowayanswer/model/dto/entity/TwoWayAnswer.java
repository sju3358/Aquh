package com.ssafy.team8alette.twowayanswer.model.dto.entity;

import java.io.Serializable;
import java.util.Date;

import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.room.model.dto.Room;
import com.ssafy.team8alette.twowayanswer.model.dto.key.TwoWayAnswerID;
import com.ssafy.team8alette.twowayquestion.model.dto.TwoWayQuestion;

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
@Table(name = "two_way_answer")
public class TwoWayAnswer implements Serializable {

	@EmbeddedId
	private TwoWayAnswerID twoWayAnswerID;

	@MapsId("twoWayAnswerNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "two_way_answer_number", nullable = false)
	private TwoWayQuestion twoWayQuestion;

	@MapsId("memberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_number", nullable = false)
	private Room room;

	@Column(name = "is_pick_right", nullable = false)
	private boolean isPickRight;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

}
