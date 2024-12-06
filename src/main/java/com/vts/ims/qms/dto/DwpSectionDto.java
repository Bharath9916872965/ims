package com.vts.ims.qms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class DwpSectionDto {

	private String docType;
	private String sectionName;
	private Long groupDivisionId;
	
}
