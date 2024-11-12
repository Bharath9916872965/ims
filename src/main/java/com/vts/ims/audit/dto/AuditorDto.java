package com.vts.ims.audit.dto;

import lombok.Data;

@Data
//@Builder
public class AuditorDto {

	private Long EmpId;
	private String EmpName;
	private String Designation;
	private String DivisionName;
	private String[] EmpIds;
	
}
