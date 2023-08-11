package com.ssafy.team8alette.domain.member.alarm.model.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.alarm.model.dao.AlarmRepository;
import com.ssafy.team8alette.domain.member.alarm.model.dto.Alarm;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.global.exception.NullValueException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;
	private final MemberRepository memberRepository;

	public List<Alarm> getAlarmList(Long memberNumber) {
		List<Alarm> list = null;

		Member member = memberRepository.findById(memberNumber)
			.orElseThrow(() -> new NullValueException("회원정보가 존재하지 않습니다"));
		list = alarmRepository.findByMemberNumberAndAlarmState(member, 0);
		if (list == null || list.isEmpty()) {
			throw new NullValueException("알람이 존재하지 않습니다");
		}
		return list;
	}

	public void readAlarm(Long alarmNumber) {

		Alarm alarm = alarmRepository.findAlarmsByAlarmNumber(alarmNumber).orElseThrow(
			() -> new NullValueException("알람정보가 존재하지 않습니다")
		);

		alarm.setAlarmState(1);
		alarm.setReadDateTime(new Date());
		alarmRepository.save(alarm);
	}

	public void requestAlarm(Member member, String alarmType, String alarmReason, int alarmState) {
		Alarm alarm = new Alarm();
		alarm.setMemberNumber(member);
		alarm.setAlarmType(alarmType);
		alarm.setAlarmReason(alarmReason);
		alarm.setAlarmState(alarmState);
		alarm.setCreateDateTime(new Date());
		alarmRepository.save(alarm);
	}

	public void alarmRegist(Long memberNumber, String alarmType, String alarmReason, int alarmState) {
		Optional<Member> member = memberRepository.findMemberByMemberNumber(memberNumber);
		Alarm alarm = new Alarm();
		alarm.setMemberNumber(member.get());
		alarm.setAlarmType(alarmType);
		alarm.setAlarmReason(alarmReason);
		alarm.setAlarmState(alarmState);
		alarm.setCreateDateTime(new Date());
		alarmRepository.save(alarm);
	}

	public boolean getAlarm(Member member, String alarmType) {
		Optional<Alarm> alarm = alarmRepository.findByMemberNumberAndAlarmType(member, alarmType);
		boolean alarmOrNull = alarm.isPresent();
		return alarmOrNull;
	}

}
