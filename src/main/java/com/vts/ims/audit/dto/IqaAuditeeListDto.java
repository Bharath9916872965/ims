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
	private String divisionFullName;
	private String divisionHeadName;
	private Long divisionHeadId;
	private String groupName;
	private String groupHeadName;
	private Long   groupHeadId;
	private String groupFullName;
	private String projectName;
	private String projectDirectorName;
	private Long projectDirectorId;
	private String projectCode;
	private String projectShortName;
}
