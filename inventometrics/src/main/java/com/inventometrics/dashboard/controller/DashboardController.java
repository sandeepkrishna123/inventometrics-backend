package com.inventometrics.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventometrics.dashboard.dto.AdminDashboardResponse;
import com.inventometrics.dashboard.dto.AuditorDashboardResponse;
import com.inventometrics.dashboard.dto.ManagerDashboardResponse;
import com.inventometrics.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<AdminDashboardResponse>
    getAdminDashboard() {

        return ResponseEntity.ok(
                dashboardService.getAdminDashboard());
    }
    
    @GetMapping("/manager")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ManagerDashboardResponse>getManagerDashboard(){
    	return ResponseEntity.ok(dashboardService.getManagerDashboard());
    }
    @GetMapping("/Auditor")
    @PreAuthorize("hasRole('AUDITOR')")
    public ResponseEntity<AuditorDashboardResponse>
    getAuditorDashboard(
            Authentication authentication) {

        return ResponseEntity.ok(
                dashboardService.getAuditorDashboard(
                        authentication.getName()));
    }
}