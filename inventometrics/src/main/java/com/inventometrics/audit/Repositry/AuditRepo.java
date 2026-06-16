package com.inventometrics.audit.Repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventometrics.audit.Entity.Audit;
import com.inventometrics.audit.enums.AuditStatus;

public interface AuditRepo extends JpaRepository<Audit, Long> {
List<Audit>findByAssignedAuditorEmail(String email);
List<Audit>findByStatus(AuditStatus status);
long countByStatus(AuditStatus status);
long countByAssignedAuditor_EmailAndStatus(
        String email,
        AuditStatus status);
List<Audit>findByAuditNameContainingIgnoreCase(String auditName);
}
