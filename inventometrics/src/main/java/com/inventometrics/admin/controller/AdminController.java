package com.inventometrics.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventometrics.admin.dto.CreateUserRequest;
import com.inventometrics.admin.service.AdminService;
import com.inventometrics.auth.dto.RegisterResponse;
import com.inventometrics.common.Exception.EmailAlreadyExistsException;
import com.inventometrics.common.Exception.EmployeeCodeAlreadyExists;
import com.inventometrics.common.Exception.RoleNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create-manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponse> createManager(
            @Valid @RequestBody CreateUserRequest request)
            throws EmailAlreadyExistsException,
                   EmployeeCodeAlreadyExists,
                   RoleNotFoundException {

        RegisterResponse response =
                adminService.createManager(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @PostMapping("/create-auditor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponse> createAuditor(
            @Valid @RequestBody CreateUserRequest request)
            throws EmailAlreadyExistsException,
                   EmployeeCodeAlreadyExists,
                   RoleNotFoundException {

        RegisterResponse response =
                adminService.createAuditor(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }
}