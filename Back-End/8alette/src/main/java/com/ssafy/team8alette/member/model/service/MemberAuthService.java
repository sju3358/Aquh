package com.ssafy.team8alette.member.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.member.exception.MemberDuplicatedException;
import com.ssafy.team8alette.member.exception.MemberNotExistException;
import com.ssafy.team8alette.member.model.dao.MemberLoginInfoRepository;
import com.ssafy.team8alette.member.model.dao.MemberRepository;
import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.member.util.NullValueChecker;
import com.ssafy.team8alette.member.util.PasswordUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAuthService {

	private final MemberLoginInfoRepository memberLoginInfoRepository;
	private final MemberRepository memberRepository;
	private final NullValueChecker nullValueChecker;
	private final PasswordUtil passwordUtil;

	public Long loginCheck(String loginEmail, String loginPassword) throws
		NullPointerException,
		SQLException,
		NoSuchAlgorithmException {

		nullValueChecker.check(loginEmail, loginPassword);

		Member member = memberRepository.findMemberByMemberEmail(loginEmail);

		if (member == null) {
			throw new MemberNotExistException();
		}

		if (memberLoginInfoRepository.findMemberLoginInfoByMemberNumber(member.getMemberNumber()) != null) {
			throw new MemberDuplicatedException("이미 로그인 중입니다.");
		}

		passwordUtil.match(member.getMemberPassword(), loginPassword);

		return member.getMemberNumber();
	}

	public void login(Long memberNumber, String refreshToken) throws SQLException {
		memberLoginInfoRepository.insertMemberLoginInfo(memberNumber, refreshToken, false);

	}

	public void logout(Long memberNumber) throws SQLException {
		memberLoginInfoRepository.deleteMemberLoginInfo(memberNumber);
	}
}
