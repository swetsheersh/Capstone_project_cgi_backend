package com.jwt.springjwt.service;

import java.util.List;

import com.jwt.springjwt.Entity.Product;
import com.jwt.springjwt.Entity.RegisterUser;
//user interface
public interface UserInterface {
	//abstruct methods
	public List<Product> getall();
	public Product getProductById(int id);
	public List<Product> getProductByCat(String category); 
	public void addtocart(int id,int userId);
	public void deltocart(int id,String username);
	public List<Product> getallcart(String username);
	public boolean existsById(int id);
	public RegisterUser getRegisterUserByUserName(String username);
	public void addToOrderHistory(int id,int userId);
	public List<Product> getAllFromOrderHistory(String username);
	
}
