package com.ssafy.team8alette.domain.member.alarm.model.dto;

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
