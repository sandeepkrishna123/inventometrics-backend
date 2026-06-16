package com.inventometrics.auth.dto;

import com.inventometrics.role.enums.RoleType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
@NotBlank
private String employeeCode;
@NotBlank
private String name;
@Email
@NotBlank
private String email;
@NotBlank
@Size(min=6 ,max=10)
private String password;
private RoleType role;
}
