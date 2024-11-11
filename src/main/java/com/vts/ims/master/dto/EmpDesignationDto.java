package com.vts.ims.master.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpDesignationDto {

    private Long desigId;
    private int desigSr;
    private String desigCode;
    private String designation;
    private Long desigLimit;
    private String desigCadre;

	
	
}
