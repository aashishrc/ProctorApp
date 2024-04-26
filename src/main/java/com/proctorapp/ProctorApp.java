package com.proctorapp;

import com.proctorapp.model.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@Controller
@SpringBootApplication(scanBasePackages = {"com.proctorapp.controller","com.proctorapp.dao","com.proctorapp.model"})
public class ProctorApp {

	public static void main(String[] args) {
		SpringApplication.run(ProctorApp.class, args);
	}

	@GetMapping("/ProctorApp")
	public String Welcome(){
		return "index";
	}
}
