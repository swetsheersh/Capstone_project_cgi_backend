package com.jwt.springjwt.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwt.springjwt.Entity.RegisterUser;

//Implementation of Spring Security UsserDetails class
public class CustomUserDetails implements UserDetails {
	private RegisterUser user;
	
	//constructor
	public CustomUserDetails(RegisterUser user) {
		super();
		this.user = user;
	}
	
	//it will return granted authority whether user is able to access uri's of admin or user role
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(user.getRole());
		return List.of(simpleGrantedAuthority);
	}
	//password getter ,taking password form user object itself
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	//username getter ,taking username form user object itself
	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	//is Account Not Expired
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//is Account Not Locked
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//is Credentials Not Expired
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//is enabled
	@Override
	public boolean isEnabled() {
		return true;
	}

}
