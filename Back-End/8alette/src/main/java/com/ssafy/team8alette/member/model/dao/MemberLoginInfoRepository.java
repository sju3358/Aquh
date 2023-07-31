package com.ssafy.team8alette.member.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.member.exception.UnAuthorizedException;
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

		ValueOperations<String, Map<String, Object>> vop = redisTemplate.opsForValue();
		vop.set(Long.toString(memberNumber), data);
	}

	public MemberLoginInfo findMemberLoginInfoByMemberNumber(Long memberNumber) {

		ValueOperations<String, Map<String, Object>> vop = redisTemplate.opsForValue();
		Map<String, Object> data = vop.get(Long.toString(memberNumber));

		if (data == null)
			return null;

		MemberLoginInfo memberLoginInfo = new MemberLoginInfo();
		memberLoginInfo.setSocialLogin(Boolean.parseBoolean(data.get("is_social_login").toString()));
		memberLoginInfo.setMemberNumber(memberNumber);
		memberLoginInfo.setRefreshToken(data.get("refresh_token").toString());

		return new MemberLoginInfo();
	}

	public void updateMemberLoginInfo(Long memberNumber, String refreshToken) throws SQLException {

		ValueOperations<String, Map<String, Object>> vop = redisTemplate.opsForValue();
		Map<String, Object> data = vop.get(Long.toString(memberNumber));

		if (data == null)
			throw new UnAuthorizedException("로그인이 필요합니다");

		data.put("refresh_token", refreshToken);

		vop.set(Long.toString(memberNumber), data);

	}

	public void deleteMemberLoginInfo(Long memberNumber) {

		ValueOperations<String, Map<String, Object>> vop = redisTemplate.opsForValue();
		vop.getAndDelete(Long.toString(memberNumber));
	}
}
