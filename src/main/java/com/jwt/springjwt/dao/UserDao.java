package com.jwt.springjwt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.jwt.springjwt.Entity.RegisterUser;

//Register User JPA Repo with JPQL & HQL query
@Service
public interface UserDao extends JpaRepository<RegisterUser, Integer>{
	@Query("select u from RegisterUser u where u.username=:user")
	public RegisterUser getRegisterUserByUserName(@Param("user") String username);
	public boolean existsByUsername(String username);
}
