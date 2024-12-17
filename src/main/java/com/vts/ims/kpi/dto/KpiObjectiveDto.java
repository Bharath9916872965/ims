package com.vts.ims.kpi.dto;

import java.util.List;

import lombok.Data;

@Data
public class KpiObjectiveDto {

	private Long kpiId;
	private String objective;
	private String metrics; 
	private String target; 
	private Long kpiUnitId; 
	private String revisionRecordId; 
	private List<RatingDto> ratings; 

}
