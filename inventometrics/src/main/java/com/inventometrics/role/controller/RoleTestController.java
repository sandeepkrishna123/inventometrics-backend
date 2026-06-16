package com.inventometrics.role.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTestController {
@GetMapping("/api/test/auditor")
@PreAuthorize("hasRole('AUDITOR')")
public String auditorAcess() {
	return "Auditor Access Granted";
}
@GetMapping("/api/test/manager")
@PreAuthorize("hasRole('MANAGER')")
public String managerAccess() {
    return "Manager Access Granted";
}

@GetMapping("/api/test/admin")
@PreAuthorize("hasRole('ADMIN')")
public String adminAccess() {
    return "Admin Access Granted";
}

@GetMapping("/api/test/common")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','AUDITOR')")
public String commonAccess() {
    return "Common Access Granted";
}
}
