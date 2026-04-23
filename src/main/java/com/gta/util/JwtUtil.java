package com.gta.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 관련 유틸 클래스
 */
@Slf4j
@Component
public class JwtUtil {
	@Value("${jwt.secret}")
    private String secretKey;
	
	private Key key;
	
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public String generateToken(Long userId, String role) {
	    Date now = new Date();
	    long expiryMillis;
	    
	    if ("ADMIN".equals(role)) {
	        // 관리자 → 30일
	        expiryMillis = 1000L * 60 * 60 * 24 * 30;
	    } else {
	        // 일반회원 → 3시간
	        expiryMillis = 1000L * 60 * 60 * 3;
	    }
	    
	    Date expiry = new Date(now.getTime() + expiryMillis);

	    return Jwts.builder()
	            .setSubject(String.valueOf(userId))
	            .claim("role", role)
	            .setIssuedAt(now)
	            .setExpiration(expiry)
	            .signWith(key, SignatureAlgorithm.HS256)
	            .compact();
	}
	
	public Long getUserId(String token) {
	    return Long.parseLong(
	        Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(token)
	            .getBody()
	            .getSubject()
	    );
	}

	public String getRole(String token) {
	    return (String)
	        Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(token)
	            .getBody()
	            .get("role");
	}
}
