package com.jwt.springjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.jwt.springjwt.JwtHelper.JwtAuthenticationEntryPoint;
import com.jwt.springjwt.JwtHelper.JwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class MySecurityConfig{
	
	//JwtAuthenticationFilter class object -->jwt security
	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	
	//JwtAuthenticationEntryPoint class object -->jwt security
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	//chaning method of spring security to set configuration
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
   
		 http
	    	.cors()
	    	.disable()
	    	.csrf()
	    	.disable()
	    	.authorizeRequests()
	    	.requestMatchers("/dashboard/admin/**").hasAuthority("ADMIN")
	    	.requestMatchers("/dashboard/user/**").hasAuthority("USER")
	    	.requestMatchers("/token").permitAll()
	    	.requestMatchers("/dashboard/**").access("hasRole('DASHBOARD_ACCESS')")
	    	.requestMatchers("/save").permitAll()
            .anyRequest().authenticated()
	    	.and()
	    	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    	.and()
	    	.exceptionHandling().authenticationEntryPoint(entryPoint);
		
		//filtering the request by using JWT filter
		http
	    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	//UserDetailsService bean --> we can get object of the same class wherever required
	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailsServiceImpl();
	}
	//password encoder bean --> we can get object of the same class wherever required 
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//DaoAuthenticationProvider bean, just like a implementation of class
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	//AuthenticationManager class ,just like a implementation of class
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	            .build();
		
	}
}