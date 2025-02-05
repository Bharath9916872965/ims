package com.vts.ims.audit.dto;


import lombok.Data;

@Data
public class AuditTeamEmployeeDto {

	private Long teamId;
	private Long teamMemberIds;
	private String teamMembers;
	private Long isLead;
	private Long auditorId;
	private Long empId;
}
