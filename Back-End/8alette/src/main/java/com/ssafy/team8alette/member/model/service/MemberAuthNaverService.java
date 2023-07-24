package com.ssafy.team8alette.member.model.service;

import org.springframework.stereotype.Service;

@Service
public class MemberAuthNaverService {

	// private final RegexChecker regexChecker;
	// private final PasswordEncryptor passwordEncryptor;
	// private final JwtTokenProvider jwtTokenProvider;
	//
	// @Autowired
	// public MemberAuthNaverService(RegexChecker regexChecker, PasswordEncryptor passwordEncryptor,
	// 	JwtTokenProvider jwtTokenProvider) {
	//
	// 	this.regexChecker = regexChecker;
	// 	this.passwordEncryptor = passwordEncryptor;
	// 	this.jwtTokenProvider = jwtTokenProvider;
	// }
	//
	// public Member login(String memberId, String name) throws
	// 	SQLException,
	// 	NoSuchAlgorithmException,
	// 	NullPointerException {
	//
	// 	Member loginUser = memberMapper.findUserById(memberId);
	//
	// 	if (loginUser == null) {
	// 		this.register(memberId, name);
	// 		loginUser = memberMapper.findUserById(memberId);
	// 	}
	// 	return loginUser;
	// }
	//
	// public void register(String memberId, String memberName) throws SQLException, NoSuchAlgorithmException {
	//
	// 	String memberPassword = passwordEncryptor.getRandomPassword(12);
	// 	String memberPasswordEncoded = passwordEncryptor.encodePassword(memberPassword);
	// 	memberMapper.insertUser(new Member(memberName, memberId, memberPasswordEncoded));
	// }

}
