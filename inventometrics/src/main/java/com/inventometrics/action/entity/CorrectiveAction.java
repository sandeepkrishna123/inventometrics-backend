package com.inventometrics.action.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.inventometrics.action.enums.CorrectiveActionStatus;
import com.inventometrics.audit.Entity.Audit;
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
@Table(name = "corrective_actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CorrectiveAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private CorrectiveActionStatus status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "audit_id")
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedTo;
}