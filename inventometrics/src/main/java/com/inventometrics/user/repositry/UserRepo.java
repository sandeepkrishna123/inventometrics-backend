package com.inventometrics.user.repositry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventometrics.user.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{
Optional<User> findByEmail(String email);
boolean existsByEmail(String email);

boolean existsByEmployeeCode(String employeeCode);
List<User> findByRole_RoleName(String roleName);
}
