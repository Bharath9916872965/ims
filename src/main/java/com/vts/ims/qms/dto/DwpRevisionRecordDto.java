package com.vts.ims.qms.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DwpRevisionRecordDto {

	private long RevisionRecordId;
	private String DocType;
	private long GroupDivisionId;
	private DivisionMasterDto divisionMasterDto;
	private DivisionGroupDto divisionGroupDto;
	private String DocFileName;
	private String DocFilepath;
	private String Description;
	private int IssueNo;
	private int RevisionNo;
	private LocalDate DateOfRevision;
	private String Status;
	private String StatusCode;
	private String StatusCodeNext;
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
