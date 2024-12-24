package com.vts.ims.qms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "ims_qms_qsp_document_summary")
public class QmsQspDocumentSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long DocumentSummaryId;
    private String AdditionalInfo;
    private String Abstract;
    private String Keywords;
    private String Distribution;
    @Column(name = "RevisionRecordId")
    private long revisionRecordId;
    private String CreatedBy;
    private LocalDateTime CreatedDate;
    private String ModifiedBy;
    private LocalDateTime ModifiedDate;

}
