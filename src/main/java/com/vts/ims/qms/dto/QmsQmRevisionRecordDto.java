package com.vts.ims.qms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QmsQmRevisionRecordDto {

	private long RevisionRecordId;
	private String DocFileName;
	private String DocFilepath;
	private String Description;
	private int IssueNo;
	private int RevisionNo;
	private LocalDate DateOfRevision;
	private String StatusCode;
	private String StatusCodeNext;
	private String Status;
	private String AbbreviationIdNotReq;
	private Long InitiatedBy;
	private Long ReviewedBy; 
	private Long ApprovedBy;
	private String CreatedBy;
	private String Action;
	private String Remarks;
	private Long EmpId;
	private String InitiatedByEmployee;
	private String ReviewedByEmployee;
	private String ApprovedByEmployee;
	private LocalDateTime CreatedDate;
	private String ModifiedBy;
	private LocalDateTime ModifiedDate;
	private int IsActive;
	
}
