package com.vts.ims.qms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.qms.model.QmsQmDocumentSummary;

public interface QmsQmDocumentSummaryRepo extends JpaRepository<QmsQmDocumentSummary, Long> {
	
    QmsQmDocumentSummary findByRevisionRecordId(Long revisionRecordId);
    
}
