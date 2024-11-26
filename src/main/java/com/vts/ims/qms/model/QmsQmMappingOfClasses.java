package com.vts.ims.qms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
	
	@Column(name = "MocId")
	private long mocId;
	@Column(name = "SectionNo")
	private String  sectionNo;
	@Column(name = "ClauseNo")
	private String clauseNo;
	@Column(name = "Description")
	private String description;
	@Column(name = "RevisionRecordId")
	private long revisionRecordId;
	@Column(name = "CreatedBy")
	private String createdBy;
	@Column(name = "CreatedDate")
	private LocalDateTime createdDate;
	
}


