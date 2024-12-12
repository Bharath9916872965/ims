package com.vts.ims.admin.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormModuleDto {

	private Long FormModuleId ;
	private String FormModuleName ;
	private String ModuleUrl ;
	private String ModuleIcon ;
	private Long SerialNo ;
	private int IsActive ;
	
}
