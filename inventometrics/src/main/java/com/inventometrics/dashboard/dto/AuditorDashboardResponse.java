package com.inventometrics.dashboard.dto;

import lombok.Data;

@Data
public class AuditorDashboardResponse {
private Long assignedaudits;
private Long submittedAudits;

private Long findings;

private Long actions;
}
