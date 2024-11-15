package com.vts.ims.audit.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditeeDto {

	private Long auditeeId;
	private Long empId;
	private Long groupId;
	private Long divisionId;
	private Long projectId;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private int isActive;
}
