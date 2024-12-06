package com.vts.ims.audit.dto;

import lombok.Data;

@Data
public class IqaAuditeeDto {

	private Long IqaAuditeeId;
	private Long IqaId;
	private Long AuditeeId;
	private Long CreatedBy;
	private Long CreatedDate;
	private String auditee;
	private String divisionName;
	private String groupName;
	private String projectName;
	private String projectCode;
	private String[] auditeeIds;
}
