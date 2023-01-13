package com.contentplusplus.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println(
				"\n=========DEFAULT CREDENTIALS=========\nIf used dbscript_mysql.sql then....\nUsername:admin@admin\nPassword:admin@admin\n=====================================\n");
	}

}
