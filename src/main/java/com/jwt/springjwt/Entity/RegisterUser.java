package com.jwt.springjwt.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//RegisterUser class mapped with database using Hibernate implementation JPA
@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RegisterUser {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String username;
	private String password;
	private String role;
	
	//Seperate Relation Table will be created with fetch lazy effect having a relation many to many
	@JsonIgnore
	@Column(unique = false)
	@ManyToMany(targetEntity=Product.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    private List<Product> products;
	
	//Seperate Relation Table will be created with fetch lazy effect having a relation many to many
	@Column(unique = false)
	@ManyToMany(targetEntity=Product.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Product> orderHistory;

	//getter and setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role.toUpperCase();
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Product> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(List<Product> orderHistory) {
		this.orderHistory = orderHistory;
	}

	//constructor
	public RegisterUser(int id, String name, String username, String password, String role, List<Product> products,
			List<Product> orderHistory) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role.toUpperCase();
		this.products = products;
		this.orderHistory = orderHistory;
	}

	public RegisterUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	//tostring method
	@Override
	public String toString() {
		return "RegisterUser [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password
				+ ", role=" + role + ", products=" + products + ", orderHistory=" + orderHistory + "]";
	}
	
	
}
