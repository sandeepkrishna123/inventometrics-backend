package com.inventometrics.finding.dto;

import com.inventometrics.finding.enums.FindingSeverity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FindingRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String observation;

    @NotBlank
    private String recommendation;

    @NotNull
    private FindingSeverity severity;

    @NotNull
    private Long auditId;
}