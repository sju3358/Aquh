package com.ssafy.team8alette.domain.member.alarm.method;

import com.ssafy.team8alette.domain.member.alarm.model.dto.Alarm;
import com.ssafy.team8alette.domain.member.alarm.model.dto.AlarmListResponseDTO;

public class AlarmListMapper {
	public static AlarmListResponseDTO convertToDTO(Alarm alarm) {
		AlarmListResponseDTO dto = new AlarmListResponseDTO();
		dto.setAlarmNumber(alarm.getAlarmNumber());
		dto.setMemberNumber(alarm.getMemberNumber().getMemberNumber());
		dto.setType(alarm.getAlarmType());
		dto.setReason(alarm.getAlarmReason());
		return dto;
	}
}
