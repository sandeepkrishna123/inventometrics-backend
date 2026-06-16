package com.inventometrics.action.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CorrectiveActionResponse {

    private Long id;

    private String title;

    private String description;

    private String status;

    private String auditCode;

    private String assignedTo;

    private LocalDate dueDate;

    private LocalDateTime createdAt;
}