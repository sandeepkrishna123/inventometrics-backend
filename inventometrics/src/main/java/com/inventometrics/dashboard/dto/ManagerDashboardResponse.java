package com.inventometrics.dashboard.dto;

import lombok.Data;

@Data
public class ManagerDashboardResponse {
	 private Long plannedAudits;

	    private Long assignedAudits;

	    private Long submittedAudits;

	    private Long approvedAudits;

	    private Long rejectedAudits;
}
