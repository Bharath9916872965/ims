package com.vts.ims.admin.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class FormDetailDto {

	private Long FormDetailId ;
	private Long FormModuleId ;
	private String FormName;
    private String FormUrl;
	private String FormDispName;
	private Long FormSerialNo ;
	private String FormColor;
	private int IsActive ;
    private String ModifiedBy;
    private LocalDateTime ModifiedDate;
	
}
