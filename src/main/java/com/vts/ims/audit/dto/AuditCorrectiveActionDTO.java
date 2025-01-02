package com.vts.ims.audit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditCorrectiveActionDTO {
	                                 
    private Long correctiveActionId;
    private Long auditCheckListId;
    private Long iqaId;
    private String iqaNo; 
    private String divisionGroupCode; 
    private String carRefNo;
    private String carDescription;
    private String actionPlan;
    private Long responsibility;
    private String targetDate;
    private Long scheduleId;
    private Long auditeeId;
    private Long auditeeEmpId;
    private String carAttachment;
    private String rootCause;
    private String correctiveActionTaken;
    private String carCompletionDate;
    private String executiveName;
    private String carDate;
    private String auditStatus;
    private String auditStatusName;
    private String message;
    private Long headId;

}

