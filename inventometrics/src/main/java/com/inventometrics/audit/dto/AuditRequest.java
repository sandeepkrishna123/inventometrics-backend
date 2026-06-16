package com.inventometrics.audit.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class AuditRequest {
private String auditName;
private String description;
private LocalDate Duedate;
}
