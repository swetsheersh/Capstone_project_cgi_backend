package com.jwt.springjwt.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jwt.springjwt.Entity.RegisterUser;

//implementation of user JPA  repo. object
@Repository
public class Impl{
	
	//user JPA  repo. object
	@Autowired
	private UserDao userDao;
	
	//saving user
	public void userSave(RegisterUser user) {
		this.userDao.save(user);
	}
	//to get all user
	public List<RegisterUser> AllUser(){
		return userDao.findAll();
	}
}
