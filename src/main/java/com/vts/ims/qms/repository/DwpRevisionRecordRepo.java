package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.qms.model.DwpRevisionRecord;

public interface DwpRevisionRecordRepo extends JpaRepository<DwpRevisionRecord, Long> {

	@Query("SELECT a FROM DwpRevisionRecord a WHERE a.isActive = 1 AND a.docType=:docType AND a.groupDivisionId=:groupDivisionId AND a.isExisting='N' ORDER BY a.revisionRecordId DESC")
	List<DwpRevisionRecord> findAllActiveDwpRecordsByDocType(@Param("docType") String docType, @Param("groupDivisionId") Long groupDivisionId);
	
	public DwpRevisionRecord findByRevisionRecordId(Long revisionRecordId);
	
	@Query("SELECT a FROM DwpRevisionRecord a WHERE a.isActive = 1 AND a.docType=:docType  ORDER BY a.revisionRecordId DESC")
	List<DwpRevisionRecord> findVersionRecordsByDocType(@Param("docType") String docType);

	@Query("SELECT a FROM DwpRevisionRecord a WHERE a.isActive = 1 ORDER BY a.revisionRecordId DESC")
	List<DwpRevisionRecord> findAllActiveDwpRecords();


	@Query(value="SELECT a.EmpId,a.StatusCode,a.TransactionDate,a.Remarks,b.Status,a.DGTransactionId FROM ims_qms_dwp_revision_transc a,ims_qms_doc_status b WHERE a.RevisionRecordId = :revisionRecordId AND a.StatusCode = b.StatusCode ORDER BY a.DGTransactionId",nativeQuery = true)
	public List<Object[]> getDwpRevisionTran(@Param("revisionRecordId")String revisionRecordId);

	@Query("SELECT a FROM DwpRevisionRecord a WHERE a.isActive = 1 AND a.docType=:docType AND a.groupDivisionId=:groupDivisionId ORDER BY a.revisionRecordId DESC")
	List<DwpRevisionRecord> findAllActiveDwpPrintRecordsByDocType(@Param("docType") String docType, @Param("groupDivisionId") Long groupDivisionId);


}
