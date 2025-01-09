package com.vts.ims.qms.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QmsDocTypeDto {

	private String docType;
	private Long groupDivisionId;
	private Long revisionRecordId;
	private String statusCode;
	
}
