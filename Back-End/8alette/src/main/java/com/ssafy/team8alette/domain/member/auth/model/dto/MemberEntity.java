package com.ssafy.team8alette.domain.member.auth.model.dto;

import java.util.Date;

import com.ssafy.team8alette.domain.member.record.model.dto.MemberRecordEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "member")
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_number", nullable = false)
	private Long memberNumber;

	@Column(name = "member_id", nullable = false)
	private String memberId;

	@Column(name = "member_email", nullable = false)
	private String memberEmail;

	@Column(name = "member_password", nullable = false)
	private String memberPassword;

	@Column(name = "member_nickname", nullable = false)
	private String memberNickname;

	@Column(name = "member_name", nullable = false)
	private String memberName;

	@Column(name = "member_intro")
	private String memberIntro;

	@Column(name = "member_state", nullable = false)
	private int memberState;

	@Column(name = "member_type", nullable = false)
	private String memberType;

	@Column(name = "is_email_authentication", nullable = false)
	private boolean isEmailVerified;

	@Column(name = "is_email_receive", nullable = false)
	private boolean isEmailReceive;

	@Column(name = "delete_dttm")
	private Date deleteDate;

	//record랑 매핑
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "member")
	@PrimaryKeyJoinColumn
	private MemberRecordEntity memberRecordEntity;

}
