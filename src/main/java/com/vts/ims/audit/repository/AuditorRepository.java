package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.audit.model.Auditor;

public interface AuditorRepository extends JpaRepository<Auditor, Long> {
	
	@Query(value = "SELECT EmpId,AuditorId FROM ims_audit_auditor WHERE IsActive='0'",nativeQuery = true)
	public List<Object[]> isActiveAuditorList(int IsActive);

	 List<Auditor> findAllByIsActive(int isActive);

	
	

}
