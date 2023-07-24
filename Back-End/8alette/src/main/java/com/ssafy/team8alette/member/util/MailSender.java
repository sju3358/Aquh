package com.ssafy.team8alette.member.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailSender {

	private final JavaMailSender javaMailSender;

	@Autowired
	public MailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	private void sendVerifyEmailMessage(int memberNumber, String email) throws IllegalAccessException {
		try {
			MimeMessage mimeMessage = createVerifyEmailMessage(memberNumber, email);
			javaMailSender.send(mimeMessage);
		} catch (Exception mailException) {
			mailException.printStackTrace();
			throw new IllegalAccessException();
		}
	}

	public void sendChangePasswordEmail(String code, String email) throws IllegalAccessException {
		try {
			MimeMessage mimeMessage = createChangePasswordMessage(code, email);
			javaMailSender.send(mimeMessage);
		} catch (Exception mailException) {
			mailException.printStackTrace();
			throw new IllegalAccessException();
		}
	}

	private MimeMessage createVerifyEmailMessage(int memberNumber, String email) throws Exception {
		MimeMessage message = javaMailSender.createMimeMessage();
		//이메일인증 url 담긴 메일 생성
		return message;
	}

	private MimeMessage createChangePasswordMessage(String tempPassword, String email) throws Exception {

		MimeMessage message = javaMailSender.createMimeMessage();

		message.addRecipients(Message.RecipientType.TO, email);
		message.setSubject("Camping 임시비밀번호가 발급되었습니다.");
		message.setText("임시 비밀번호 : " + tempPassword);
		message.setFrom("tjdfkr011@naver.com");

		return message;
	}

}