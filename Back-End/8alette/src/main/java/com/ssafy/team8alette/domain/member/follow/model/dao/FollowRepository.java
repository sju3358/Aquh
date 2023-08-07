package com.ssafy.team8alette.domain.member.follow.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;
import com.ssafy.team8alette.domain.member.follow.model.dto.Entity.FollowEntity;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

	List<FollowEntity> findAllByFollowingMemberNumber(MemberEntity memberEntityNumber);

	List<FollowEntity> findAllByFollowerMemberNumber(MemberEntity memberEntityNumber);

	// Follow findFollowByFollowerMemberNumberAndFollowingMemberNumber(Member member);

	// @Query("SELECT f FROM Follow f WHERE f.followerMemberNumber = :followerMember AND f.followingMemberNumber = :followingMember")
	// Follow findByFollowerMemberNumberAndFollowingMemberNumber(@Param("followerMember") Member followMember,
	// 	@Param("followingMember") Member followingMember);

	FollowEntity findByFollowerMemberNumberAndFollowingMemberNumber(
		MemberEntity followerMemberEntity, MemberEntity followingMemberEntity);

	// int countByFollowerMemberNumber(Member member);
	int countByFollowingMemberNumber(MemberEntity memberEntity);

}
