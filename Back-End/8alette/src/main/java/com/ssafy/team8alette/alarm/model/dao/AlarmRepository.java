package com.ssafy.team8alette.alarm.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.alarm.model.dto.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

	Optional<List<Alarm>> findAlarmsByMemberNumber(Long memberNumber);

	Optional<Alarm> findAlarmsByAlarmNumber(Long alarmNumber);
	
}
