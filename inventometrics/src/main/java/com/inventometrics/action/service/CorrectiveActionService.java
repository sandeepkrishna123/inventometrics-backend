package com.inventometrics.action.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.inventometrics.action.dto.CorrectiveActionRequest;
import com.inventometrics.action.dto.CorrectiveActionResponse;
import com.inventometrics.action.entity.CorrectiveAction;
import com.inventometrics.action.enums.CorrectiveActionStatus;
import com.inventometrics.action.repositry.CorrectiveActionRepo;
import com.inventometrics.audit.Entity.Audit;
import com.inventometrics.audit.Repositry.AuditRepo;
import com.inventometrics.user.entity.User;
import com.inventometrics.user.repositry.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectiveActionService {

    private final CorrectiveActionRepo actionRepo;
    private final AuditRepo auditRepo;
    private final UserRepo userRepo;

    public CorrectiveActionResponse createAction(
            CorrectiveActionRequest request) {

        Audit audit =
                auditRepo.findById(
                        request.getAuditId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Audit not found"));

        User assignedUser =
                userRepo.findById(
                        request.getAssignedUserId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        CorrectiveAction action =
                new CorrectiveAction();

        action.setTitle(
                request.getTitle());

        action.setDescription(
                request.getDescription());

        action.setDueDate(
                request.getDueDate());

        action.setCreatedAt(
                LocalDateTime.now());

        action.setStatus(
                CorrectiveActionStatus.OPEN);

        action.setAudit(audit);

        action.setAssignedTo(
                assignedUser);

        CorrectiveAction saved =
                actionRepo.save(action);

        return mapToResponse(saved);
    }

    private CorrectiveActionResponse mapToResponse(
            CorrectiveAction action) {

        CorrectiveActionResponse response =
                new CorrectiveActionResponse();

        response.setId(
                action.getId());

        response.setTitle(
                action.getTitle());

        response.setDescription(
                action.getDescription());

        response.setStatus(
                action.getStatus().name());

        response.setAuditCode(
                action.getAudit().getAuditCode());

        response.setAssignedTo(
                action.getAssignedTo().getName());

        response.setDueDate(
                action.getDueDate());

        response.setCreatedAt(
                action.getCreatedAt());

        return response;
    }
    public List<CorrectiveActionResponse>getMyactions(String email){
    	List<CorrectiveAction>actions=actionRepo.findByAssignedToEmail(email);
    	return actions.stream().map(this::mapToResponse).toList();
    }
    public CorrectiveActionResponse startAction(Long actionId) {
    	CorrectiveAction action=actionRepo.findById(actionId).orElseThrow(()->new RuntimeException("action is not there"));
    	action.setStatus(CorrectiveActionStatus.IN_PROGRESS);
    	return mapToResponse(actionRepo.save(action));
    }
    public CorrectiveActionResponse completeAction(Long actionId) {
    	CorrectiveAction action=actionRepo.findById(actionId).orElseThrow(()->new RuntimeException("action is not there"));
    	action.setStatus(CorrectiveActionStatus.COMPLETED);
    	return mapToResponse(actionRepo.save(action));
    }
}