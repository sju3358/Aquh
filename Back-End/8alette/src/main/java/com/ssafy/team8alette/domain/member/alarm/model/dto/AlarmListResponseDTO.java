package com.ssafy.team8alette.domain.member.alarm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmListResponseDTO {
	private Long alarmNumber;
	private Long memberNumber;
	private String type;
	private String reason;
	private int state;
}
