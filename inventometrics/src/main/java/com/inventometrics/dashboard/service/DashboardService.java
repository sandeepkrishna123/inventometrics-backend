package com.inventometrics.dashboard.service;

import org.springframework.stereotype.Service;

import com.inventometrics.action.repositry.CorrectiveActionRepo;
import com.inventometrics.audit.Repositry.AuditRepo;
import com.inventometrics.audit.enums.AuditStatus;
import com.inventometrics.dashboard.dto.AdminDashboardResponse;
import com.inventometrics.dashboard.dto.AuditorDashboardResponse;
import com.inventometrics.dashboard.dto.ManagerDashboardResponse;
import com.inventometrics.finding.repositry.AuditFindingRepo;
import com.inventometrics.user.repositry.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {
private final UserRepo userRepo;
private final AuditRepo auditRepo;
private final AuditFindingRepo findingRepo;
private final CorrectiveActionRepo actionRepo;
public AdminDashboardResponse getAdminDashboard() {
	AdminDashboardResponse response =
            new AdminDashboardResponse();

    response.setTotalusers(
            userRepo.count());

    response.setTotalaudits(
            auditRepo.count());

    response.setTotalfindings(
            findingRepo.count());

    response.setTotalActions(
            actionRepo.count());

    return response;
}
public ManagerDashboardResponse getManagerDashboard() {
	ManagerDashboardResponse response=new ManagerDashboardResponse();
	response.setApprovedAudits(
	        auditRepo.countByStatus(
	                AuditStatus.APPROVED));

	response.setAssignedAudits(
	        auditRepo.countByStatus(
	                AuditStatus.ASSIGNED));

	response.setSubmittedAudits(
	        auditRepo.countByStatus(
	                AuditStatus.SUBMITTED));

	response.setPlannedAudits(
	        auditRepo.countByStatus(
	                AuditStatus.PLANNED));

	response.setRejectedAudits(
	        auditRepo.countByStatus(
	                AuditStatus.REJECTED));
	return response;
}
public AuditorDashboardResponse getAuditorDashboard(String email) {
	AuditorDashboardResponse response=new AuditorDashboardResponse();
	 response.setAssignedaudits(
	            auditRepo.countByAssignedAuditor_EmailAndStatus(
	                    email,
	                    AuditStatus.ASSIGNED));

	    response.setSubmittedAudits(
	            auditRepo.countByAssignedAuditor_EmailAndStatus(
	                    email,
	                    AuditStatus.SUBMITTED));

	    response.setFindings(findingRepo.countByCreatedBy_Email(email));

	    response.setActions(
	            actionRepo.countByAssignedToEmail(
	                    email));

	    return response;
	
}
}
