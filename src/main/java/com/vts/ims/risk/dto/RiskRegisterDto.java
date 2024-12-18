package com.vts.ims.risk.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data 
public class RiskRegisterDto {

	private Long riskRegisterId;
	private Long revisionRecordId;
	private String riskDescription;
	private int probability;
	private int technicalPerformance;
	private int time;
	private int cost;
	private Double average;
	private Double riskNo;
	private String DocType;
	private int IssueNo;
	private int RevisionNo;
	private String Description;
	private String StatusCode;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private int isActive;
}
