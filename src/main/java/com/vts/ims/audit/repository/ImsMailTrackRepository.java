package com.vts.ims.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.audit.model.ImsAuditMailTrack;

public interface ImsMailTrackRepository extends JpaRepository<ImsAuditMailTrack, Long>{
	
}

