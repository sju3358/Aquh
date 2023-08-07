package com.ssafy.team8alette.domain.member.auth.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.auth.model.dao.MemberLoginInfoRepository;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberLoginInfo;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberType;
import com.ssafy.team8alette.domain.member.auth.util.NullValueChecker;
import com.ssafy.team8alette.domain.member.auth.util.PasswordUtil;
import com.ssafy.team8alette.global.exception.MemberDuplicatedException;
import com.ssafy.team8alette.global.exception.UnAuthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAuthNaverService {

	private final MemberRepository memberRepository;
	private final PasswordUtil passwordUtil;
	private final MemberLoginInfoRepository memberLoginInfoRepository;
	private final NullValueChecker nullValueChecker;

	public void login(Long memberNumber, String refreshToken) throws SQLException {

		if (memberNumber == -1) {
			throw new MemberDuplicatedException("회원이 존재하지 않습니다");
		}

		MemberLoginInfo memberLoginInfo = memberLoginInfoRepository.findById(Long.toString(memberNumber)).orElse(null);

		if (memberLoginInfo != null) {
			memberLoginInfo.setRefreshToken(refreshToken);
			memberLoginInfoRepository.save(memberLoginInfo);
		} else {
			memberLoginInfo = new MemberLoginInfo();
			memberLoginInfo.setSocialLogin(true);
			memberLoginInfo.setMemberNumber(Long.toString(memberNumber));
			memberLoginInfo.setRefreshToken(refreshToken);
			memberLoginInfoRepository.save(memberLoginInfo);
		}

	}

	public Long register(JSONObject naverMemberInfo) throws NoSuchAlgorithmException {

		String memberId = naverMemberInfo.get("id").toString();
		String memberEmail = naverMemberInfo.get("email").toString();
		String memberNickname = naverMemberInfo.get("nickname").toString();
		String memberName = naverMemberInfo.get("name").toString();

		MemberEntity memberEntity = memberRepository.findMemberByMemberId(memberId);

		if (memberEntity != null) {

			if (memberEntity.getMemberState() == 2) {
				throw new UnAuthorizedException("이미 탈퇴한 회원입니다");
			} else {
				return memberEntity.getMemberNumber();
			}
		}

		nullValueChecker.check(
			memberEmail,
			memberId,
			memberName,
			memberNickname);

		MemberEntity newMemberEntity = new MemberEntity();

		newMemberEntity.setMemberId(memberId);
		newMemberEntity.setMemberEmail(memberEmail);
		newMemberEntity.setMemberPassword(passwordUtil.encodePassword(passwordUtil.getRandomPassword()));
		newMemberEntity.setMemberNickname("N_" + memberNickname);
		newMemberEntity.setMemberName(memberName);
		newMemberEntity.setMemberState(1);
		newMemberEntity.setMemberType(MemberType.NA.toString());
		newMemberEntity.setEmailVerified(true);
		newMemberEntity.setEmailReceive(true);
		memberRepository.save(newMemberEntity);

		return memberRepository.findMemberByMemberId(memberId).getMemberNumber();
	}
}
