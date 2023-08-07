package com.ssafy.team8alette.domain.member.alarm.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.member.alarm.model.dto.AlarmEntity;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

	List<AlarmEntity> findByMemberNumberAndAlarmState(MemberEntity memberEntityNumber, int state);

	Optional<AlarmEntity> findAlarmsByAlarmNumber(Long alarmNumber);

}
