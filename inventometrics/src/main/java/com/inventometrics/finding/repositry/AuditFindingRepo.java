package com.inventometrics.finding.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventometrics.finding.entity.AuditFinding;

public interface AuditFindingRepo extends JpaRepository<AuditFinding,Long> {
List<AuditFinding>findByAuditId(Long auditId);
long countByCreatedBy_Email(String email);
}
