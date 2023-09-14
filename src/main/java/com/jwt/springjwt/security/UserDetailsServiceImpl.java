package com.jwt.springjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.springjwt.Entity.RegisterUser;
import com.jwt.springjwt.dao.UserDao;
//implementation class of spring security userDetailService class
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	//Jpa RegisterUser repo object
	@Autowired
	private UserDao userDao;
	//implemented method to load user with the help of username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RegisterUser user= userDao.getRegisterUserByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("Could not found User!!");
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
		
	}

}
