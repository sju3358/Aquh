package com.ssafy.team8alette.alarm.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.alarm.model.dto.Alarm;
import com.ssafy.team8alette.member.model.dto.Member;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

	List<Alarm> findByMemberNumberAndAlarmState(Member memberNumber, int state);

	Optional<Alarm> findAlarmsByAlarmNumber(Long alarmNumber);

}
