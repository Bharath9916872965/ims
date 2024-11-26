package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.audit.model.AuditTransaction;

public interface AuditTransactionRepository extends JpaRepository<AuditTransaction, Long> {
	
	@Query(value = "SELECT a.EmpId,a.TransactionDate,a.Remarks,b.StatusName,a.ScheduleId FROM ims_audit_trans a,ims_audit_status b WHERE a.AuditStatus IN ('ASR','ARL') AND a.AuditStatus = b.AuditStatus",nativeQuery = true)
	public List<Object[]> getScheduleRemarks();

}
