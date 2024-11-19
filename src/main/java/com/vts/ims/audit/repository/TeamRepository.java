package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.audit.model.AuditTeam;

public interface TeamRepository extends JpaRepository<AuditTeam, Long> {
	

	 List<AuditTeam> findAllByIsActive(int isActive);

	
	

}
