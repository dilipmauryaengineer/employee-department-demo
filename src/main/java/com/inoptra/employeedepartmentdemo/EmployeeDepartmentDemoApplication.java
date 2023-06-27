package com.inoptra.employeedepartmentdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.inoptra.employeedepartmentdemo.models")
public class EmployeeDepartmentDemoApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/employeedepartmentdemo");
		SpringApplication.run(EmployeeDepartmentDemoApplication.class, args);
	}

}
