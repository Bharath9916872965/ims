package com.vts.ims.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.audit.model.ImsAuditMailTrackInsights;

public interface ImsMailTrackInsightRepository extends JpaRepository<ImsAuditMailTrackInsights, Long>{
	
}

