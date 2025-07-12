package com.fileprocessing.mail.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.GetExchange;

import com.fileprocessing.mail.model.UserDto;
import com.fileprocessing.mail.security.JwtUtils;
import com.fileprocessing.mail.service.StoreService;

@RestController
@RequestMapping("/store/v1")
public class StoreController {
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtil;
	
	@PostMapping
	public ResponseEntity<?> readCsv(@RequestParam("file") MultipartFile file) throws IOException{
		
		return new ResponseEntity<String>(storeService.readCsv(file),HttpStatus.OK);
	}
	
	@GetMapping("/home")
	public ResponseEntity<String> homePage(){
		
		return new ResponseEntity<String>("welcome to store service !!",HttpStatus.OK);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto user) {
		try {
			System.out.println("Hello");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
			String token=jwtUtil.generateToken(user.getUserName());
			return new ResponseEntity<String>(token,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/welcome/{name}")
	public ResponseEntity<String> dummyAuthenticatedPage(@PathVariable String name) {
		return new ResponseEntity<String>("Hello "+name,HttpStatus.OK);
		
	}

}
