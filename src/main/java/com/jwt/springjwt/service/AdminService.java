package com.jwt.springjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.springjwt.Entity.Product;
import com.jwt.springjwt.dao.ProductDao;

//Admin Interface Implementation
@Service
public class AdminService implements AdminInterface{
	
		//Product Jpa Repo. Object
		@Autowired
		private ProductDao dao;
		
		//Delete product
		@Override
		public void deleteProduct(int bookId) {
			if (this.dao.existsById(bookId))
			{
			 Product ed=this.dao.getById(bookId);
			 this.dao.delete(ed);
			}	
		}
		
		//update product
		@Override
		public void updateProduct(Product product) {
			 this.dao.save(product);
		}
		//Add product
		@Override
		public void addProduct(Product product) {
			this.dao.save(product);
		}


}
