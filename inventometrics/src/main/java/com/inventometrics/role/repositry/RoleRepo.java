package com.inventometrics.role.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventometrics.role.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
Optional<Role>findByRoleName(String roleName);
}
