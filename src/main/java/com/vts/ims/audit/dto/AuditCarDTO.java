package com.vts.ims.audit.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditCarDTO {
	                                 
    private Long correctiveActionId;
    private String action;
    private Long employee;
    private LocalDateTime targetDate;

}

