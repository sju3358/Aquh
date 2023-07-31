package com.ssafy.team8alette.member.model.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.member.exception.MemberNotExistException;
import com.ssafy.team8alette.member.exception.MemberPasswordInvalidException;
import com.ssafy.team8alette.member.model.dao.MemberRepository;
import com.ssafy.team8alette.member.model.dto.Member;
import com.ssafy.team8alette.member.model.dto.MemberType;
import com.ssafy.team8alette.member.util.MailSenderUtil;
import com.ssafy.team8alette.member.util.NullValueChecker;
import com.ssafy.team8alette.member.util.PasswordUtil;
import com.ssafy.team8alette.member.util.RegexChecker;

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
		boolean isEmailReceive = param.get("is_email_receive").equals("true");

		nullValueChecker.check(
			memberEmail,
			memberPassword,
			memberPasswordRepeat,
			memberNickname);

		regexChecker.checkEmailPattern(memberEmail);
		regexChecker.checkNicknamePattern(memberNickname);
		regexChecker.checkPasswordPattern(memberPassword);

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
		member.setMemberType(MemberType.CO);
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

	public Member getMemberInfoByEmail(String memberEmail) {
		Member member = memberRepository.findMemberByMemberEmail(memberEmail);

		if (member == null) {
			throw new MemberNotExistException();
		}

		return member;
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

	public void changeMemberPassword(Long memberNumber, String newPassword,
		String newPasswordRepeat) throws
		NoSuchAlgorithmException {

		Member member = memberRepository.findMemberByMemberNumber(memberNumber);

		nullValueChecker.check(newPassword, newPasswordRepeat);

		regexChecker.checkPasswordPattern(newPassword);

		if (newPassword.equals(newPasswordRepeat) != true) {
			throw new MemberPasswordInvalidException("변경할 비밀번호가 일치 하지 않습니다.");
		}

		String newPasswordEncoded = passwordUtil.encodePassword(newPassword);

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
