package com.inventometrics.audit.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventometrics.audit.dto.AuditDetailsResponse;
import com.inventometrics.audit.dto.AuditRequest;
import com.inventometrics.audit.dto.AuditResponse;
import com.inventometrics.audit.enums.AuditStatus;
import com.inventometrics.audit.service.AuditService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/audit")
@Validated
public class Auditcontroller {
private final AuditService Auditservice;
@PostMapping
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public ResponseEntity<AuditResponse> createAudit(
        @Valid @RequestBody AuditRequest request,
        Authentication authentication) {

    String email = authentication.getName();

    AuditResponse response =
            Auditservice.createAudit(
                    request,
                    email);

    return new ResponseEntity<>(
            response,
            HttpStatus.CREATED);
}
@PutMapping("/{auditId}/assign/{auditorId}")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public ResponseEntity<AuditResponse> assignAuditor(
        @PathVariable Long auditId,
        @PathVariable Long auditorId) {

    AuditResponse response =
            Auditservice.assignAuditor(
                    auditId,
                    auditorId);

    return ResponseEntity.ok(response);
}
@GetMapping("/my-audits")
@PreAuthorize("hasRole('AUDITOR')")
public ResponseEntity<List<AuditResponse>> getMyAudits(
        Authentication authentication) {

    String email =
            authentication.getName();

    List<AuditResponse> response =
            Auditservice.getMyAudits(
                    email);

    return ResponseEntity.ok(
            response);
}
@GetMapping("/submitted")
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
public ResponseEntity<List<AuditResponse>>
getSubmittedAudits() {

    return ResponseEntity.ok(
            Auditservice.getSubmittedAudits());
}
@PutMapping("/{auditId}/approve")
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
public ResponseEntity<AuditResponse>
approveAudit(
        @PathVariable Long auditId) {

    return ResponseEntity.ok(
            Auditservice.approveAudit(
                    auditId));
}
@PutMapping("/{auditId}/reject")
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
public ResponseEntity<AuditResponse>
rejectAudit(
        @PathVariable Long auditId) {

    return ResponseEntity.ok(
            Auditservice.rejectAudit(
                    auditId));
}
@GetMapping("/all")
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
public ResponseEntity<List<AuditResponse>> getAllAudits() {

    return ResponseEntity.ok(
            Auditservice.getAllAudits());
}
@GetMapping("/{auditId}")
@PreAuthorize("hasAnyRole('MANAGER','ADMIN','AUDITOR')")
public ResponseEntity<AuditDetailsResponse>getDetailsbyId(@PathVariable Long auditId){
	return ResponseEntity.ok(Auditservice.getAuditDetails(auditId));
}
@GetMapping
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
public ResponseEntity<Page<AuditResponse>>
getAudits(
        @RequestParam(defaultValue = "0")
        int page,

        @RequestParam(defaultValue = "10")
        int size) {

    return ResponseEntity.ok(
            Auditservice.getAudits(
                    page,
                    size));
}
@GetMapping("/search")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public ResponseEntity<List<AuditResponse>>
searchAudit(
        @RequestParam String name) {

    return ResponseEntity.ok(
            Auditservice.searchAudit(
                    name));
}
@GetMapping("/status/{status}")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public ResponseEntity<List<AuditResponse>>
getByStatus(
        @PathVariable AuditStatus status) {

    return ResponseEntity.ok(
            Auditservice.getAuditsByStatus(
                    status));
}
@PutMapping("/{auditId}/submit")
@PreAuthorize("hasRole('AUDITOR')")
public ResponseEntity<AuditResponse>
submitAudit(
        @PathVariable Long auditId) {

    return ResponseEntity.ok(
            Auditservice.submitAudit(
                    auditId));
}
}
