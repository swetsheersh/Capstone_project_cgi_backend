package com.jwt.springjwt.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.jwt.springjwt.Entity.Product;
import com.jwt.springjwt.dao.ProductDao;
import com.jwt.springjwt.service.AdminService;


@RestController
public class AdminController {
	
	//AdminService object
	@Autowired
	private AdminService service;
	
	//Product JPA Repo. object 
	@Autowired
	private ProductDao dao;
	
	//update product details
	@PutMapping(path="/dashboard/admin/updateProduct")
	public ResponseEntity<?> productupdate(@RequestBody Product ed){
		if(this.dao.existsById(ed.getProductId())){
			this.service.updateProduct(ed);
			return new ResponseEntity<Product> (ed,HttpStatus.OK);
			
		}
	return new ResponseEntity<String> ("product doesn't exist",HttpStatus.CONFLICT);
	
	}
	//Delete Product with the help of Product id
	@DeleteMapping(path="/dashboard/admin/deleteProduct/{id}")
	public ResponseEntity<?> productdelete(@PathVariable("id") int id){
		if(this.dao.existsById(id)){
			this.service.deleteProduct(id);
			return new ResponseEntity<String> ("product deleted",HttpStatus.OK);	
		}
	return new ResponseEntity<String> ("product doesn't exist",HttpStatus.NOT_FOUND);
	}
	
	//Add New Product Detail
	@PostMapping(path="/dashboard/admin/addProduct")
	public ResponseEntity<?> addProduct(@RequestBody Product product){
		if(!(dao.existsById(product.getProductId()))) {
			service.addProduct(product);
			return new ResponseEntity<Product>(product,HttpStatus.CREATED);
		}
        return new ResponseEntity<String>("Product Allready there!!",HttpStatus.CONFLICT);
	}

}
