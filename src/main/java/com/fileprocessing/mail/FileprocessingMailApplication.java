package com.fileprocessing.mail;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
	Read all the stores from csv file.
 	if the store in csv file is available in "current_stores" table 
	then update that store in the "stores" table
	after completion send a mail with the csv file attached.
	validation of csv file:
	 	max size 4mb, 
 	 	file type csv
	 	store_id length 5
	Implement jwt token 	
*/

@SpringBootApplication
@EnableScheduling
public class FileprocessingMailApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FileprocessingMailApplication.class, args);
	}
}
