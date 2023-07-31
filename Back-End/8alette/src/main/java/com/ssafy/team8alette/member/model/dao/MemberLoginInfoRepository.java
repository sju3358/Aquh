package com.ssafy.team8alette.member.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.member.model.dto.MemberLoginInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberLoginInfoRepository {

	private final RedisTemplate redisTemplate;

	public void insertMemberLoginInfo(Long memberNumber, String refreshToken, boolean isSocialLogin) {
		Map<String, Object> data = new HashMap<>();

		data.put("refresh_token", refreshToken);
		data.put("is_social_login", isSocialLogin);

		ValueOperations<Long, Map<String, Object>> vop = redisTemplate.opsForValue();
		vop.set(memberNumber, data);
	}

	public MemberLoginInfo findMemberLoginInfoByMemberNumber(Long memberNumber) {

		ValueOperations<Long, Map<String, Object>> vop = redisTemplate.opsForValue();
		Map<String, Object> data = vop.get(memberNumber);

		MemberLoginInfo memberLoginInfo = new MemberLoginInfo();
		memberLoginInfo.setSocialLogin(Boolean.parseBoolean(data.get("is_social_login").toString()));
		memberLoginInfo.setMemberNumber(memberNumber);
		memberLoginInfo.setRefreshToken(data.get("refresh_token").toString());

		return new MemberLoginInfo();
	}

	public void updateMemberLoginInfo(Long memberNumber, String refreshToken) throws SQLException {

		ValueOperations<Long, Map<String, Object>> vop = redisTemplate.opsForValue();
		Map<String, Object> data = vop.get(memberNumber);

		data.put("refresh_token", refreshToken);

		vop.set(memberNumber, data);

	}

	public void deleteMemberLoginInfo(Long memberNumber) {

		ValueOperations<Long, Map<String, Object>> vop = redisTemplate.opsForValue();
		vop.getAndDelete(memberNumber);
	}
}
