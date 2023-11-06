package com.ram.hms.service;

import java.util.Arrays;

import com.ram.hms.model.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ram.hms.dto.UserDto;
import com.ram.hms.entity.User;
import com.ram.hms.repository.RoleRepository;
import com.ram.hms.repository.UserRepository;


@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository repo;
	@Autowired private PasswordEncoder passwordEncoder;

	public User save(User user) {
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);

		return repo.save(user);
	}
	@Override
	public UserDto getUser() {
		String userName = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User user = repo.findByUserName(userName);
		UserDto userDto = new UserDto();
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setUserName(user.getUserName());
		return userDto;
	}
}
