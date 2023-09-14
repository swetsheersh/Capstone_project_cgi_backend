package com.jwt.springjwt.service;

import com.jwt.springjwt.Entity.Product;
//Admin interface
public interface AdminInterface {
	//abstruct method
	public void deleteProduct(int bookId);
	public void updateProduct(Product product);
	public void addProduct(Product product);

}
