package com.vts.ims.audit.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditTotalTeamMembersDto {
	              
	private Long teamId;
	private Long teamMemberId;
	private Long auditorId;
	private Long iqaId;
	private Long isLead;
	private Long empId;
	private String empName;
	private List<String> groups;  
	private List<String> divisions;  
	private List<String> projects;  
}
