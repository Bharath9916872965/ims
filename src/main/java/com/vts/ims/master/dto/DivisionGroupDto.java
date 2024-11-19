package com.vts.ims.master.dto;

import lombok.Data;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
public class DivisionGroupDto {
	
	    private Long groupId;
	    private String labCode;
	    private String groupCode;
	    private String groupName;
	    private Long groupHeadId;
	    private Long tdId;
	    private int isActive;
	    
	    private String createdBy;
	    private LocalDateTime createdDate;
	    private String modifiedBy;
	    private LocalDateTime modifiedDate;
	    
	    //extra fields
	    private String tdName;
	    private String groupHeadName;
	    private String groupHeadDesig;
	    
	


  



}
