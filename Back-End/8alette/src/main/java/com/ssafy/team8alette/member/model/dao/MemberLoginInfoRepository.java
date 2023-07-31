package com.ssafy.team8alette.member.model.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.member.model.dto.MemberLoginInfo;

/*
// set
    @PostMapping("")
    public String setRedisKey(@RequestBody Map<String, String> req) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        try {
            // Redis Set Key-value
            System.out.println(req.get("key") + " // " + req.get("value"));
            vop.set(req.get("key"), req.get("value"));
            return "set message success";
        } catch (Exception e) {
            e.printStackTrace();
            return "set message fail";
        }
    }

    // get
    @GetMapping("/{key}")
    public String getRedisKey(@PathVariable String key) {
        System.out.println(key);
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        return vop.get(key);
    }
    */
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
