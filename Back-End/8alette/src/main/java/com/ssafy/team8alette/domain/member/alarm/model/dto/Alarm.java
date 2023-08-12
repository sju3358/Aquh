package com.ssafy.team8alette.domain.member.alarm.model.dto;

import java.util.Date;

import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "alarm")
public class Alarm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarm_number", nullable = false)
	private Long alarmNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private Member memberNumber;

	@Column(name = "alarm_type", nullable = false)
	private String alarmType;

	@Column(name = "alarm_reason")
	private String alarmReason;

	@Column(name = "alarm_state", nullable = false)
	private int alarmState;

	@Column(name = "read_dttm")
	private Date readDateTime;

	@Column(name = "create_dttm")
	private Date createDateTime;
	
}
