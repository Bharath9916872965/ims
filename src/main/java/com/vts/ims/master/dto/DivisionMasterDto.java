package com.vts.ims.master.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DivisionMasterDto {

	    private Long divisionId;
	    private String labCode;
	    private String divisionCode;
	    private String divisionName;
	    private Long divisionHeadId;
	    private Long groupId;
		public int isActive;
	    public String createdBy;
	    public LocalDateTime createdDate;
	    public String modifiedBy;

	    
		//extra
	    public String divHeadName;
	    public String divHeadDesig;
	    public String divGroupName;

	

    
    //
	

}
