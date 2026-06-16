package com.inventometrics.evidence.entity;

import com.inventometrics.finding.entity.AuditFinding;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evidence_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String filePath;

    private String fileType;

    @ManyToOne
    @JoinColumn(name = "finding_id")
    private AuditFinding finding;
}