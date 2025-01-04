package com.vts.ims.kpi.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KpiObjListDto {
           
	private List<KpiObjDto> ratingList;
	private Long iqaId;
	private Long groupDivisionId;
	private String kpiType;

}
