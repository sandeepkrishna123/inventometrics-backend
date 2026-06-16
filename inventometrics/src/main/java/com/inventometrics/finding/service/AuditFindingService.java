package com.inventometrics.finding.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.inventometrics.audit.Entity.Audit;
import com.inventometrics.audit.Repositry.AuditRepo;
import com.inventometrics.audit.enums.AuditStatus;
import com.inventometrics.finding.dto.FindingRequest;
import com.inventometrics.finding.dto.FindingResponse;
import com.inventometrics.finding.entity.AuditFinding;
import com.inventometrics.finding.repositry.AuditFindingRepo;
import com.inventometrics.user.entity.User;
import com.inventometrics.user.repositry.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditFindingService {
private final AuditFindingRepo auditfindingRepo;
private final AuditRepo auditRepo;
private final UserRepo userRepo;
public FindingResponse createFinding(
        FindingRequest request,
        String email) {

    User user =
            userRepo.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "User not found"));

    Audit audit =
            auditRepo.findById(
                    request.getAuditId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Audit not found"));

    if (!audit.getAssignedAuditor()
            .getEmail()
            .equals(email)) {

        throw new RuntimeException(
                "You are not assigned to this audit");
    }

    audit.setStatus(
            AuditStatus.IN_PROGRESS);

    auditRepo.save(audit);

    AuditFinding finding =
            new AuditFinding();

    finding.setTitle(
            request.getTitle());

    finding.setObservation(
            request.getObservation());

    finding.setRecommendation(
            request.getRecommendation());

    finding.setSeverity(
            request.getSeverity());

    finding.setCreatedAt(
            LocalDateTime.now());

    finding.setAudit(
            audit);

    finding.setCreatedBy(
            user);

    AuditFinding saved =
            auditfindingRepo.save(
                    finding);

    FindingResponse response =
            new FindingResponse();

    response.setId(
            saved.getId());

    response.setTitle(
            saved.getTitle());

    response.setObservation(
            saved.getObservation());

    response.setRecommendation(
            saved.getRecommendation());

    response.setSeverity(
            saved.getSeverity().name());

    response.setAuditCode(
            saved.getAudit()
                    .getAuditCode());

    response.setCreatedAt(
            saved.getCreatedAt());

    return response;
}
public List<FindingResponse>
getFindingsByAudit(Long auditId) {

    List<AuditFinding> findings =
            auditfindingRepo.findByAuditId(
                    auditId);

    return findings.stream()
            .map(finding -> {

                FindingResponse response =
                        new FindingResponse();

                response.setId(
                        finding.getId());

                response.setTitle(
                        finding.getTitle());

                response.setObservation(
                        finding.getObservation());

                response.setRecommendation(
                        finding.getRecommendation());

                response.setSeverity(
                        finding.getSeverity().name());

                response.setAuditCode(
                        finding.getAudit()
                               .getAuditCode());

                response.setCreatedAt(
                        finding.getCreatedAt());

                return response;
            })
            .toList();
}
}
