package com.vts.ims.qms.repository;

import com.vts.ims.qms.model.DwpRevisionRecord;
import com.vts.ims.qms.model.QmsQmRevisionRecord;
import com.vts.ims.qms.model.QmsQspChapters;
import com.vts.ims.qms.model.QmsQspRevisionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QmsQspRevisionRecordRepo extends JpaRepository<QmsQspRevisionRecord, Long> {

//    @Query("SELECT a FROM QmsQspRevisionRecord a WHERE a.isActive = 1 AND a.docName = :docType ORDER BY a.revisionRecordId DESC")
//    List<QmsQspRevisionRecord> findAllByDocNameAndIsActive(@Param("docType") String docType);

}
