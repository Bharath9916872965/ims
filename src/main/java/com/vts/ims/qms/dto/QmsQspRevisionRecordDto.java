package com.vts.ims.qms.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Builder
public class QmsQspRevisionRecordDto {

    private long revisionRecordId;
    private String docName;
    private String docFileName;
    private String docFilepath;
    private String description;
    private int issueNo;
    private int revisionNo;
    private LocalDate dateOfRevision;
    private String statusCode;
    private String StatusCodeNext;
	private String Status;
    private String abbreviationIdNotReq;
    private Long InitiatedBy;
	private Long ReviewedBy; 
	private Long ApprovedBy;
	private String Action;
	private String Remarks;
	private Long EmpId;
	private String InitiatedByEmployee;
	private String ReviewedByEmployee;
	private String ApprovedByEmployee;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
    private int isActive;
}
