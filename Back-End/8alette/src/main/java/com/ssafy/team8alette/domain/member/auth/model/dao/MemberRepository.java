package com.ssafy.team8alette.domain.member.auth.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findMemberByMemberId(String memberId);

	Optional<Member> findMemberByMemberNumber(Long memberNumber);

	Optional<Member> findMemberByMemberEmail(String memberEmail);

	Optional<Member> findMemberByMemberNickname(String memberNickname);

}
