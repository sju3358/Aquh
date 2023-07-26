package com.ssafy.team8alette.member.model.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.member.model.dto.MemberLoginInfo;

@Repository
public class MemberLoginInfoRepository {
	public void insertMemberLoginInfo(Long memberNumber, String refreshToken, boolean isSocialLogin) throws
		SQLException {
		//레디스 저장 쿼리
	}

	public MemberLoginInfo findMemberLoginInfoByMemberNumber(Long memberNumber) throws SQLException {
		// 레디스 find 쿼리
		return null;
	}

	public void updateMemberLoginInfo(Long memberNumber, String refreshToken) throws SQLException {

		//레디스 update 쿼리
	}

	public void deleteMemberLoginInfo(Long memberNumber) throws SQLException {
		//레디스 삭제쿼리
	}
}
