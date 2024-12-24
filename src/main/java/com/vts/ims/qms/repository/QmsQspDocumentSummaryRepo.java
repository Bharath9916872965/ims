package com.vts.ims.qms.repository;

import com.vts.ims.qms.model.QmsQspDocumentSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QmsQspDocumentSummaryRepo extends JpaRepository<QmsQspDocumentSummary, Long> {
    QmsQspDocumentSummary findByRevisionRecordId(long revisionRecordId);
}
