package com.ssafy.team8alette.domain.member.record.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.member.record.model.dto.MemberRecordEntity;

public interface MemberRecordRepository extends JpaRepository<MemberRecordEntity, Long> {

	MemberRecordEntity findMemberRecordByMemberNumber(Long memberNumber);

}
