package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.audit.model.AuditClosure;

public interface AuditClosureRepository extends JpaRepository<AuditClosure, Long>{
	
	
	public List<AuditClosure> findByIsActive(int isActive);
	
}
