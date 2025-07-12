package com.fileprocessing.mail.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	private static final String SECRET_KEY="Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
	
	
	/*   Generating the token                  */

	public String generateToken(String username) {
		Map<String,String> claims = new HashMap<>();
		return createToken(claims,username);
		
	}
	
	public String createToken(Map<String,String> claims, String subject) {  
		//subject means username, and any other extra info we pass in claims
		
		return Jwts.builder()
			.claims(claims)
			.subject(subject)
			.header().empty().add("typ","JWT")
			.and()
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50))
			.signWith(getSigninKey())
			.compact();
		
	}
	
	public SecretKey getSigninKey() {
		
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	/*   Extracting the username and validating the token                  */
	
	public String extractUserName(String jwt) {
		
		Claims claim = Jwts.parser()  //claim means subject
		.verifyWith(getSigninKey())
		.build()
		.parseSignedClaims(jwt)
		.getPayload();
		
		return claim.getSubject();
		
	}
	
	public boolean isTokenExpired(String jwt) {
		
		Claims claim = Jwts.parser()  //claim means subject
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
		
		Date date = claim.getExpiration();
		return date.before(new Date());
		
	}
	
	public boolean validateToken(String token, String username) {
		System.out.println("Validate token");
		return extractUserName(token).equals(username) && !isTokenExpired(token);
		
	}
	

}
