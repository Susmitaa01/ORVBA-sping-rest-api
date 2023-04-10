package com.capgemini.vehiclebreakdown.jwtfilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.capgemini.vehiclebreakdown.service.UserService;
import com.capgemini.vehiclebreakdown.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	//To validate token
	@Autowired
	private JwtUtil util;

	//To load userdetails from app userservice
	@Autowired
	private UserService service;

	//To handle exceptions that occur during processing of request
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver exceptionResolver;

	//To intercept HTTP request and validate JWT Token
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("inside filter");
		String authorizationHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		String role = null;
		
		//extract JWT token from header
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			System.out.println("header found");
			token = authorizationHeader.substring(7);
			//try to extract username from token
			try {
				userName = util.extractUsername(token);
				role = util.getUserRole(token);
				System.out.println("username found");
			} catch (IllegalArgumentException e) {
				System.out.println("failed to get jwt");
				exceptionResolver.resolveException(request, response, null, e);
				return;
			} catch (ExpiredJwtException e) {
				exceptionResolver.resolveException(request, response, null, e);
				return;
			} catch (SignatureException e) {
				exceptionResolver.resolveException(request, response, null, e);
				return;
			}
		}
		
		//if username extracted and user not authenticated
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		
			UserDetails userDetails = service.loadUserByUsernameAndRole(userName, role);
			//if token is valid
			if (util.validateToken(token, userDetails)) {
				System.out.println("token validated");
				//create Authentication obj
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				//set auth obj to make user authenticated for duration of request
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				System.out.println(authToken);
			}
		}
		System.out.println("filter continue....");
		//allow request to continue processing by other endpoint
		filterChain.doFilter(request, response);
	}

}
