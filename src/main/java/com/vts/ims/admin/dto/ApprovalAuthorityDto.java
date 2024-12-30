package com.vts.ims.admin.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApprovalAuthorityDto {

	private Long mRsId;
	private Long empId;
	private String mrType;
	private LocalDate mrFrom;
	private LocalDate mrTo;
	private String empName;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private int isActive;
	private String salutation;
    private String[] mrRepEmpId;
    private Long mrempId;
}
