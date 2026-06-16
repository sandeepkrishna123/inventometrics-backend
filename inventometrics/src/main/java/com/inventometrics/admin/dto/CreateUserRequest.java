package com.inventometrics.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateUserRequest {
   @NotBlank
	private String employeeCode;
   @NotBlank
	private String name;
   @Email
	private String email;
   @NotBlank
	private String password;
}
