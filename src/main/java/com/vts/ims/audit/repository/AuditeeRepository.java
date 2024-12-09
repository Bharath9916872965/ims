package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.audit.model.Auditee;

public interface AuditeeRepository extends JpaRepository<Auditee, Long>{

	List<Auditee> findAllByIsActive(int isActive) throws Exception;
	
	@Query(value = "SELECT a.AuditeeId, a.EmpId, a.DivisionId, a.GroupId, a.ProjectId,b.IqaId, b.IqaAuditeeId FROM ims_audit_auditee a, ims_audit_iqa_auditee b WHERE a.AuditeeId = b.AuditeeId AND a.IsActive = 1",nativeQuery = true)
	public List<Object[]> getIqaAuditeeList();

}
