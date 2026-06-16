package com.inventometrics.finding.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FindingResponse {

    private Long id;

    private String title;

    private String observation;

    private String recommendation;

    private String severity;

    private String auditCode;

    private LocalDateTime createdAt;
}