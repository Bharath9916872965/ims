package com.vts.ims.kpi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KpiObjDto {
           
	private Long kpiObjectiveId;
	private Long kpiId;
	private String kpiValue; 
	private Long kpiRating; 

}
