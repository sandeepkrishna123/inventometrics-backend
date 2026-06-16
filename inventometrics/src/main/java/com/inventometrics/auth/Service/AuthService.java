package com.inventometrics.auth.Service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventometrics.auth.dto.LoginRequest;
import com.inventometrics.auth.dto.LoginResponse;
import com.inventometrics.auth.dto.RegisterRequest;
import com.inventometrics.auth.dto.RegisterResponse;
import com.inventometrics.common.Exception.EmailAlreadyExistsException;
import com.inventometrics.common.Exception.EmployeeCodeAlreadyExists;
import com.inventometrics.common.Exception.InvalidCredentialsException;
import com.inventometrics.common.Exception.RoleNotFoundException;
import com.inventometrics.role.entity.Role;
import com.inventometrics.role.repositry.RoleRepo;
import com.inventometrics.security.JWT.JWTservice;
import com.inventometrics.user.entity.User;
import com.inventometrics.user.repositry.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
private final UserRepo userRepositry;
private final RoleRepo roleRepositry;
private final PasswordEncoder passwordEncoder;
private final JWTservice jwtService;
public RegisterResponse Register(RegisterRequest request) throws EmailAlreadyExistsException, EmployeeCodeAlreadyExists, RoleNotFoundException {
	if(userRepositry.existsByEmail(request.getEmail())) {
		throw new EmailAlreadyExistsException("the mail is already exists");
	}
	if(userRepositry.existsByEmployeeCode(request.getEmployeeCode())) {
		throw new EmployeeCodeAlreadyExists("the employee code exists");
	}
	
    User user=new User();
    Role role = roleRepositry.findByRoleName(
	        request.getRole().name()
	)
	.orElseThrow(() ->
	        new RuntimeException("Role not found"));

	user.setRole(role);
    user.setEmployeeCode(request.getEmployeeCode());

    user.setName(request.getName());
    user.setEmail(request.getEmail());

    user.setPassword(
            passwordEncoder.encode(
                    request.getPassword()));

    user.setEnabled(true);
    user.setCreatedAt(LocalDateTime.now());
    user.setRole(role);

    User savedUser = userRepositry.save(user);

    return new RegisterResponse(
            savedUser.getId(),
            savedUser.getEmployeeCode(),
            savedUser.getName(),
            savedUser.getEmail(),
            savedUser.getRole().getRoleName());
	
}
public LoginResponse login(LoginRequest request) {
	User user= userRepositry.findByEmail(request.getEmail()).orElseThrow(()->new InvalidCredentialsException("the credentials are not correct"));
	boolean valid=passwordEncoder.matches(request.getPassword(),user.getPassword());
	if(!valid) {
		throw new InvalidCredentialsException("Invalid Email or password");
	}
	String token=jwtService.generateToken(user);
	return new LoginResponse(token,user.getRole().getRoleName());
	}
}
