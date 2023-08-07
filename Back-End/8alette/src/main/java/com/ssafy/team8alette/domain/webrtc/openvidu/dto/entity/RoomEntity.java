package com.ssafy.team8alette.domain.webrtc.openvidu.dto.entity;

import java.util.Date;

import com.ssafy.team8alette.domain.auth.member.model.dto.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_number", nullable = false)
	private Long roomNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private Member member;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_number", nullable = false)
	private CategoryEntity categoryEntity;

	@Column(name = "room_type", nullable = false)
	private String roomType;

	@Column(name = "room_title", length = 20, nullable = false)
	private String roomTitle;

	@Column(name = "room_content", length = 500, nullable = false)
	private String roomContent;

	@Column(name = "room_thumbnail")
	private String roomThumbnail;

	@Column(name = "room_state", nullable = false)
	private boolean roomState;

	@Column(name = "plane_open_dttm")
	private Date planeOpenDate;

	@Column(name = "plane_close_dttm")
	private Date planeCloseDate;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

	@Column(name = "done_dttm")
	private Date closeDate;

}
