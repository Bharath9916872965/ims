package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.audit.model.Auditee;

public interface AuditeeRepository extends JpaRepository<Auditee, Long>{

	public List<Auditee> findAllByIsActive(int isActive) throws Exception;
	
	@Query(value = "SELECT a.AuditeeId, a.EmpId, a.DivisionId, a.GroupId, a.ProjectId,b.IqaId, b.IqaAuditeeId FROM ims_audit_auditee a, ims_audit_iqa_auditee b WHERE a.AuditeeId = b.AuditeeId AND a.IsActive = 1",nativeQuery = true)
	public List<Object[]> getIqaAuditeeList();
	
	@Query("SELECT a.divisionId FROM Auditee a WHERE a.empId = :empId AND a.divisionId>0 AND a.isActive = 1")
    public List<Long> findDivisionIdsByEmpId(@Param("empId") Long empId);
	
	@Query("SELECT a.groupId FROM Auditee a WHERE a.empId = :empId AND a.groupId>0 AND a.isActive = 1")
	public List<Long> findDivisionGroupIdsByEmpId(@Param("empId") Long empId);

}
