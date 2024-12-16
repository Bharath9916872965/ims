package com.vts.ims.kpi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KpiTargetRatingrDto {
	                        
	private Long kpiTargetRatingId;
	private Long kpiId;
	private Long startValue; 
	private Long endValue; 
	private Long KpiRating; 

}
