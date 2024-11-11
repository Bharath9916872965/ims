package com.vts.ims.master.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class EmployeeDto {
    private Long empId;
    private String labCode;
    private String title;
    private String salutation;
    private Long srNo;
    private String empNo;
    private String empName;
    private Long desigId;
    private String extNo;
    private Long mobileNo; 
    private Long divisionId;
    private String email;
    private String dronaEmail;
    private String internalEmail;
    private String internetEmail; 
    private Long superiorOfficer;
    private int isActive;
    private String photo;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    
    
    // Extra fields
    private String empDesigName;
    private String empDesigCode;
    private String empDivCode;
}
