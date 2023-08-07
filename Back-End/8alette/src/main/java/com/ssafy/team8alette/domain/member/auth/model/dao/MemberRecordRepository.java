package com.ssafy.team8alette.domain.member.auth.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.member.auth.model.dto.MemberRecord;

public interface MemberRecordRepository extends JpaRepository<MemberRecord, Long> {

	MemberRecord findMemberRecordByMemberNumber(Long memberNumber);

}
