package com.inventometrics.evidence.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.inventometrics.evidence.dto.EvidenceResponse;
import com.inventometrics.evidence.entity.Evidence;
import com.inventometrics.evidence.repositry.EvidenceRepo;
import com.inventometrics.finding.entity.AuditFinding;
import com.inventometrics.finding.repositry.AuditFindingRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvidenceService {
private final EvidenceRepo evidenceRepo;
private final AuditFindingRepo findingRepo;
public Evidence uploadEvidence(
        Long findingId,
        MultipartFile file)
        throws IOException {

    AuditFinding finding =
            findingRepo.findById(findingId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Finding not found"));

    String uploadDir = "uploads";

    Path uploadPath =
            Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }

    String fileName =
            System.currentTimeMillis()
            + "_"
            + file.getOriginalFilename();

    Files.copy(
            file.getInputStream(),
            uploadPath.resolve(fileName),
            StandardCopyOption.REPLACE_EXISTING);

    Evidence evidence =
            new Evidence();

    evidence.setFileName(
            fileName);

    evidence.setFilePath(
            uploadPath.resolve(fileName)
                      .toString());

    evidence.setFileType(
            file.getContentType());

    evidence.setFinding(
            finding);

    return evidenceRepo.save(
            evidence);
}
public List<EvidenceResponse>
getEvidenceByFinding(Long findingId) {

    List<Evidence> evidenceList =
            evidenceRepo.findByFindingId(
                    findingId);

    return evidenceList.stream()
            .map(evidence -> {

                EvidenceResponse response =
                        new EvidenceResponse();

                response.setId(
                        evidence.getId());

                response.setFileName(
                        evidence.getFileName());

                response.setFilePath(
                        evidence.getFilePath());

                response.setFindingId(
                        evidence.getFinding().getId());

                return response;
            })
            .toList();
}
public Resource downloadEvidence(
        Long evidenceId) throws Exception {

    Evidence evidence =
            evidenceRepo.findById(
                    evidenceId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Evidence not found"));

    Path path =
            Paths.get(
                    evidence.getFilePath());

    return new UrlResource(
            path.toUri());
}
}
