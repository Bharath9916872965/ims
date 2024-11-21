package com.vts.ims.audit.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IqaDto {

	private Long iqaId;
	private String iqaNo;
	private Date fromDate;
	private Date toDate;
	private String scope;
	private String description;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private int isActive;
	
}
