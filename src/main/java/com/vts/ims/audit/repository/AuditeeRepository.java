package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.audit.model.Auditee;

public interface AuditeeRepository extends JpaRepository<Auditee, Long>{

	List<Auditee> findAllByIsActive(int isActive) throws Exception;
	
	@Query("SELECT a.divisionId FROM Auditee a WHERE a.empId = :empId AND a.divisionId>0 AND a.isActive = 1")
    List<Long> findDivisionIdsByEmpId(@Param("empId") Long empId);
	
	@Query("SELECT a.groupId FROM Auditee a WHERE a.empId = :empId AND a.groupId>0 AND a.isActive = 1")
	List<Long> findDivisionGroupIdsByEmpId(@Param("empId") Long empId);

}
