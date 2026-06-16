package com.inventometrics.finding.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventometrics.finding.dto.FindingRequest;
import com.inventometrics.finding.dto.FindingResponse;
import com.inventometrics.finding.service.AuditFindingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/findings")
@RequiredArgsConstructor
@Validated
public class AuditFindingController {

    private final AuditFindingService findingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('AUDITOR','ADMIN')")
    public ResponseEntity<FindingResponse> createFinding(
            @Valid @RequestBody FindingRequest request,
            Authentication authentication) {

        String email =
                authentication.getName();

        FindingResponse response =
                findingService.createFinding(
                        request,
                        email);

        return new ResponseEntity<>(
                response,
        
                HttpStatus.CREATED);
    }
    @GetMapping("/audit/{auditId}")
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public ResponseEntity<List<FindingResponse>>
    getFindingsByAudit(
            @PathVariable Long auditId) {

        return ResponseEntity.ok(
                findingService.getFindingsByAudit(
                        auditId));
    }
}