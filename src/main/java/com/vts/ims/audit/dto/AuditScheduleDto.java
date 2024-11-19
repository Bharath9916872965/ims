package com.vts.ims.audit.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditScheduleDto {
	         
	private Long scheduleId;
	private LocalDateTime scheduleDate;
	private Long auditeeId;
	private Long teamId;
	private String remarks;
	private Integer revision;
}
