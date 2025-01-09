package com.vts.ims.qms.dto;

import lombok.Data;

@Data
public class QmsQmMappingRevDto {

	private Long mocRevId;
	private Long revisionRecordId;
	private Long mocId;
	private String clauseNo;
	private String sectionNo;
	private Long mocParentId;
	private String isForCheckList;
	private String description;
	private Long documentId;
	private Integer isActive;
	private String attachmentName;
}
