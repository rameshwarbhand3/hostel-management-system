package com.ram.hms.controller;

import com.ram.hms.entity.Role;
import com.ram.hms.entity.User;
import com.ram.hms.dto.UserDto;
import com.ram.hms.model.AuthRequest;
import com.ram.hms.model.AuthResponse;
import com.ram.hms.repository.UserRepository;
import com.ram.hms.security.JwtTokenUtil;
import com.ram.hms.service.IUserService;
import com.ram.hms.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthController {


    private JwtTokenUtil jwtUtil;

    private AuthenticationManager authManager;
    private IUserService userServiceImpl;
    private UserRepository userRepository;


    public AuthController(
            JwtTokenUtil jwtUtil,
            AuthenticationManager authManager,
            UserServiceImpl userServiceImpl,
            UserRepository userRepository
    ) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.userServiceImpl = userServiceImpl;
        this.userRepository = userRepository;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getUserName(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    private String mapToRoleNames(Role role) {
        return role.getName();
    }

    //	@PostMapping("/signup")
//    public ResponseEntity<?> saveUser(@RequestBody SignUpRequest signUpRequest) {
//    	System.out.println(signUpRequest);
//    	if (userRepository.existsByUserName(signUpRequest.getUserName())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new AuthResponse(false,"Username is already taken!"));
//		}
//    	userServiceImpl.saveUser(signUpRequest);
//    	URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/signup").toUriString());
//    	return ResponseEntity.created(uri).body(new AuthResponse(true,"User registered successfully!"));
//    }
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
        User createdUser = userServiceImpl.save(user);
        URI uri = URI.create("/users/" + createdUser.getId());

        UserDto userDto = new UserDto(createdUser.getId(), createdUser.getUserName(),createdUser.getFirstName(), createdUser.getLastName());

        return ResponseEntity.created(uri).body(userDto);
    }


}
