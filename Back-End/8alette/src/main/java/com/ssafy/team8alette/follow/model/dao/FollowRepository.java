package com.ssafy.team8alette.follow.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.follow.model.dto.Entity.Follow;
import com.ssafy.team8alette.follow.model.dto.Key.FollowID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowID> {

	List<Follow> findAllByFollowingMemberNumber(Long memberNumber);

	List<Follow> findAllByFollowerMemberNumber(Long memberNumber);

	Follow findFollowByFollowerMemberNumberAndFollowingMemberNumber(Long followerMemberNumber,
		Long followingMemberNumber);
}
