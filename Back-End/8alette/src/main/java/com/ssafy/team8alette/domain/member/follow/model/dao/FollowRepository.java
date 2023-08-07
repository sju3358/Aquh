package com.ssafy.team8alette.domain.member.follow.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.follow.model.dto.Entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	List<Follow> findAllByFollowingMemberNumber(Member memberNumber);

	List<Follow> findAllByFollowerMemberNumber(Member memberNumber);

	// Follow findFollowByFollowerMemberNumberAndFollowingMemberNumber(Member member);

	// @Query("SELECT f FROM Follow f WHERE f.followerMemberNumber = :followerMember AND f.followingMemberNumber = :followingMember")
	// Follow findByFollowerMemberNumberAndFollowingMemberNumber(@Param("followerMember") Member followMember,
	// 	@Param("followingMember") Member followingMember);

	Follow findByFollowerMemberNumberAndFollowingMemberNumber(Member followerMember, Member followingMember);

	// int countByFollowerMemberNumber(Member member);
	int countByFollowingMemberNumber(Member member);

}
