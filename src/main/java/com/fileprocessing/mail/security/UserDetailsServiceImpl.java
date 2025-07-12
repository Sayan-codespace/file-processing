package com.fileprocessing.mail.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.fileprocessing.mail.model.UserDto;
import com.fileprocessing.mail.repo.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		
		UserDto user = userRepo.findByUserName(username);
		if(user!=null) {
			return User.builder()
					.username(username)
					.password(user.getPassword())
					.roles(user.getRoles())
					.build();
					
		}
		throw new UsernameNotFoundException("User not found!!!!");
		
	}
	
	

}
