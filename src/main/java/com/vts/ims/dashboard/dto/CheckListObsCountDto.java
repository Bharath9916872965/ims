package com.vts.ims.dashboard.dto;



import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckListObsCountDto {



    private Long iqaId;
    private Long totalCountNC;
    private Long totalCountOBS;
    private Long totalCountOFI;
    
    
    private Long countOfNC;
    private Long countOfOBS;
    private Long countOfOFI;
    private Long scheduleId;
    private Long auditeeId;
    private Long empId;
    private Long divisionId;
    private Long groupId;
    private Long projectId;
    private String divisionName;
    private String groupName;
    private String projectName;
    private String auditeeEmpName;
    
    

	
}
