package com.ssafy.team8alette.domain.member.auth.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	MemberEntity findMemberByMemberId(String memberId);

	MemberEntity findMemberByMemberNumber(Long memberNumber);

	MemberEntity findMemberByMemberEmail(String memberEmail);

	MemberEntity findMemberByMemberNickname(String memberNickname);

}
