package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.qms.model.QmsQmRevisionRecord;

public interface QmsQmRevisionRecordRepo extends JpaRepository<QmsQmRevisionRecord, Long> {

	@Query("SELECT a FROM QmsQmRevisionRecord a WHERE a.isActive = 1 ORDER BY a.revisionRecordId DESC")
    public List<QmsQmRevisionRecord> findAllActiveQmRecords();

	public QmsQmRevisionRecord findByRevisionRecordId(Long revisionRecordId);

	@Query(value="SELECT a.EmpId,a.StatusCode,a.TransactionDate,a.Remarks,b.Status,a.QMTransactionId FROM ims_qms_qm_revision_transc a,ims_qms_doc_status b WHERE a.RevisionRecordId = :revisionRecordId AND a.StatusCode = b.StatusCode ORDER BY a.QMTransactionId",nativeQuery = true)
	public List<Object[]> getRevisionTran(@Param("revisionRecordId")String revisionRecordId);
	
}
