package com.ssafy.team8alette.domain.member.alarm.method;

import com.ssafy.team8alette.domain.member.alarm.model.dto.AlarmEntity;
import com.ssafy.team8alette.domain.member.alarm.model.dto.AlarmListResponseDTO;

public class AlarmListMapper {
	public static AlarmListResponseDTO convertToDTO(AlarmEntity alarmEntity) {
		AlarmListResponseDTO dto = new AlarmListResponseDTO();
		dto.setAlarmNumber(alarmEntity.getAlarmNumber());
		dto.setMemberNumber(alarmEntity.getMemberEntityNumber().getMemberNumber());
		dto.setType(alarmEntity.getAlarmType());
		dto.setReason(alarmEntity.getAlarmReason());
		return dto;
	}
}
