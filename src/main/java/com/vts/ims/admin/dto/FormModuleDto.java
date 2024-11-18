package com.vts.ims.admin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class FormModuleDto {

	private Long FormModuleId ;
	private String FormModuleName ;
	private String ModuleUrl ;
	private String ModuleIcon ;
	private Long SerialNo ;
	private int IsActive ;
	
}
