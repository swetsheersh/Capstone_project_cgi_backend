package com.jwt.springjwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.springjwt.Entity.Product;
import com.jwt.springjwt.Entity.RegisterUser;
import com.jwt.springjwt.dao.ProductDao;
import com.jwt.springjwt.dao.UserDao;


//Implementation service class of userInterface
@Service
public class UserService implements UserInterface{
	
	//JPa Product Repo. object
	@Autowired
	private ProductDao dao;
	
	//JPA RegisterUser Repo. object
	@Autowired
	private UserDao userdao;
	
	//Get all product
	@Override
	public List<Product> getall() {
		return dao.findAll();
	}
	
	//product by id
	@Override
	public Product getProductById(int id) {
		return this.dao.getById(id);
	}
	
	//product by category
	@Override
	public List<Product> getProductByCat(String category) {
		return dao.findAllByCategory(category);
	}
	
	//Add to cart
	@Transactional
	@Override
	public void addtocart(int id,int userId) {
		Product prod=dao.getById(id);
		RegisterUser user=userdao.getById(userId);
		if(!(user.getProducts().contains(prod))) {
			List<Product> pr=user.getProducts();
			pr.add(prod);
			userdao.save(user);
		}
		//prod.setUser(user);
		dao.save(prod);
		this.addToOrderHistory(id, userId);
	}
	
	//Delete to cart
	@Transactional
	@Override
	public void deltocart(int id,String username) {
		Product prod=dao.getById(id);
		//prod.setUser(null);
		RegisterUser user=userdao.getRegisterUserByUserName(username);
		List<Product> li=user.getProducts();
		if(li.contains(prod)) {
			li.remove(prod);
		}
		dao.save(prod);
	}

	//get all from cart
	@Override
	public List<Product> getallcart(String username) {
		RegisterUser user=this.userdao.getRegisterUserByUserName(username);
		return user.getProducts();
	}

	//Product exists by id
	@Override
	public boolean existsById(int id) {
		return dao.existsById(id);
	}

	//to get RegisterUser object from db using username
	@Override
	public RegisterUser getRegisterUserByUserName(String username) {
		return userdao.getRegisterUserByUserName(username);
	}

	//Add to order history
	@Transactional
	@Override
	public void addToOrderHistory(int id, int userId) {
		Product prod=dao.getById(id);
		RegisterUser user=userdao.getById(userId);
		List<Product> pd=user.getOrderHistory();
		pd.add(prod);
		user.setOrderHistory(pd);
		userdao.save(user);
	}
	
	//Get all from order history
	@Override
	public List<Product> getAllFromOrderHistory(String username) {
		RegisterUser user=this.userdao.getRegisterUserByUserName(username);
		return user.getOrderHistory();
	}
	
	

}
