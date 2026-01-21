package com.todolist.security.Security;


import java.util.Date;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;


@Component
public class JwtUtils {
	private static final Logger logger=LoggerFactory.getLogger(JwtUtils.class);
	@Value("${spring.app.jwtSecret}")
	private String jwtSecret;
	@Value("${spring.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public String getJwtFromHeader(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		logger.debug("Authorization Header: {}",bearerToken);
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	public String generateTokenFromUsername(UserDetails userDetails) {
		String username = userDetails.getUsername();
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key())
				.compact();
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
	}
	
	private Key key() {
		byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
		// Ensure at least 256 bits (32 bytes) as required by HS256
		if (keyBytes.length < 32) {
			byte[] padded = new byte[32];
			System.arraycopy(keyBytes, 0, padded, 0, keyBytes.length);
			keyBytes = padded;
		}
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
			return true;
		}
		catch(MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		}
		catch(ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		}
		catch(UnsupportedJwtException e) {
			logger.error("JWT token is unsupport: {}", e.getMessage());
		}
		catch(IllegalArgumentException e) {
			logger.error("JWT token is empty: {}", e.getMessage());
		}
		return false;
	}
}
