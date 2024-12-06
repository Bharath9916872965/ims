package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.vts.ims.audit.model.IqaAuditee;

public interface IqaAuditeeRepository extends JpaRepository<IqaAuditee, Long>{

	@Query(value = "SELECT a.EmpId,b.AuditeeId,b.IqaId,a.DivisionId,a.GroupId,a.ProjectId FROM ims_audit_auditee a, ims_audit_iqa_auditee b WHERE a.AuditeeId=b.AuditeeId AND b.IqaId=:iqaId AND a.IsActive=1",nativeQuery = true)
	List<Object[]> iqaAuditeeList(@Param("iqaId")Long iqaId);

	@Modifying   
	@Transactional
	@Query(value = "DELETE FROM ims_audit_iqa_auditee im WHERE im.IqaId =:iqaId",nativeQuery = true)
	public int deleteByIqaId(@RequestParam("iqaId") Long iqaId);
	

}
