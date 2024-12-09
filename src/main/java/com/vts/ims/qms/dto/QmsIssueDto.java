package com.vts.ims.qms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class QmsIssueDto {

	private String NewAmendVersion;
	private String AmendParticulars;
	private String DocType;
	private long GroupDivisionId;
	
}
