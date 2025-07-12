package com.fileprocessing.mail.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fileprocessing.mail.model.UserDto;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Integer>{
	
	UserDto findByUserName(String userName);

}
