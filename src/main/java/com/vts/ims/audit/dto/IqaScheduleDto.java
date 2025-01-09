package com.vts.ims.audit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IqaScheduleDto {
	        
	private Long iqaId;
	private String iqaNo;
	private String fromDate;
	private String toDate;
	private Long auditees;
	private Long auditeeSub;
	private Long auditorSub;
	private Long auditeeAcp;
	
}
