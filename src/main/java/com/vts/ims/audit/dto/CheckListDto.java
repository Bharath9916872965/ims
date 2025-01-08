package com.vts.ims.audit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckListDto {
	                          
    private Long auditCheckListId;
    private Long scheduleId;
    private Long iqaId;
    private Long mocId;
    private Long auditObsId;
    private String auditorRemarks;
    private String auditeeRemarks;
    private String clauseNo;
    private String sectionNo;
    private Long mocParentId;
    private String isForCheckList;
    private String description;
    private String scheduleStatus;
    private String carRefNo;
    private Long ncCount;
    private String obsName;
    private String attachmentName;
}

