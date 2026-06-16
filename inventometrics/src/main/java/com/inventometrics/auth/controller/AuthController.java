package com.inventometrics.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventometrics.auth.Service.AuthService;
import com.inventometrics.auth.dto.LoginRequest;
import com.inventometrics.auth.dto.LoginResponse;
import com.inventometrics.auth.dto.RegisterRequest;
import com.inventometrics.auth.dto.RegisterResponse;
import com.inventometrics.common.Exception.EmailAlreadyExistsException;
import com.inventometrics.common.Exception.EmployeeCodeAlreadyExists;
import com.inventometrics.common.Exception.RoleNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) throws EmailAlreadyExistsException, EmployeeCodeAlreadyExists, RoleNotFoundException {

        RegisterResponse response =
                authService.Register(request);
 
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request));
    }
    
}