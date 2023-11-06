package com.ram.hms.service;

import com.ram.hms.dto.UserDto;
import com.ram.hms.entity.User;

public interface IUserService{	

	User save(User user);
	UserDto getUser();

}
