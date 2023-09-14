package com.jwt.springjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan({"com.jwt.springjwt.*"})
public class SpringjwtserverApplication {
	//Main method
	public static void main(String[] args) {
		SpringApplication.run(SpringjwtserverApplication.class, args);
	}

}
