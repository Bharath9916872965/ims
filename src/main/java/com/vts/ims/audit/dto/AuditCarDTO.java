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
    private String attachmentName;
    private String rootCause;
    private String correctiveActionTaken;
    private LocalDateTime completionDate;
    private String carRefNo;

}

