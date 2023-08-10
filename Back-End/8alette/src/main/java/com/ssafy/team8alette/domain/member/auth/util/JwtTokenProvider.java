package com.ssafy.team8alette.domain.member.auth.util;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.ssafy.team8alette.global.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private static final String SALT = "campingSecretcampingSecretcampingSecretcampingSecret";
	private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 1; // 분단위
	private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 2; // 주단위

	//일단 REFRESH 토큰 요청안하므로 길게 잡음
	private <T> String createAccessToken(String key, T data) throws UnsupportedEncodingException {
		return create(key, data, "access-token", 1000 * 60 * ACCESS_TOKEN_EXPIRE_MINUTES * 60);
	}

	private <T> String createRefreshToken(String key, T data) throws UnsupportedEncodingException {
		return create(key, data, "refresh-token", 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MINUTES);
	}

	private <T> String create(String key, T data, String subject, long expire) throws UnsupportedEncodingException {

		Claims claims = Jwts.claims()
			.setSubject(subject)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expire));

		claims.put(key, data);

		SignatureAlgorithm signAlgorithm = SignatureAlgorithm.HS256;

		String jwt = Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setClaims(claims)
			.signWith(signAlgorithm, SALT.getBytes("UTF-8"))
			.compact();

		return jwt;
	}

	public Map<String, Object> getTokens(Long memberNumber) throws SQLException, UnsupportedEncodingException {

		Map<String, Object> tokens = new HashMap<>();

		String accessToken = this.createAccessToken("memberNumber", memberNumber);
		String refreshToken = this.createRefreshToken("memberNumber", memberNumber);

		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);

		return tokens;
	}

	public void checkToken(String jwt) {

		try {
			Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new InvalidTokenException("토큰이 유효하지 않습니다");
		}

	}

	public Long getMemberNumber(String jwt) throws ParseException {

		Base64.Decoder decoder = Base64.getDecoder();

		String payLoad = new String(decoder.decode(jwt.split("\\.")[1]));

		JSONParser parser = new JSONParser();
		JSONObject userInfo = (JSONObject)parser.parse(payLoad);

		return Long.parseLong(userInfo.get("memberNumber").toString());
	}

}

