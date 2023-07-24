package com.ssafy.team8alette.member.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.member.exception.DuplicatedMemberException;
import com.ssafy.team8alette.member.exception.InvalidMemberPasswordException;
import com.ssafy.team8alette.member.exception.NullValueException;
import com.ssafy.team8alette.member.model.dao.MemberLoginInfoRepository;
import com.ssafy.team8alette.member.model.dao.MemberRepository;
import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.member.util.NullValueChecker;
import com.ssafy.team8alette.member.util.PasswordEncryptor;

@Service
public class MemberAuthService {

	private final MemberLoginInfoRepository memberLoginInfoRepository;
	private final MemberRepository memberRepository;
	private final NullValueChecker nullValueChecker;
	private final PasswordEncryptor passwordEncryptor;

	public MemberAuthService(
		MemberLoginInfoRepository memberLoginInfoRepository,
		MemberRepository memberRepository,
		NullValueChecker nullValueChecker,
		PasswordEncryptor passwordEncryptor) {

		this.memberLoginInfoRepository = memberLoginInfoRepository;
		this.memberRepository = memberRepository;
		this.nullValueChecker = nullValueChecker;
		this.passwordEncryptor = passwordEncryptor;
	}

	public Long loginCheck(String loginEmail, String loginPassword) throws
		NullPointerException,
		SQLException,
		NoSuchAlgorithmException {

		nullValueChecker.check(loginEmail, loginPassword);

		Member member = memberRepository.findMemberByMemberEmail(loginEmail);

		if (member == null)
			throw new NullValueException("존재하지 않는 회원입니다.");

		if (memberLoginInfoRepository.findMemberLoginInfoByMemberNumber(member.getMemberNumber()) != null)
			throw new DuplicatedMemberException("이미 로그인 중입니다.");

		if (passwordEncryptor.match(member.getMemberPassword(), loginPassword) != true)
			throw new InvalidMemberPasswordException("비밀번호가 일치하지 않습니다.");

		return member.getMemberNumber();
	}

	public void login(Long memberNumber, String refreshToken) throws SQLException {
		memberLoginInfoRepository.insertMemberLoginInfo(memberNumber, refreshToken);

	}

	public void logout(Long memberNumber) throws SQLException {
		memberLoginInfoRepository.deleteMemberLoginInfo(memberNumber);
	}
}
