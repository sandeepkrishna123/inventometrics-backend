package com.inventometrics.evidence.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inventometrics.evidence.dto.EvidenceResponse;
import com.inventometrics.evidence.entity.Evidence;
import com.inventometrics.evidence.service.EvidenceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/evidence")
@RequiredArgsConstructor
public class EvidenceController {

    private final EvidenceService evidenceService;

    @PostMapping("/upload/{findingId}")
    @PreAuthorize("hasRole('AUDITOR')")
    public ResponseEntity<Evidence>
    uploadEvidence(
            @PathVariable Long findingId,
            @RequestParam("file")
            MultipartFile file)
            throws IOException {

        return ResponseEntity.ok(
                evidenceService.uploadEvidence(
                        findingId,
                        file));
    }
    @GetMapping("/finding/{findingId}")
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public ResponseEntity<List<EvidenceResponse>>
    getEvidenceByFinding(
            @PathVariable Long findingId) {

        return ResponseEntity.ok(
                evidenceService.getEvidenceByFinding(
                        findingId));
    }
    @GetMapping("/download/{evidenceId}")
    @PreAuthorize(
            "hasAnyRole('ADMIN','MANAGER','AUDITOR')")
    public ResponseEntity<Resource>
    downloadEvidence(
            @PathVariable Long evidenceId)
            throws Exception {

        Resource resource =
                evidenceService
                        .downloadEvidence(
                                evidenceId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + resource.getFilename()
                                + "\"")
                .body(resource);
    }
    
}