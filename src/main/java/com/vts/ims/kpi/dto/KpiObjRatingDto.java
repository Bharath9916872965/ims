package com.vts.ims.kpi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KpiObjRatingDto {
	                          
	private Long kpiObjectiveId;
	private Long kpiId;
	private String kpiValue; 
	private Long kpiRating; 
	private Long iqaId; 
	private String iqaNo; 
	private Long actEmpId; 
	private Long revisionRecordId; 
	private String kpiObjectives; 
	private String kpiMerics; 
	private Long kpiUnitId; 
	private String kpiUnitName; 
	private String kpiTarget; 

}
