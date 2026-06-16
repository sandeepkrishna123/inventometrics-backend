package com.inventometrics.evidence.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventometrics.evidence.entity.Evidence;

public interface EvidenceRepo extends JpaRepository<Evidence, Long> {
List<Evidence>findByFindingId(Long findingId);
}
