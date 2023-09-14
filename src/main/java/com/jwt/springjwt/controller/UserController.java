package com.jwt.springjwt.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.jwt.springjwt.Entity.Product;
import com.jwt.springjwt.Entity.RegisterUser;
import com.jwt.springjwt.dao.ProductDao;
import com.jwt.springjwt.dao.UserDao;
import com.jwt.springjwt.service.UserService;

@RestController
public class UserController {
	
	//UserService object
	@Autowired
	private UserService service;
	
	//Get All Product from List
	@GetMapping(path="/dashboard/user/getallProducts")
	public ResponseEntity<?> getAll(){
		List<Product> prod=service.getall();
		return new ResponseEntity<List<Product>>(prod,HttpStatus.OK);
	}
	
	//Get Product by id
	@GetMapping(path="/dashboard/user/id/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id) throws Exception{
		System.out.println(id);
		if(service.existsById(id)) {
			Product prod=service.getProductById(id);
			return new ResponseEntity<Product>(prod,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Product Not Exists",HttpStatus.NOT_FOUND);
	}
	
	//get product by category
	@GetMapping(path="/dashboard/user/catogery/{id}")
	public ResponseEntity<?> allbycat(@PathVariable("id") String id) {
		List<Product> pd=service.getProductByCat(id);
		return new ResponseEntity<List<Product>>(pd,HttpStatus.OK);
	}
	//Adding Product to Respective user cart
	//Transaction annotation is used here bz here both get and update detail is executing together
	@Transactional
	@GetMapping(path="/dashboard/user/addtocart/{id}")
	public ResponseEntity<?> addtocart(@PathVariable("id") Integer id,Principal p) {
		if(service.existsById(id)) {
			RegisterUser user=service.getRegisterUserByUserName(p.getName());
			service.addtocart(id, user.getId());
			return new ResponseEntity<String>("Product Added to Cart",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Product Not Exists",HttpStatus.NOT_FOUND);
	}
	//Removing product from cart
	@Transactional
	@DeleteMapping(path="/dashboard/user/remove/{id}")
	public ResponseEntity<?> deltocart(@PathVariable("id") Integer id,Principal p) {
		RegisterUser user=service.getRegisterUserByUserName(p.getName());
		if(service.existsById(id)) {
			Product pd=service.getProductById(id);
			if(user.getProducts().contains(pd)) {
				service.deltocart(id,p.getName());
				return new ResponseEntity<String>("Product Removed From Cart",HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Product Not Found in Cart",HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>("Product Not Exists",HttpStatus.NOT_FOUND);
	}
	//Get all product from Respective cart
	@GetMapping(path="/dashboard/user/getallcart")
	public ResponseEntity<?> getAllFromCart(Principal p){
		List<Product> prod=this.service.getallcart(p.getName());
		return new ResponseEntity<List<Product>>(prod,HttpStatus.OK);
	}
	
	//Get All the order History
	@GetMapping(path="/dashboard/user/orderhistory")
	public ResponseEntity<?> getAllFromOrderHistory(Principal p){
		List<Product> prod=this.service.getAllFromOrderHistory(p.getName());
		return new ResponseEntity<List<Product>>(prod,HttpStatus.OK);
	}
}
