package com.ssafy.team8alette.grouplist.model.dto.entity;

import java.io.Serializable;
import java.util.Date;

import com.ssafy.team8alette.grouplist.model.dto.key.GroupID;
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
@Table(name = "group_list")
public class GroupList implements Serializable {

	@EmbeddedId
	private GroupID groupID;

	@MapsId("roomNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_number", nullable = false)
	private Room room;

	@MapsId("memberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private Member member;

	@Column(name = "is_mic_on", nullable = false)
	private boolean micStatus;

	@Column(name = "is_cam_on", nullable = false)
	private boolean camStatus;

	@Column(name = "is_mic_lock", nullable = false)
	private boolean micLockStatus;

	@Column(name = "is_cam_lock", nullable = false)
	private boolean camLockStatus;

	@Column(name = "is_chat_lock", nullable = false)
	private boolean chatLockStatus;

	@Column(name = "join_state", nullable = false)
	private int joinStatus;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;
}
