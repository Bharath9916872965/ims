package com.vts.ims.master.dto;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class DocTemplateAttributesDto {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long AttributeId;
	private int HeaderFontSize;
	private int SubHeaderFontsize;
	private int SuperHeaderFontsize;
	private int ParaFontSize;
	private String FontFamily;
	private String RestrictionOnUse;
	private String CreatedBy;
	private LocalDateTime CreatedDate;
	private String ModifiedBy;
	private LocalDateTime ModifiedDate;
	private int IsActive;
	
}
