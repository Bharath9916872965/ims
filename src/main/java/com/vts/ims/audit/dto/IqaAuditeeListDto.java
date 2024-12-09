package com.vts.ims.audit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IqaAuditeeListDto {

	private Long auditeeId;
	private Long empId;
	private Long iqaAuditeeId;
	private Long iqaId;
	private Long groupId;
	private Long divisionId;
	private Long projectId;
	private String auditee;
	private String divisionName;
	private String groupName;
	private String projectName;
	private String projectCode;
	private String projectShortName;
}
