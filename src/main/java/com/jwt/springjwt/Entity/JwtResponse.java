package com.jwt.springjwt.Entity;

//Jwt Response class to accept token from json format
public class JwtResponse {
	private String token;

	//constructor having super method
	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	//getters and setters
	public JwtResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	//toString Method
	@Override
	public String toString() {
		return "JwtResponse [token=" + token + "]";
	}
	
}
