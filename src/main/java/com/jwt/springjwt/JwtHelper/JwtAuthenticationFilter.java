package com.jwt.springjwt.JwtHelper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.springjwt.security.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//JWT filter class extends jwt OncePerRequestFilter class
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	//UserDetailsServiceImpl object
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	//JwtUtil object
	@Autowired
	private JwtUtil jwtUtil;

	//overriden method for filtering all the request whether token is valid or not
	//If found valid token then only it will allow to execute next step
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//get jwt
		//Bearer
		//validate
		
		String requestTokenHeader = request.getHeader("Authorization");
		String userName=null;
		String jwtToken=null;
		//null and format
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken=requestTokenHeader.substring(7);
			try {
				
				userName=this.jwtUtil.extractUsername(jwtToken);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			UserDetails details =this.userDetailsServiceImpl.loadUserByUsername(userName);
			
			//security
			
			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}else {
				System.out.println("Token is not Validated...");
			}
			
		}
		filterChain.doFilter(request, response);
	}

}
