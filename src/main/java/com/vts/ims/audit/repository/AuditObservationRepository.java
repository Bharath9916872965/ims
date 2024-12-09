package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.audit.model.AuditObservation;

public interface AuditObservationRepository extends JpaRepository<AuditObservation, Long>{

	public List<AuditObservation> findByIsActive(int isActive);

}
