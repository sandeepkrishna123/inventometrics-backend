package com.inventometrics.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterResponse {
private Long id;
private String employeeCode;
private String name;
private String email;
private String role;
}
