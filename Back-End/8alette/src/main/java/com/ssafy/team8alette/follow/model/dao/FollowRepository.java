package com.ssafy.team8alette.follow.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.follow.model.dto.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	List<Follow> findAllByFollowingMemberNumber(Long memberNumber);

	List<Follow> findAllByFollowerMemberNumber(Long memberNumber);

	Follow findFollowByFollowerMemberNumberAndFollowingMemberNumber(Long followerMemberNumber,
		Long followingMemberNumber);
}
