package com.inventometrics.user.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.inventometrics.user.entity.User;
import com.inventometrics.user.repositry.UserRepo;
import com.inventometrics.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public List<UserResponse> getAuditors() {

        List<User> auditors =
                userRepo.findByRole_RoleName(
                        "AUDITOR"
                );

        return auditors.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private UserResponse mapToResponse(
            User user) {

        UserResponse response =
                new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setEmployeeCode(
                user.getEmployeeCode());

        return response;
    }
}