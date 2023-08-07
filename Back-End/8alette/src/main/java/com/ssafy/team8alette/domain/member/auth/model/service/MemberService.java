package com.ssafy.team8alette.domain.member.auth.model.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberType;
import com.ssafy.team8alette.domain.member.auth.util.MailSenderUtil;
import com.ssafy.team8alette.domain.member.auth.util.NullValueChecker;
import com.ssafy.team8alette.domain.member.auth.util.PasswordUtil;
import com.ssafy.team8alette.domain.member.auth.util.RegexChecker;
import com.ssafy.team8alette.global.exception.MemberDuplicatedException;
import com.ssafy.team8alette.global.exception.MemberNotExistException;
import com.ssafy.team8alette.global.exception.MemberPasswordInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final RegexChecker regexChecker;
	private final PasswordUtil passwordUtil;
	private final NullValueChecker nullValueChecker;
	private final MailSenderUtil mailSenderUtil;

	public Long register(Map<String, String> param) throws NoSuchAlgorithmException {

		String memberEmail = param.get("member_email");
		String memberPassword = param.get("member_password");
		String memberPasswordRepeat = param.get("member_password_repeat");
		String memberNickname = param.get("member_nickname");
		String memberName = param.get("member_name");
		boolean isEmailReceive = true;

		nullValueChecker.check(
			memberEmail,
			memberPassword,
			memberPasswordRepeat,
			memberNickname,
			memberName);

		regexChecker.checkEmailPattern(memberEmail);
		regexChecker.checkNicknamePattern(memberNickname);
		regexChecker.checkPasswordPattern(memberPassword);

		if (memberRepository.findMemberByMemberEmail(memberEmail) != null) {
			throw new MemberDuplicatedException("이미 회원정보가 존재합니다");
		}

		if (memberPassword.equals(memberPasswordRepeat) != true) {
			throw new MemberPasswordInvalidException("비밀번호를 다시 확인해주세요");
		}

		String memberPasswordEncoded = passwordUtil.encodePassword(memberPassword);

		Member member = new Member();
		member.setMemberId(memberEmail);
		member.setMemberEmail(memberEmail);
		member.setMemberPassword(memberPasswordEncoded);
		member.setMemberNickname(memberNickname);
		member.setMemberName(memberName);
		member.setMemberState(0);
		member.setMemberType(MemberType.CO.toString());
		member.setEmailVerified(false);
		member.setEmailReceive(isEmailReceive);
		memberRepository.save(member);

		return member.getMemberNumber();
	}

	public void deactivate(Long memberNumber) {
		Member member = memberRepository.findMemberByMemberNumber(memberNumber);
		member.setMemberState(2);
		memberRepository.save(member);
	}

	public Member getMemberInfo(Long memberNumber) {
		Member member = memberRepository.findMemberByMemberNumber(memberNumber);

		if (member == null) {
			throw new MemberNotExistException();
		}

		return member;
	}

	public Member getMemberInfo(String memberId) {
		Member member = memberRepository.findMemberByMemberId(memberId);

		if (member == null) {
			throw new MemberNotExistException();
		}

		return member;
	}

	public boolean isExistMemberId(String memberId) {
		Member member = memberRepository.findMemberByMemberId(memberId);

		if (member == null) {
			return false;
		}

		return true;
	}

	public boolean isExistMemberNickname(String memberNickname) {
		Member member = memberRepository.findMemberByMemberNickname(memberNickname);

		if (member == null) {
			return false;
		}

		return true;
	}

	public Member getMemberInfoByNickname(String memberNickName) {
		Member member = memberRepository.findMemberByMemberNickname(memberNickName);

		if (member == null) {
			throw new MemberNotExistException();
		}

		return member;
	}

	public void editMemberInfo(Map<String, String> param) throws IllegalAccessException {

		Long memberNumber = Long.parseLong(param.get("member_number").trim());
		String memberEmail = param.get("member_email").trim();
		String memberNickname = param.get("member_nickname").trim();
		String memberIntro = param.get("member_intro").trim();
		String memberName = param.get("member_name").trim();
		String memberEmailReceive = param.get("member_emailReceive").trim();

		Member member = memberRepository.findMemberByMemberNumber(memberNumber);

		if (memberEmail != null && memberEmail.equals("") != true) {
			member.setMemberEmail(memberEmail);
			member.setEmailVerified(false);
			mailSenderUtil.sendVerifyEmailMessage(memberNumber, memberEmail);
		}
		if (memberNickname != null && memberNickname.equals("") != true) {
			member.setMemberNickname(memberNickname);
		}

		if (memberIntro != null && memberIntro.equals("") != true) {
			member.setMemberIntro(memberIntro);
		}

		if (memberName != null && memberName.equals("") != true) {
			member.setMemberName(memberName);
		}

		if (memberEmailReceive != null && memberEmailReceive.equals("") != true) {
			member.setEmailReceive(memberEmailReceive.equals("Y"));
		}

		memberRepository.save(member);
	}

	public void verifyMemberState(Long memberNumber) {
		Member member = memberRepository.findMemberByMemberNumber(memberNumber);
		member.setMemberState(1);
		member.setEmailVerified(true);
		memberRepository.save(member);
	}

	public void verifyMemberEmail(Long memberNumber) {
		Member member = memberRepository.findMemberByMemberNumber(memberNumber);
		member.setEmailVerified(true);
		memberRepository.save(member);
	}

	public void changeMemberPassword(Map<String, String> param) throws
		NoSuchAlgorithmException {

		Long memberNumber = Long.parseLong(param.get("member_number").trim());
		String memberNewPassword = param.get("new_password").trim();
		String memberNewPasswordRepeat = param.get("new_password_repeat").trim();

		Member member = memberRepository.findMemberByMemberNumber(memberNumber);

		nullValueChecker.check(memberNewPassword, memberNewPasswordRepeat);

		regexChecker.checkPasswordPattern(memberNewPassword);

		if (memberNewPassword.equals(memberNewPasswordRepeat) != true) {
			throw new MemberPasswordInvalidException("변경할 비밀번호가 일치 하지 않습니다.");
		}

		String newPasswordEncoded = passwordUtil.encodePassword(memberNewPassword);

		member.setMemberPassword(newPasswordEncoded);

		memberRepository.save(member);
	}

	public void changeMemberPassword(String email, String newPassword) throws
		NoSuchAlgorithmException {

		Member member = memberRepository.findMemberByMemberEmail(email);

		String newPasswordEncoded = passwordUtil.encodePassword(newPassword);

		member.setMemberPassword(newPasswordEncoded);

		memberRepository.save(member);
	}

	public boolean checkValid(String memberEmail, String memberPassword) throws NoSuchAlgorithmException {

		Member member = memberRepository.findMemberByMemberEmail(memberEmail);

		passwordUtil.match(memberPassword, member.getMemberPassword());

		return true;
	}

}
