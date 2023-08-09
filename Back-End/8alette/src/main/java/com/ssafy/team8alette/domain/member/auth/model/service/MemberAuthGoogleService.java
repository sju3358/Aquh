package com.ssafy.team8alette.domain.member.auth.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.member.auth.model.dao.MemberLoginInfoRepository;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberLoginInfo;
import com.ssafy.team8alette.domain.member.auth.model.dto.MemberType;
import com.ssafy.team8alette.domain.member.auth.util.NullValueChecker;
import com.ssafy.team8alette.domain.member.auth.util.PasswordUtil;
import com.ssafy.team8alette.domain.member.record.model.dao.MemberRecordRepository;
import com.ssafy.team8alette.domain.member.record.model.dto.entity.MemberRecord;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolGrantRepository;
import com.ssafy.team8alette.domain.symbol.model.dao.SymbolRepository;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.entity.Grant;
import com.ssafy.team8alette.domain.symbol.model.dto.grant.key.GrantID;
import com.ssafy.team8alette.domain.symbol.model.dto.symbol.Symbol;
import com.ssafy.team8alette.global.exception.MemberDuplicatedException;
import com.ssafy.team8alette.global.exception.UnAuthorizedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAuthGoogleService {

	private final MemberRepository memberRepository;
	private final PasswordUtil passwordUtil;
	private final MemberLoginInfoRepository memberLoginInfoRepository;
	private final NullValueChecker nullValueChecker;
	private final MemberRecordRepository memberRecordRepository;
	private final SymbolRepository symbolRepository;
	private final SymbolGrantRepository symbolGrantRepository;

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

		String memberId = naverMemberInfo.get("sub").toString();
		String memberEmail = naverMemberInfo.get("email").toString();
		String memberNickname = naverMemberInfo.get("name").toString();
		String memberName = naverMemberInfo.get("name").toString();

		Member member = memberRepository.findMemberByMemberId(memberId);

		if (member != null) {

			if (member.getMemberState() == 2) {
				throw new UnAuthorizedException("이미 탈퇴한 회원입니다");
			} else {
				return member.getMemberNumber();
			}
		}

		nullValueChecker.check(
			memberEmail,
			memberId,
			memberName,
			memberNickname);

		Member newMember = new Member();

		newMember.setMemberId(memberId);
		newMember.setMemberEmail(memberEmail);
		newMember.setMemberPassword(passwordUtil.encodePassword(passwordUtil.getRandomPassword()));
		newMember.setMemberNickname("G_" + memberNickname);
		newMember.setMemberName(memberName);
		newMember.setMemberState(1);
		newMember.setMemberType(MemberType.GO.toString());
		newMember.setEmailVerified(true);
		newMember.setEmailReceive(true);
		memberRepository.save(newMember);

		MemberRecord memberRecord = new MemberRecord();
		memberRecord.setMemberNumber(newMember.getMemberNumber());
		memberRecord.setMember(newMember);
		memberRecord.setDate(new Date());
		memberRecordRepository.save(memberRecord);

		List<Symbol> symbols = symbolRepository.findAll();
		Symbol symbol = symbols.get(0);
		Grant grant = new Grant();
		GrantID grantID = new GrantID();
		grantID.setGrantedMemberNumber(newMember.getMemberNumber());
		grantID.setSymbolNumber(1L);
		grant.setGrantID(grantID);
		grant.setMemberRecord(memberRecord);
		grant.setSymbol(symbol);
		grant.setDate(new Date());
		symbolGrantRepository.save(grant);

		return memberRepository.findMemberByMemberId(memberId).getMemberNumber();
	}
}
