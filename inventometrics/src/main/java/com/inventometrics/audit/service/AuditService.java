package com.inventometrics.audit.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventometrics.audit.Entity.Audit;
import com.inventometrics.audit.Repositry.AuditRepo;
import com.inventometrics.audit.dto.AuditDetailsResponse;
import com.inventometrics.audit.dto.AuditRequest;
import com.inventometrics.audit.dto.AuditResponse;
import com.inventometrics.audit.enums.AuditStatus;
import com.inventometrics.user.repositry.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepo auditRepo;
    private final UserRepo userRepo;

    public AuditResponse createAudit(
            AuditRequest request,
            String userEmail) {

        com.inventometrics.user.entity.User createdBy =
                userRepo.findByEmail(userEmail)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        long count = auditRepo.count() + 1;

        String auditCode = "AUD-2026-" + count;

        Audit audit = new Audit();

        audit.setAuditCode(auditCode);

        audit.setAuditName(
                request.getAuditName());

        audit.setDescription(
                request.getDescription());

        audit.setDueDate(
                request.getDuedate());

        audit.setCreatedAt(
                LocalDateTime.now());

        audit.setStatus(
                AuditStatus.PLANNED);

        audit.setCreatedBy(
                createdBy);

        audit.setAssignedAuditor(
                null);

        Audit savedAudit =
                auditRepo.save(audit);

        AuditResponse response =
                new AuditResponse();

        response.setId(
                savedAudit.getId());

        response.setAuditCode(
                savedAudit.getAuditCode());

        response.setAuditName(
                savedAudit.getAuditName());

        response.setDescription(
                savedAudit.getDescription());

        response.setStatus(
                savedAudit.getStatus().name());

        response.setDueDate(
                savedAudit.getDueDate());

        response.setCreatedAt(
                savedAudit.getCreatedAt());

        response.setCreatedBy(
                savedAudit.getCreatedBy().getName());

        response.setAssignedAuditor(
                savedAudit.getAssignedAuditor() == null
                        ? "Not Assigned"
                        : savedAudit.getAssignedAuditor().getName());

        return response;
    }
    public AuditResponse assignAuditor(
            Long auditId,
            Long auditorId) {

        Audit audit = auditRepo.findById(auditId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Audit not found"));

        com.inventometrics.user.entity.User auditor =
                userRepo.findById(auditorId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Auditor not found"));

        if (!auditor.getRole()
                .getRoleName()
                .equals("AUDITOR")) {

            throw new RuntimeException(
                    "Selected user is not an auditor");
        }

        audit.setAssignedAuditor(auditor);

        audit.setStatus(
                AuditStatus.ASSIGNED);

        Audit savedAudit =
                auditRepo.save(audit);

        AuditResponse response =
                new AuditResponse();

        response.setId(
                savedAudit.getId());

        response.setAuditCode(
                savedAudit.getAuditCode());

        response.setAuditName(
                savedAudit.getAuditName());

        response.setDescription(
                savedAudit.getDescription());

        response.setStatus(
                savedAudit.getStatus().name());

        response.setDueDate(
                savedAudit.getDueDate());

        response.setCreatedAt(
                savedAudit.getCreatedAt());

        response.setCreatedBy(
                savedAudit.getCreatedBy().getName());

        response.setAssignedAuditor(
                savedAudit.getAssignedAuditor().getName());

        return response;
    }
    public List<AuditResponse> getMyAudits(
            String email) {

        List<Audit> audits =
                auditRepo.findByAssignedAuditorEmail(
                        email);

        return audits.stream()
                .map(audit -> {

                    AuditResponse response =
                            new AuditResponse();

                    response.setId(
                            audit.getId());

                    response.setAuditCode(
                            audit.getAuditCode());

                    response.setAuditName(
                            audit.getAuditName());

                    response.setDescription(
                            audit.getDescription());

                    response.setStatus(
                            audit.getStatus().name());

                    response.setDueDate(
                            audit.getDueDate());

                    response.setCreatedAt(
                            audit.getCreatedAt());

                    response.setCreatedBy(
                            audit.getCreatedBy().getName());

                    response.setAssignedAuditor(
                            audit.getAssignedAuditor() == null
                                    ? "Not Assigned"
                                    : audit.getAssignedAuditor().getName());

                    return response;
                })
                .toList();
    }
    public List<AuditResponse> getSubmittedAudits() {

        List<Audit> audits =
                auditRepo.findByStatus(
                        AuditStatus.SUBMITTED);

        return audits.stream()
                .map(audit -> {

                    AuditResponse response =
                            new AuditResponse();

                    response.setId(
                            audit.getId());

                    response.setAuditCode(
                            audit.getAuditCode());

                    response.setAuditName(
                            audit.getAuditName());

                    response.setDescription(
                            audit.getDescription());

                    response.setStatus(
                            audit.getStatus().name());

                    response.setDueDate(
                            audit.getDueDate());

                    response.setCreatedAt(
                            audit.getCreatedAt());

                    response.setCreatedBy(
                            audit.getCreatedBy().getName());

                    response.setAssignedAuditor(
                            audit.getAssignedAuditor() == null
                                    ? "Not Assigned"
                                    : audit.getAssignedAuditor().getName());

                    return response;

                })
                .toList();
    }
    public AuditResponse approveAudit(
            Long auditId) {

        Audit audit =
                auditRepo.findById(auditId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Audit not found"));

        audit.setStatus(
                AuditStatus.APPROVED);

        Audit savedAudit =
                auditRepo.save(audit);

        return mapToResponse(savedAudit);
    }
    private AuditResponse mapToResponse(
            Audit audit) {

        AuditResponse response =
                new AuditResponse();

        response.setId(audit.getId());
        response.setAuditCode(audit.getAuditCode());
        response.setAuditName(audit.getAuditName());
        response.setDescription(audit.getDescription());
        response.setStatus(audit.getStatus().name());
        response.setDueDate(audit.getDueDate());
        response.setCreatedAt(audit.getCreatedAt());

        response.setCreatedBy(
                audit.getCreatedBy().getName());

        response.setAssignedAuditor(
                audit.getAssignedAuditor() == null
                        ? "Not Assigned"
                        : audit.getAssignedAuditor().getName());

        return response;
    }
	public AuditResponse rejectAudit(
	        Long auditId) {

	    Audit audit =
	            auditRepo.findById(auditId)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Audit not found"));

	    audit.setStatus(
	            AuditStatus.REJECTED);

	    Audit savedAudit =
	            auditRepo.save(audit);

	    return mapToResponse(savedAudit);
	}
	public List<AuditResponse> getAllAudits() {

	    List<Audit> audits = auditRepo.findAll();

	    return audits.stream()
	            .map(this::mapToResponse)
	            .toList();
	}
	public AuditDetailsResponse getAuditDetails(Long auditId) {
		Audit audit=auditRepo.findById(auditId).orElseThrow(()-> new RuntimeException("auditor not found"));
		AuditDetailsResponse response=new AuditDetailsResponse();
		response.setId(audit.getId());
		response.setAuditCode(audit.getAuditCode());
		response.setAuditName(audit.getAuditName());
		response.setDescription(audit.getDescription());
		response.setStatus(audit.getStatus().name());
		response.setDueDate(
	            audit.getDueDate());

	    response.setCreatedAt(
	            audit.getCreatedAt());

	    response.setCreatedBy(
	            audit.getCreatedBy().getName());

	    response.setAssignedAuditor(
	            audit.getAssignedAuditor() == null
	                    ? "Not Assigned"
	                    : audit.getAssignedAuditor().getName());

	    return response;
		
		
		
		
	}
	
	public Page<AuditResponse>getAudits(int page,int size){
		Pageable pageble=PageRequest.of(page, size);
		Page<Audit>audits=auditRepo.findAll(pageble);
		return audits.map(this::mapToResponse);
	}
	public List<AuditResponse> searchAudit(
	        String auditName) {

	    return auditRepo
	            .findByAuditNameContainingIgnoreCase(
	                    auditName)
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
}
	
	public List<AuditResponse> getAuditsByStatus(
	        AuditStatus status) {

	    return auditRepo
	            .findByStatus(status)
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
	}
	public AuditResponse submitAudit(Long auditId) {

	    Audit audit =
	            auditRepo.findById(auditId)
	                    .orElseThrow(() ->
	                            new RuntimeException(
	                                    "Audit not found"));

	    audit.setStatus(
	            AuditStatus.SUBMITTED);

	    Audit savedAudit =
	            auditRepo.save(audit);

	    return mapToResponse(savedAudit);
	}





}