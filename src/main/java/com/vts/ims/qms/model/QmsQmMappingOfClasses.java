package com.vts.ims.qms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="ims_qms_qm_mapping_classes")
public class QmsQmMappingOfClasses {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long MocId;
	private String  SectionNo;
	private String ClauseNo;
	private String Description;
//	private String DocumentType;
	private long DocumentId;
	private String CreatedBy;
	private LocalDateTime CreatedDate;
	
}


