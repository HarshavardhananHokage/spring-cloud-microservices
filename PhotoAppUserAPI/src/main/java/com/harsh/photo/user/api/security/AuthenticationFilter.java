package com.harsh.photo.user.api.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsh.photo.user.api.service.UsersService;
import com.harsh.photo.user.api.shared.UserDTO;
import com.harsh.photo.user.api.ui.model.LoginRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	Environment environment;
	UsersService usersService;
	
	@Autowired
	public AuthenticationFilter(Environment environment, UsersService usersService, AuthenticationManager authenticationManager) {
		this.environment = environment;
		this.usersService = usersService;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			
			return getAuthenticationManager().
					authenticate(new UsernamePasswordAuthenticationToken(
							loginRequest.getEmailID(), 
							loginRequest.getPassword(), 
							new ArrayList<>())
							);
			
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String emailID = ((User) authResult.getPrincipal()).getUsername();
		
		UserDTO userDTO = usersService.getUserByEmail(emailID);
		
		//Algorithm algo = Algorithm.HMAC512(environment.getProperty("token.hmac512.secret"));
		
//		@SuppressWarnings("deprecation")
//		String token = JWT.create()
//				.withSubject(userDTO.getUserID())
//				.withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration.time"))))
//				.sign(Algorithm.HMAC512(environment.getProperty("token.hmac512.secret")));
		
		String token = Jwts.builder()
                .setSubject(userDTO.getUserID())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration.time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.hmac512.secret") )
                .compact();
		
		
		response.addHeader("token", token);
		response.addHeader("userID", userDTO.getUserID());
	}
	
	
	

}
