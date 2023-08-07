package com.ssafy.team8alette.domain.webrtc.openvidu.dto.entity;

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
@Table(name = "bubble")
public class BubbleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bubble_number", nullable = false)
	private Long bubbleNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private MemberEntity memberEntityNumber;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_number", nullable = false)
	private CategoryEntity categoryEntity;

	@Column(name = "is_bubble_talk", nullable = false)
	private boolean bubbleType;

	@Column(name = "bubble_title", length = 20, nullable = false)
	private String bubbleTitle;

	@Column(name = "bubble_content", length = 500, nullable = false)
	private String bubbleContent;

	@Column(name = "bubble_thumbnail")
	private String bubbleThumbnail;

	@Column(name = "bubble_state", nullable = false)
	private boolean bubbleState;

	@Column(name = "plan_open_dttm")
	private Date planOpenDate;

	@Column(name = "plan_close_dttm")
	private Date planCloseDate;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;

	@Column(name = "done_dttm")
	private Date closeDate;

}
