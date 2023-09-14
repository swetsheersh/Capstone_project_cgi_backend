package com.jwt.springjwt.Entity;

//Login class to accept login credentials in json format
public class LoginUser {
	private String username;
	private String password;
	//constructor
	public LoginUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	//getter and setter
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	//tostring method
	@Override
	public String toString() {
		return "UserModel [Username=" + username + ", password=" + password + "]";
	}
	
}
