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
	 private String SECRET_KEY = "secret";

	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	    private Claims extractAllClaims(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	    }

	    private Boolean isTokenExpired(String token) {
	     return extractExpiration(token).before(new Date());
	    	//return false;
	    }

	    public String generateToken(String username) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, username);
	    }
	    
	    public String generateToken(Map<String,Object> extraClaims, String username) {
	       
	        return createToken(extraClaims, username);
	    }

	    private String createToken(Map<String, Object> claims, String username) {
	        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *60 * 10))
	        		//.setExpiration(new Date(432_000_000))
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	    }

	    public Boolean validateToken(String token,UserDetails userDetails) {
	        final String username = extractUsername(token);
	        
	        return (username.equals(userDetails.getUsername() )&& !isTokenExpired(token));
	    }
	    
	    public String getUserRole(String token) {
	    	Claims claims = extractAllClaims(token);
	    	String role = (String) claims.get("role");
	    	return role;
	    }
	    
	    
	
}
