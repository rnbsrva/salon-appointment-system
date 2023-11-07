package com.akerke.loggerlib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class LoggerLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoggerLibApplication.class, args);
	}


}
