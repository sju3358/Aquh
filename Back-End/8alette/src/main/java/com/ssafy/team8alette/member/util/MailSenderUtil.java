package com.ssafy.team8alette.member.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailSenderUtil {

	private final JavaMailSender javaMailSender;

	public void sendVerifyEmailMessage(Long memberNumber, String email) throws IllegalAccessException {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();

			message.addRecipients(Message.RecipientType.TO, email);

			String serverAddress = "localhost:8080";
			String apiAddress = "/api/v1//api/v1/member/url/email-certification";
			String queryString = "?member_number=" + memberNumber.toString();

			String url = serverAddress + apiAddress + queryString;

			message.setSubject("이메일 인증을 해주세요");
			message.setText("링크로 들어가 이메일 인증을 해주세요!");
			message.setText(url);

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
}