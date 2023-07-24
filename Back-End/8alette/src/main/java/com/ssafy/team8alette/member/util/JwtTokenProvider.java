package com.ssafy.team8alette.member.util;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private static final String SALT = "campingSecretcampingSecretcampingSecretcampingSecret";
	private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 1; // 분단위
	private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 2; // 주단위

	public <T> String createAccessToken(String key, T data) throws UnsupportedEncodingException {
		//front-end에서 refresh token에 대한 기능 구현 미완으로 인해 임시로 만료기한을 길게 잡습니다.
		return create(key, data, "access-token", 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MINUTES);
	}

	public <T> String createRefreshToken(String key, T data) throws UnsupportedEncodingException {
		//front-end에서 refresh token에 대한 기능 구현 미완으로 인해 임시로 만료기한을 길게 잡습니다.
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

	public Map<String, Object> getTokens(String memberEmail) throws SQLException, UnsupportedEncodingException {

		Map<String, Object> tokens = new HashMap<>();

		String accessToken = this.createAccessToken("memberEmail", memberEmail);
		String refreshToken = this.createRefreshToken("memberEmail", memberEmail);

		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);

		return tokens;
	}

	public boolean checkToken(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser()
				.setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

