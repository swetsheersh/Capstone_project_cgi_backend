package com.jwt.springjwt.JwtHelper;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//jwt implementation class of JwtAuthenticationEntryPoint ,its works like a exception class 
//sends exception in case of invalid token
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	//overriden method for sending unauthorized request at response
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(401,"Unauthorized");
		
	}

}
