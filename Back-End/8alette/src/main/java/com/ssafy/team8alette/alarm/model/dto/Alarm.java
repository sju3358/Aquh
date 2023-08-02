package com.ssafy.team8alette.alarm.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "alarm")
public class Alarm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarm_number")
	private Long alarmNumber;

	@Column(name = "member_number")
	private Long memberNumber;

	@Column(name = "alarm_text")
	private String alarmText;

	@Column(name = "alarm_state")
	private int alarmState;

}
