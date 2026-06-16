package com.inventometrics.action.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.inventometrics.action.dto.CorrectiveActionRequest;
import com.inventometrics.action.dto.CorrectiveActionResponse;
import com.inventometrics.action.service.CorrectiveActionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/actions")
@RequiredArgsConstructor
@Validated
public class CorrectiveActionController {

    private final CorrectiveActionService actionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<CorrectiveActionResponse>
    createAction(
            @Valid @RequestBody
            CorrectiveActionRequest request) {

        return new ResponseEntity<>(
                actionService.createAction(
                        request),
                HttpStatus.CREATED);
    }
    @GetMapping("/my-actions")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public ResponseEntity<List<CorrectiveActionResponse>>
    getMyActions(
            Authentication authentication) {

        return ResponseEntity.ok(
                actionService.getMyactions(
                        authentication.getName()));
    }
    @PutMapping("/{actionId}/start")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public ResponseEntity<CorrectiveActionResponse>
    startAction(
            @PathVariable Long actionId) {

        return ResponseEntity.ok(
                actionService.startAction(
                        actionId));
    }
    @PutMapping("/{actionId}/complete")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public ResponseEntity<CorrectiveActionResponse>
    completeAction(
            @PathVariable Long actionId) {

        return ResponseEntity.ok(
                actionService.completeAction(
                        actionId));
    }
}