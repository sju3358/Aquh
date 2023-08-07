package com.ssafy.team8alette.domain.member.auth.model.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;
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

		MemberEntity memberEntity = new MemberEntity();
		memberEntity.setMemberId(memberEmail);
		memberEntity.setMemberEmail(memberEmail);
		memberEntity.setMemberPassword(memberPasswordEncoded);
		memberEntity.setMemberNickname(memberNickname);
		memberEntity.setMemberName(memberName);
		memberEntity.setMemberState(0);
		memberEntity.setMemberType(MemberType.CO.toString());
		memberEntity.setEmailVerified(false);
		memberEntity.setEmailReceive(isEmailReceive);
		memberRepository.save(memberEntity);

		return memberEntity.getMemberNumber();
	}

	public void deactivate(Long memberNumber) {
		MemberEntity memberEntity = memberRepository.findMemberByMemberNumber(memberNumber);
		memberEntity.setMemberState(2);
		memberRepository.save(memberEntity);
	}

	public MemberEntity getMemberInfo(Long memberNumber) {
		MemberEntity memberEntity = memberRepository.findMemberByMemberNumber(memberNumber);

		if (memberEntity == null) {
			throw new MemberNotExistException();
		}

		return memberEntity;
	}

	public MemberEntity getMemberInfo(String memberId) {
		MemberEntity memberEntity = memberRepository.findMemberByMemberId(memberId);

		if (memberEntity == null) {
			throw new MemberNotExistException();
		}

		return memberEntity;
	}

	public boolean isExistMemberId(String memberId) {
		MemberEntity memberEntity = memberRepository.findMemberByMemberId(memberId);

		if (memberEntity == null) {
			return false;
		}

		return true;
	}

	public boolean isExistMemberNickname(String memberNickname) {
		MemberEntity memberEntity = memberRepository.findMemberByMemberNickname(memberNickname);

		if (memberEntity == null) {
			return false;
		}

		return true;
	}

	public MemberEntity getMemberInfoByNickname(String memberNickName) {
		MemberEntity memberEntity = memberRepository.findMemberByMemberNickname(memberNickName);

		if (memberEntity == null) {
			throw new MemberNotExistException();
		}

		return memberEntity;
	}

	public void editMemberInfo(Map<String, String> param) throws IllegalAccessException {

		Long memberNumber = Long.parseLong(param.get("member_number").trim());
		String memberEmail = param.get("member_email").trim();
		String memberNickname = param.get("member_nickname").trim();
		String memberIntro = param.get("member_intro").trim();
		String memberName = param.get("member_name").trim();
		String memberEmailReceive = param.get("member_emailReceive").trim();

		MemberEntity memberEntity = memberRepository.findMemberByMemberNumber(memberNumber);

		if (memberEmail != null && memberEmail.equals("") != true) {
			memberEntity.setMemberEmail(memberEmail);
			memberEntity.setEmailVerified(false);
			mailSenderUtil.sendVerifyEmailMessage(memberNumber, memberEmail);
		}
		if (memberNickname != null && memberNickname.equals("") != true) {
			memberEntity.setMemberNickname(memberNickname);
		}

		if (memberIntro != null && memberIntro.equals("") != true) {
			memberEntity.setMemberIntro(memberIntro);
		}

		if (memberName != null && memberName.equals("") != true) {
			memberEntity.setMemberName(memberName);
		}

		if (memberEmailReceive != null && memberEmailReceive.equals("") != true) {
			memberEntity.setEmailReceive(memberEmailReceive.equals("Y"));
		}

		memberRepository.save(memberEntity);
	}

	public void verifyMemberState(Long memberNumber) {
		MemberEntity memberEntity = memberRepository.findMemberByMemberNumber(memberNumber);
		memberEntity.setMemberState(1);
		memberEntity.setEmailVerified(true);
		memberRepository.save(memberEntity);
	}

	public void verifyMemberEmail(Long memberNumber) {
		MemberEntity memberEntity = memberRepository.findMemberByMemberNumber(memberNumber);
		memberEntity.setEmailVerified(true);
		memberRepository.save(memberEntity);
	}

	public void changeMemberPassword(Map<String, String> param) throws
		NoSuchAlgorithmException {

		Long memberNumber = Long.parseLong(param.get("member_number").trim());
		String memberNewPassword = param.get("new_password").trim();
		String memberNewPasswordRepeat = param.get("new_password_repeat").trim();

		MemberEntity memberEntity = memberRepository.findMemberByMemberNumber(memberNumber);

		nullValueChecker.check(memberNewPassword, memberNewPasswordRepeat);

		regexChecker.checkPasswordPattern(memberNewPassword);

		if (memberNewPassword.equals(memberNewPasswordRepeat) != true) {
			throw new MemberPasswordInvalidException("변경할 비밀번호가 일치 하지 않습니다.");
		}

		String newPasswordEncoded = passwordUtil.encodePassword(memberNewPassword);

		memberEntity.setMemberPassword(newPasswordEncoded);

		memberRepository.save(memberEntity);
	}

	public void changeMemberPassword(String email, String newPassword) throws
		NoSuchAlgorithmException {

		MemberEntity memberEntity = memberRepository.findMemberByMemberEmail(email);

		String newPasswordEncoded = passwordUtil.encodePassword(newPassword);

		memberEntity.setMemberPassword(newPasswordEncoded);

		memberRepository.save(memberEntity);
	}

	public boolean checkValid(String memberEmail, String memberPassword) throws NoSuchAlgorithmException {

		MemberEntity memberEntity = memberRepository.findMemberByMemberEmail(memberEmail);

		passwordUtil.match(memberPassword, memberEntity.getMemberPassword());

		return true;
	}

}
