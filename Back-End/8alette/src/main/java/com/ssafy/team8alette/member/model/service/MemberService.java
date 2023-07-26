package com.ssafy.team8alette.member.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.member.exception.DuplicatedMemberException;
import com.ssafy.team8alette.member.exception.InvalidMemberPasswordException;
import com.ssafy.team8alette.member.exception.NullValueException;
import com.ssafy.team8alette.member.exception.RegexException;
import com.ssafy.team8alette.member.model.dao.MemberRepository;
import com.ssafy.team8alette.member.model.dto.Member;
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

	public Long register(String memberEmail, String memberNickname, String memberPassword,
		String memberPasswordRepeat) throws NoSuchAlgorithmException {

		nullValueChecker.check(
			memberEmail,
			memberPassword,
			memberPasswordRepeat,
			memberNickname);

		if (memberRepository.findMemberByMemberNickname(memberNickname) != null) {
			throw new DuplicatedMemberException();
		}

		if (regexChecker.checkValidationRegisterPassword(memberPassword) != true) {
			throw new RegexException("비밀번호 형식이 맞지 않습니다.");
		}

		if (memberPassword.equals(memberPasswordRepeat) != true) {
			throw new InvalidMemberPasswordException("비밀번호를 다시 확인해주세요");
		}

		String memberPasswordEncoded = passwordUtil.encodePassword(memberPassword);

		Member member = new Member();
		member.setMemberEmail(memberEmail);
		member.setMemberNickname(memberNickname);
		member.setMemberPassword(memberPasswordEncoded);
		member.setMemberState(0);
		memberRepository.save(member);

		return member.getMemberNumber();
	}

	public void deactivate(Long memberNumber) throws SQLException {
		Member member = memberRepository.findMemberByMemberNumber(memberNumber);
		member.setMemberState(2);
		memberRepository.save(member);
	}

	public Member getMemberInfo(Long memberNumber) {
		Member member = memberRepository.findMemberByMemberNumber(memberNumber);

		if (member == null)
			throw new NullValueException("회원이 존재하지 않습니다");

		return member;
	}

	public Member getMemberInfo(String memberEmail) {
		Member member = memberRepository.findMemberByMemberEmail(memberEmail);

		if (member == null)
			throw new NullValueException("회원이 존재하지 않습니다");

		return member;
	}

	public void editMemberInfo(Long memberNumber, String memberEmail, String memberNickname, String memberIntro) {

		Member member = memberRepository.findMemberByMemberNumber(memberNumber);

		if (memberEmail != null && memberEmail.equals("") != true) {
			member.setMemberEmail(memberEmail);
		}
		if (memberNickname != null && memberNickname.equals("") != true) {
			member.setMemberNickname(memberNickname);
		}

		member.setMemberIntro(memberIntro);

		memberRepository.save(member);
	}

	public void verifyMember(Long memberNumber) {
		Member member = memberRepository.findMemberByMemberNumber(memberNumber);
		member.setMemberState(1);
		memberRepository.save(member);
	}

	public void changeMemberPassword(Long memberNumber, String newPassword,
		String newPasswordRepeat) throws
		NoSuchAlgorithmException {

		Member member = memberRepository.findMemberByMemberNumber(memberNumber);

		nullValueChecker.check(newPassword, newPasswordRepeat);

		if (regexChecker.checkValidationRegisterPassword(newPassword) != true) {
			throw new RegexException("비밀번호 형식이 맞지 않습니다.");
		}

		if (newPassword.equals(newPasswordRepeat) != true) {
			throw new InvalidMemberPasswordException("변경할 비밀번호가 일치 하지 않습니다.");
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

		return passwordUtil.match(memberPassword, member.getMemberPassword());
	}
}
