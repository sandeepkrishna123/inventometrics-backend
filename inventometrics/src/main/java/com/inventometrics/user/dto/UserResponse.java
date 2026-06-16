package com.inventometrics.user.dto;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String employeeCode;

    private String name;

    private String email;
}