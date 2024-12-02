package com.vts.ims.audit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditTranDto {
	                
	private Long empId;
	private String auditStatus;
	private String transactionDate;
	private String remarks;
	private String statusName;
	private String empName;
}
