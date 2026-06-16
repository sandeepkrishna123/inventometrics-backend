package com.inventometrics.admin.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventometrics.admin.dto.CreateUserRequest;
import com.inventometrics.auth.dto.RegisterResponse;
import com.inventometrics.common.Exception.EmailAlreadyExistsException;
import com.inventometrics.common.Exception.EmployeeCodeAlreadyExists;
import com.inventometrics.common.Exception.RoleNotFoundException;
import com.inventometrics.role.entity.Role;
import com.inventometrics.role.enums.RoleType;
import com.inventometrics.role.repositry.RoleRepo;
import com.inventometrics.user.entity.User;
import com.inventometrics.user.repositry.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse createManager(
            CreateUserRequest request)
            throws EmailAlreadyExistsException,
                   EmployeeCodeAlreadyExists,
                   RoleNotFoundException {

        return createUser(request, RoleType.MANAGER);
    }

    public RegisterResponse createAuditor(
            CreateUserRequest request)
            throws EmailAlreadyExistsException,
                   EmployeeCodeAlreadyExists,
                   RoleNotFoundException {

        return createUser(request, RoleType.AUDITOR);
    }

    private RegisterResponse createUser(
            CreateUserRequest request,
            RoleType roleType)
            throws EmailAlreadyExistsException,
                   EmployeeCodeAlreadyExists,
                   RoleNotFoundException {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already exists");
        }

        if (userRepo.existsByEmployeeCode(
                request.getEmployeeCode())) {

            throw new EmployeeCodeAlreadyExists(
                    "Employee code already exists");
        }

        Role role = roleRepo
                .findByRoleName(roleType.name())
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                roleType + " role not found"));

        User user = new User();

        user.setEmployeeCode(
                request.getEmployeeCode());

        user.setName(
                request.getName());

        user.setEmail(
                request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        user.setEnabled(true);

        user.setCreatedAt(
                LocalDateTime.now());

        user.setRole(role);

        User savedUser =
                userRepo.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getEmployeeCode(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole().getRoleName()
        );
    }
}