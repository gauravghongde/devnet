package com.rstack.devnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rstack"})
public class DevnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevnetApplication.class, args);
	}

}
