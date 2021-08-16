package com.centric.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class PmsApplication {

	public static void main(String[] args) {
		System.out.println("welcome to pms");
		SpringApplication.run(PmsApplication.class, args);
	}

}
