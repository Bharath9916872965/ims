package com.vts.ims.qms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Table(name = "ims_qms_qsp_revision_record")
@Data
@Entity
public class QmsQspRevisionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RevisionRecordId")
    private long revisionRecordId;
    @Column(name = "DocName")
    private String docName;
    @Column(name = "DocFileName")
    private String docFileName;
    @Column(name = "DocFilepath")
    private String docFilepath;
    @Column(name = "Description")
    private String description;
    @Column(name = "IssueNo")
    private int issueNo;
    @Column(name = "RevisionNo")
    private int revisionNo;
    @Column(name = "DateOfRevision")
    private LocalDate dateOfRevision;
    @Column(name = "StatusCode")
    private String statusCode;
    @Column(name = "StatusCodeNext")
    private String statusCodeNext;
    @Column(name = "AbbreviationIdNotReq")
    private String abbreviationIdNotReq;
    @Column(name = "InitiatedBy")
	private Long initiatedBy;
	@Column(name = "ReviewedBy")
	private Long reviewedBy;
	@Column(name = "ApprovedBy")
	private Long approvedBy;
    @Column(name = "CreatedBy")
    private String createdBy;
    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;
    @Column(name = "ModifiedBy")
    private String modifiedBy;
    @Column(name = "ModifiedDate")
    private LocalDateTime modifiedDate;
    @Column(name = "IsActive")
    private int isActive;

}
