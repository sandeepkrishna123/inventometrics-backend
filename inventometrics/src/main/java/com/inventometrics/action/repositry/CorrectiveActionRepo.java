package com.inventometrics.action.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventometrics.action.entity.CorrectiveAction;

public interface CorrectiveActionRepo
        extends JpaRepository<CorrectiveAction, Long> {

    List<CorrectiveAction> findByAssignedToId(Long userId);

    List<CorrectiveAction> findByAuditId(Long auditId);
    List<CorrectiveAction> findByAssignedToEmail(String email);
    long countByAssignedToEmail(
            String email);
}