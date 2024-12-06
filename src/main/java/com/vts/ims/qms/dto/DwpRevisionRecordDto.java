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
	private String StatusCode;
	private String AbbreviationIdNotReq;
	private String CreatedBy;
	private LocalDateTime CreatedDate;
	private String ModifiedBy;
	private LocalDateTime ModifiedDate;
	private int IsActive;
	
}
