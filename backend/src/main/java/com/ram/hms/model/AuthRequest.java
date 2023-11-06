package com.ram.hms.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
	@NotBlank
	private String userName;
	@NotBlank
	private String password;



}
