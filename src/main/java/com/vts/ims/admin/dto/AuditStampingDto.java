package com.vts.ims.admin.dto;



import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuditStampingDto {

	private Long auditStampingId;
	private Long loginId;
	private String username;
	private String loginDate;
	private String logOutDateTime;
	private String ipAddress;
	private String macAddress;
	private String logOutType;
	private String loginDateTime;

	
	private String empId;
	private Date fromDate;
	private Date toDate;
	
	private String loginTime;
	private String logoutTypeDisp;
   
   
   
}