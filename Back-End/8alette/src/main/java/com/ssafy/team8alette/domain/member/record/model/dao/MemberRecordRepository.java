package com.ssafy.team8alette.domain.member.record.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.member.record.model.dto.entity.MemberRecord;

public interface MemberRecordRepository extends JpaRepository<MemberRecord, Long> {

	MemberRecord findMemberRecordByMemberNumber(Long memberNumber);

}
