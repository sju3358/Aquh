package com.ssafy.team8alette.alarm.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.alarm.model.dao.AlarmRepository;
import com.ssafy.team8alette.alarm.model.dto.Alarm;
import com.ssafy.team8alette.member.exception.NullValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;

	public List<Alarm> getAlarmList(Long memberNumber) {
		return alarmRepository.findAlarmsByMemberNumber(memberNumber)
			.orElseThrow(() -> new NullValueException("회원정보가 존재하지 않습니다")
			);
	}

	public void readAlarm(Long alarmNumber) {
		Alarm alarm = alarmRepository.findAlarmsByAlarmNumber(alarmNumber).orElseThrow(
			() -> new NullValueException("알람정보가 존재하지 않습니다")
		);

		alarm.setAlarmState(1);
		alarmRepository.save(alarm);
	}
}
