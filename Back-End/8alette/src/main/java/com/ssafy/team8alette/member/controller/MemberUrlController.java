package com.ssafy.team8alette.member.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/member/url")
@RestController
public class MemberUrlController {

	// private final MailSender mailSender;
	//
	// @Autowired
	// public MemberUrlController(MailSender mailSender){
	// 	this.mailSender = mailSender;
	// }
	//
	// @PostMapping("/email-certification")
	// public ResponseEntity<Map<String, Object>> sendVerifyEmailUrlRequest(@RequestBody Map<String, String> param) throws {
	//
	// 	//이메일 보내기
	//
	// 	Map<String, Object> token = new HashMap<>();
	// 	token.put("message", "success");
	// 	HttpStatus status = HttpStatus.ACCEPTED;
	//
	// 	return new ResponseEntity<Map<String, Object>>(token, status);
	// }
	//
	// @GetMapping("/password-find")
	// public ResponseEntity<Map<String, Object>> sendChangePasswordUrlRequest(@PathVariable Long memberNumber){
	// 	//패스워드 변경 url 이메일 보내기
	//
	// 	Map<String, Object> token = new HashMap<>();
	// 	token.put("message", "success");
	// 	HttpStatus status = HttpStatus.ACCEPTED;
	//
	// 	return new ResponseEntity<Map<String, Object>>(token, status);
	// }
}
