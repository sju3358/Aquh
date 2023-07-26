package com.ssafy.team8alette.member.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.member.exception.MemberDuplicatedException;
import com.ssafy.team8alette.member.model.dao.MemberLoginInfoRepository;
import com.ssafy.team8alette.member.model.dao.MemberRepository;
import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.member.util.PasswordUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAuthNaverService {

	private final MemberRepository memberRepository;
	private final PasswordUtil passwordUtil;
	private final MemberLoginInfoRepository memberLoginInfoRepository;

	public void login(Long memberNumber, String refreshToken) throws SQLException {

		if (memberNumber == -1) {
			throw new MemberDuplicatedException("회원이 존재하지 않습니다");
		}
		memberLoginInfoRepository.insertMemberLoginInfo(memberNumber, refreshToken, true);

	}

	public void register(String memberEmail, String memberName, String memberNickname, int memberAge) throws
		NoSuchAlgorithmException {
		Member member = new Member();
		member.setMemberState(1);
		member.setMemberEmail(memberEmail);
		member.setMemberName(memberName);
		member.setMemberNickname("NAVER_" + memberNickname);
		member.setMemberAge(memberAge);
		member.setMemberPassword(passwordUtil.encodePassword(passwordUtil.getRandomPassword()));
		memberRepository.save(member);
	}
}
