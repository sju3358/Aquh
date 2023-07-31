package com.ssafy.team8alette.member.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ssafy.team8alette.member.model.dao.MemberRepository;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailSenderUtil {

	private final JavaMailSender javaMailSender;
	private final MemberRepository memberRepository;
	@Value("${front.server}")
	private String serverAddress;

	public void sendVerifyStateMessage(Long memberNumber, String email) throws IllegalAccessException {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();

			message.addRecipients(Message.RecipientType.TO, email);

			String queryString = "?member_number=" + memberNumber.toString();

			String url = "http://" + serverAddress + queryString;

			String memberName = memberRepository.findMemberByMemberNumber(memberNumber).getMemberName();

			message.setSubject(memberName + "님! Aquah에 가입해주셔서 감사합니다");
			String mailText = "";
			mailText += "링크로 들어가 이메일 인증을 해주세요!\n";
			mailText += url;
			message.setText(mailText);

			message.setFrom("tjdfkr011@naver.com");
			javaMailSender.send(message);

		} catch (Exception mailException) {
			mailException.printStackTrace();
			throw new IllegalAccessException();
		}
	}

	public void sendVerifyEmailMessage(Long memberNumber, String email) throws IllegalAccessException {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();

			message.addRecipients(Message.RecipientType.TO, email);

			String queryString = "?member_number=" + memberNumber.toString();

			String url = "http://" + serverAddress + queryString;

			String memberName = memberRepository.findMemberByMemberNumber(memberNumber).getMemberName();

			message.setSubject(memberName + "님! 변경된 이메일 인증을 해주세요");

			String mailText = "";
			mailText += "링크로 들어가 이메일 인증을 해주세요!\n";
			mailText += url;
			message.setText(mailText);

			message.setFrom("tjdfkr011@naver.com");
			javaMailSender.send(message);

		} catch (Exception mailException) {
			mailException.printStackTrace();
			throw new IllegalAccessException();
		}
	}

	public void sendTempPasswordEmail(String email, String tempPassword) throws IllegalAccessException {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();

			message.addRecipients(Message.RecipientType.TO, email);
			message.setSubject("Aquah 임시비밀번호가 발급되었습니다.");
			message.setText("임시 비밀번호 : " + tempPassword);
			message.setText("로그인 후 반드시 비밀번호 변경을 해주세요");

			message.setFrom("tjdfkr011@naver.com");
			javaMailSender.send(message);
		} catch (Exception mailException) {
			mailException.printStackTrace();
			throw new IllegalAccessException();
		}
	}

	public void sendAlarm(String memberNumber, String alarmContent) {
		//1. 멤버 정보 조회
		//2-1. 이메일정보 없으면 예외처리
		//2-2. 이메일 인증 않되어있으면 예외처리
		//2-3. 이메일 수신거부면 메일 안보내기
	}
}