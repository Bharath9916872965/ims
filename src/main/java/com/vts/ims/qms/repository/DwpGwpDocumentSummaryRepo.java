package com.vts.ims.qms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.qms.model.DwpGwpDocumentSummary;

public interface DwpGwpDocumentSummaryRepo extends JpaRepository<DwpGwpDocumentSummary, Long> {

	DwpGwpDocumentSummary findByRevisionRecordId(Long revisionRecordId);
	
}
