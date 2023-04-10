package com.capgemini.vehiclebreakdown.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	//used to sign and verify token
	private String SECRET_KEY = "secret";

	//extract username from jwt token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	//extract expiration date from jwt token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	//to extract specific claim from jwt token based on function
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	//extract all claims from JWT tokenn
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		//returns true if already expired
		return extractExpiration(token).before(new Date());
	}

	//generate token with username
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	//generate token using additional claim and username
	public String generateToken(Map<String, Object> extraClaims, String username) {

		return createToken(extraClaims, username);
	}

	//to generate a new JWT token for a given user, containing the specified claims
	private String createToken(Map<String, Object> claims, String username) {
		//token will be validated for 10 hr
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				// .setExpiration(new Date(432_000_000))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	//to check whether token is still valid and belong to specified user
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		//return true if token has not expired
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	//retrive role from jwt token
	public String getUserRole(String token) {
		Claims claims = extractAllClaims(token);
		String role = (String) claims.get("role");
		return role;
	}

}
