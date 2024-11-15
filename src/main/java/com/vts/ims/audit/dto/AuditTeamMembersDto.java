package com.vts.ims.audit.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditTeamMembersDto {

	private Long teamMemberId;
	private Long teamId;
	private Long auditorId;
	private Long iqaId;
	private Long iIsLead;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private int isActive;
}
