package com.ssafy.team8alette.domain.auth.member.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.auth.member.model.dto.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findMemberByMemberId(String memberId);

	Member findMemberByMemberNumber(Long memberNumber);

	Member findMemberByMemberEmail(String memberEmail);

	Member findMemberByMemberNickname(String memberNickname);

}
