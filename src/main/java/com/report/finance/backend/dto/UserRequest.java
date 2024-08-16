package com.report.finance.backend.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	
	@NotBlank(message = "Username cannot be null")
	private String userName;
	
	@NotBlank(message = "password cannot be null")
	private String password;
	
	@NotBlank(message = "email cannot be null")
	private String email;

}
