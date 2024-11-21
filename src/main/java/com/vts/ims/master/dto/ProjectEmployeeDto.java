package com.vts.ims.master.dto;



import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectEmployeeDto {

    private Long projectEmployeeId;
    private Long empId;
    private Long projectId;
    private int isActive;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    
    //extra fields
    private String empName;
    private String empDesigCode;
    private String empDivCode;
    
    // List of employee IDs
    private List<Long> empIdsToAssign;  
    
}
