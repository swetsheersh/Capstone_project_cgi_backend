package com.jwt.springjwt.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jwt.springjwt.Entity.Product;
import com.jwt.springjwt.Entity.RegisterUser;

//Product jPA Repo with Custom JPQL Query 
@Service
public interface ProductDao extends JpaRepository<Product,Integer> {
	public List<Product> findAllByCategory(String category);
	//public boolean existsBySiteName(String siteName);
}
