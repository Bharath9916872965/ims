package com.vts.ims.audit.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditorTeamDto {

	private Long teamId;
	private String teamCode;
	private String auditorName;
	private String iqaNo;
	private Long isLead;
	private Long iqaId;
	private Long auditorId;
	private Long teamMemberId;
	private Long teamLeadEmpId;
	private Long[] teamMemberEmpId;
	private String auditorThree;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private int isActive;
}
