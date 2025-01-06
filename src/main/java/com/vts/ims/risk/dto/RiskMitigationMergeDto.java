package com.vts.ims.risk.dto;
import java.time.LocalDateTime;

import lombok.Data;

@Data 
public class RiskMitigationMergeDto {

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
	private int GroupDivisionId;
	private String MitigationApproach;
	private Long mitigationRiskRegisterId;
	private int mitigationProbability;
	private int  mitigationTp;
	private int mitigationTime;
	private int mitigationCost;
	private Double mitigationAverage;
	private Double mitigationRiskNo;
	
}
