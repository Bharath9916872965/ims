package com.vts.ims.audit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditScheduleRemarksDto {
	                        
	private Long empId;
	private String empName;
	private String transactionDate;
	private String remarks;
	private String StatusName;
	private Long scheduleId;
}
