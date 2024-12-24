package com.vts.ims.qms.dto;

import com.vts.ims.audit.dto.AuditTranDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QmsQmRevisionTransactionDto {

	private Long empId;
	private String statusCode;
	private String transactionDate;
	private String remarks;
	private String status;
	private String empName;
}
