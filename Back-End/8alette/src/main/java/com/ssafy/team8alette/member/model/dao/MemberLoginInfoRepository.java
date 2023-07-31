package com.ssafy.team8alette.member.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.member.model.dto.MemberLoginInfo;

@Repository
public interface MemberLoginInfoRepository extends CrudRepository<MemberLoginInfo, String> {

	public MemberLoginInfo findMemberLoginInfoByMemberNumber(String memberNumber);

	public void deleteMemberLoginInfoByMemberNumber(String memberNumber);
}
