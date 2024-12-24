package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.qms.model.DwpRevisionRecord;

public interface DwpRevisionRecordRepo extends JpaRepository<DwpRevisionRecord, Long> {

	@Query("SELECT a FROM DwpRevisionRecord a WHERE a.isActive = 1 AND a.docType=:docType AND a.groupDivisionId=:groupDivisionId ORDER BY a.revisionRecordId DESC")
//    List<DwpRevisionRecord> findAllActiveDwpRecords();
	List<DwpRevisionRecord> findAllActiveDwpRecordsByDocType(@Param("docType") String docType, @Param("groupDivisionId") Long groupDivisionId);
	
	
	@Query("SELECT a FROM DwpRevisionRecord a WHERE a.isActive = 1 AND a.docType=:docType  ORDER BY a.revisionRecordId DESC")
	List<DwpRevisionRecord> findVersionRecordsByDocType(@Param("docType") String docType);
	
	
}
