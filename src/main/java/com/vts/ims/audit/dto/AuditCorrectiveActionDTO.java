package com.vts.ims.audit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditCorrectiveActionDTO {
	                                 
    private Long correctiveActionId;
    private Long auditCheckListId;
    private Long iqaId;
    private String carRefNo;
    private String carDescription;
    private String actionPlan;
    private Long responsibility;
    private String targetDate;
    private Long scheduleId;
    private Long auditeeId;

}

