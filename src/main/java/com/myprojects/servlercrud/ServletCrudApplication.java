package com.myprojects.servlercrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
@ServletComponentScan
public class ServletCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletCrudApplication.class, args);
	}

}
