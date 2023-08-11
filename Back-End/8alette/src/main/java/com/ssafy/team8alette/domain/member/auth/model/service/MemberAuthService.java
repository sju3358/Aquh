package com.ssafy.team8alette.domain.member.auth.model.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.auth.exception.MemberDuplicatedException;
import com.ssafy.team8alette.domain.member.auth.exception.MemberLoginException;
import com.ssafy.team8alette.domain.member.auth.exception.MemberNotExistException;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberLoginInfoRepository;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberLoginInfo;
import com.ssafy.team8alette.domain.member.auth.util.PasswordUtil;
import com.ssafy.team8alette.global.exception.UnAuthorizedException;
import com.ssafy.team8alette.global.util.NullValueChecker;

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
		NoSuchAlgorithmException {

		nullValueChecker.check(loginEmail, loginPassword);

		Member member = memberRepository.findMemberByMemberEmail(loginEmail)
			.orElseThrow(() -> new MemberNotExistException());

		if (member.getMemberState() == 0) {
			throw new UnAuthorizedException("이메일인증을 먼저 진행해 주세요");
		}

		if (member.getMemberState() == 2) {
			throw new UnAuthorizedException("이미 탈퇴한 회원입니다");
		}

		if (memberLoginInfoRepository.findById(Long.toString(member.getMemberNumber())).orElse(null) != null) {
			throw new MemberDuplicatedException("이미 로그인 중입니다.");
		}

		passwordUtil.match(loginPassword, member.getMemberPassword());

		return member.getMemberNumber();
	}

	public void login(Long memberNumber, String refreshToken) {

		MemberLoginInfo memberLoginInfo = memberLoginInfoRepository.findById(Long.toString(memberNumber)).orElse(null);

		if (memberLoginInfo != null)
			throw new MemberLoginException("이미 로그인 되어있습니다");

		memberLoginInfo = new MemberLoginInfo();
		memberLoginInfo.setSocialLogin(false);
		memberLoginInfo.setMemberNumber(Long.toString(memberNumber));
		memberLoginInfo.setRefreshToken(refreshToken);
		memberLoginInfoRepository.save(memberLoginInfo);
	}

	public void refreshToken(Long memberNumber, String refreshToken) {

		MemberLoginInfo memberLoginInfo = memberLoginInfoRepository.findById(Long.toString(memberNumber)).orElse(null);

		if (memberLoginInfo == null) {
			throw new UnAuthorizedException("로그인이 필요합니다");
		}
		memberLoginInfo.setRefreshToken(refreshToken);

		memberLoginInfoRepository.save(memberLoginInfo);

	}

	public void logout(Long memberNumber) {
		memberLoginInfoRepository.deleteById(Long.toString(memberNumber));
	}

	public MemberLoginInfo getLoginMemberInfo(Long memberNumber) {
		MemberLoginInfo memberLoginInfo = memberLoginInfoRepository.findById(Long.toString(memberNumber)).orElse(null);

		if (memberLoginInfo == null) {
			throw new UnAuthorizedException();
		}

		return memberLoginInfo;
	}

}
