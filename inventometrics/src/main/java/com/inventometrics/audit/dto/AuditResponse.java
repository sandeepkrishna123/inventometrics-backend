package com.inventometrics.audit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class AuditResponse {
private Long id;
private String auditCode;

private String auditName;

private String description;

private String status;

private LocalDate dueDate;

private LocalDateTime createdAt;

private String assignedAuditor;

private String createdBy;
}
