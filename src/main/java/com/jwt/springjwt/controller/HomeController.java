package com.jwt.springjwt.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.springjwt.Entity.JwtResponse;
import com.jwt.springjwt.Entity.LoginUser;
import com.jwt.springjwt.Entity.Product;
import com.jwt.springjwt.Entity.RegisterUser;

import com.jwt.springjwt.JwtHelper.JwtUtil;
import com.jwt.springjwt.dao.ProductDao;
import com.jwt.springjwt.dao.UserDao;
import com.jwt.springjwt.security.UserDetailsServiceImpl;
import com.jwt.springjwt.service.MyService;

import jakarta.servlet.http.HttpSession;

@RestController
public class HomeController {
	
	//RegisterUser JPA Repo. Object
	@Autowired
	private UserDao userDao;
	
	//Service class Object
	@Autowired 
	private MyService service;
	
	//Password Encoder
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//Spring Security UserDetail implementation class object
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	//JWT Authentication Manager object
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//JWT Util class object, it is having method for for creating Authentication token
	@Autowired
	private JwtUtil jwtUtil;
	
	//Register User
	@CrossOrigin("*")
	@RequestMapping(path="/save",method=RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody RegisterUser user) {
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		if(this.userDao.existsByUsername(user.getUsername())) {
			return new ResponseEntity<String>("UserId Allready Exixts!!",HttpStatus.CONFLICT);
		}
		System.out.println(user);
		this.service.usersave(user);
		return new ResponseEntity<String>("UserId Created Successfully!!",HttpStatus.CREATED);
	}
/*	@RequestMapping(path="/get")
	public List<RegisterUser> getUser() {
		return this.service.userfetch();
	}
	*/
	
	//Login User
	@CrossOrigin("*")
	@RequestMapping(path="/token",method=RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody LoginUser userModel) throws Exception{
		System.out.println(userModel);
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getUsername(),userModel.getPassword()));
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}catch(BadCredentialsException e) {
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}
		
		//fine area...
		
		UserDetails userDetails= this.userDetailsServiceImpl.loadUserByUsername(userModel.getUsername());
		String token = this.jwtUtil.generateToken(userDetails);
		System.out.println("JWT :"+token);
		
		//return in this format {"token":"value"}
		//this.token1=new JwtResponse(token);
		return ResponseEntity.ok(new JwtResponse(token));
	}


}

