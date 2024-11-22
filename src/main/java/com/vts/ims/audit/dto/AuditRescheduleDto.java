package com.vts.ims.audit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditRescheduleDto {
	         
	private AuditScheduleDto auditScheduleDto;
	private AuditScheduleListDto auditScheduleListDto;
}
