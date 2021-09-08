package com.example.EMS_UST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmsUstApplication {

	@Autowired
	EMSRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(EmsUstApplication.class, args);
	}

}
