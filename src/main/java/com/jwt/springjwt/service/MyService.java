package com.jwt.springjwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.springjwt.Entity.RegisterUser;
import com.jwt.springjwt.dao.Impl;

//Impl class implementation-->used here for creating soft bond
@Service
public class MyService {
	
	//Impl class object
	@Autowired
	private Impl impl;
	
	//to save user
	public void usersave(RegisterUser user) {
		this.impl.userSave(user);
	}
	
	//to fetch all user
	public List<RegisterUser> userfetch(){
		return this.impl.AllUser();
	}
}
