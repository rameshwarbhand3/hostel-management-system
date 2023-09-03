package com.ram.hms.service;

import java.util.Arrays;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ram.hms.dto.UserDto;
import com.ram.hms.model.User;
import com.ram.hms.payload.SignUpRequest;
import com.ram.hms.repository.RoleRepository;
import com.ram.hms.repository.UserRepository;


@Service
public class UserServiceImpl implements IUserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	

	public UserServiceImpl(
			UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder passwordEncoder
			) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(SignUpRequest signUpRequest) {		
		User user = new User();
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setUserName(signUpRequest.getUserName());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_STUDENT")));
		userRepository.save(user);
	}

	@Override
	public UserDto getUser() {
		String userName = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User user = userRepository.findByUserName(userName);
		UserDto userDto = new UserDto();
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setUserName(user.getUserName());		
		return userDto;
	}

}
