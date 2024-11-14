package com.vts.ims.audit.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditorTeamDto {

	private Long teamId;
	private String teamCode;
	private String createdBy;
	private LocalDateTime createdDate;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
	private int isActive;
}
