package com.ram.hms.service;

import com.ram.hms.dto.UserDto;
import com.ram.hms.payload.SignUpRequest;

public interface IUserService{	
	void saveUser(SignUpRequest signUpRequest);			
	UserDto getUser();
}
