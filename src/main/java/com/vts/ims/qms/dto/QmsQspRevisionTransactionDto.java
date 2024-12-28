package com.vts.ims.qms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QmsQspRevisionTransactionDto {

	private Long empId;
	private String statusCode;
	private String transactionDate;
	private String remarks;
	private String status;
	private String empName;
}
