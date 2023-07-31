package com.ssafy.team8alette.member.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.member.exception.MemberDuplicatedException;
import com.ssafy.team8alette.member.exception.MemberNotExistException;
import com.ssafy.team8alette.member.exception.UnAuthorizedException;
import com.ssafy.team8alette.member.model.dao.MemberLoginInfoRepository;
import com.ssafy.team8alette.member.model.dao.MemberRepository;
import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.member.model.dto.MemberLoginInfo;
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

		if (member.getMemberState() == 0) {
			throw new UnAuthorizedException("이메일인증을 먼저 진행해 주세요");
		}

		if (member.getMemberState() == 2) {
			throw new UnAuthorizedException("이미 탈퇴한 회원입니다");
		}

		if (memberLoginInfoRepository.findMemberLoginInfoByMemberNumber(member.getMemberNumber()) != null) {
			throw new MemberDuplicatedException("이미 로그인 중입니다.");
		}

		passwordUtil.match(loginPassword, member.getMemberPassword());

		return member.getMemberNumber();
	}

	public void login(Long memberNumber, String refreshToken) throws SQLException {

		MemberLoginInfo memberLoginInfo = memberLoginInfoRepository.findMemberLoginInfoByMemberNumber(
			memberNumber);

		if (memberLoginInfo != null) {
			memberLoginInfo.setRefreshToken(refreshToken);
			{
				memberLoginInfoRepository.updateMemberLoginInfo(memberNumber, refreshToken);
			}
		} else {
			memberLoginInfoRepository.insertMemberLoginInfo(memberNumber, refreshToken, false);
		}

	}

	public void logout(Long memberNumber) throws SQLException {
		memberLoginInfoRepository.deleteMemberLoginInfo(memberNumber);
	}

	public MemberLoginInfo getLoginMemberInfo(Long memberNumber) throws SQLException {
		MemberLoginInfo member = memberLoginInfoRepository.findMemberLoginInfoByMemberNumber(memberNumber);

		if (member == null) {
			throw new UnAuthorizedException();
		}

		return member;
	}
}
