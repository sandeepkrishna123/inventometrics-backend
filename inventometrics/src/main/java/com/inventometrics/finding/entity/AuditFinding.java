package com.inventometrics.finding.entity;

import java.time.LocalDateTime;

import com.inventometrics.audit.Entity.Audit;
import com.inventometrics.finding.enums.FindingSeverity;
import com.inventometrics.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audit_findings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditFinding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @Column(columnDefinition = "TEXT")
    private String recommendation;

    @Enumerated(EnumType.STRING)
    private FindingSeverity severity;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "audit_id")
    private Audit audit;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}