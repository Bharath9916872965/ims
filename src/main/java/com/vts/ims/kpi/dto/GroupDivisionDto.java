package com.vts.ims.kpi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDivisionDto {
	private Long groupDivisionMainId;  
	private Long groupDivisionId;  
	private String groupDivisionType;  
	private String groupDivisionCode; 

}
