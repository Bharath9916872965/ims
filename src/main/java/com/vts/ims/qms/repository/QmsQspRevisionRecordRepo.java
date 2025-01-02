package com.vts.ims.qms.repository;

import com.vts.ims.qms.model.QmsQspRevisionRecord;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface QmsQspRevisionRecordRepo extends JpaRepository<QmsQspRevisionRecord, Long> {

	public QmsQspRevisionRecord findByRevisionRecordId(Long revisionRecordId);
	
	@Query("SELECT a FROM QmsQspRevisionRecord a WHERE a.isActive = 1 ORDER BY a.revisionRecordId DESC")
	public List<QmsQspRevisionRecord> findAllActiveQspRecords();

	@Query(value="SELECT a.EmpId,a.StatusCode,a.TransactionDate,a.Remarks,b.Status,a.QspTransactionId FROM ims_qms_qsp_revision_transc a,ims_qms_doc_status b WHERE a.RevisionRecordId = :revisionRecordId AND a.StatusCode = b.StatusCode ORDER BY a.QspTransactionId",nativeQuery = true)
	public List<Object[]> getQspRevisionTran(@Param("revisionRecordId")String revisionRecordId);
	
	
//    @Query("SELECT a FROM QmsQspRevisionRecord a WHERE a.isActive = 1 AND a.docName = :docType ORDER BY a.revisionRecordId DESC")
//    List<QmsQspRevisionRecord> findAllByDocNameAndIsActive(@Param("docType") String docType);

}
