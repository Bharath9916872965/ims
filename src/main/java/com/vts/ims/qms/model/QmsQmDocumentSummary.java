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
@Table(name="ims_qms_qm_document_summary")
public class QmsQmDocumentSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long DocumentSummaryId;
	private String AdditionalInfo;
	private String Abstract;
	private String Keywords;
	private String Distribution;
	private long RevisionRecordId;
	private String CreatedBy;
	private LocalDateTime CreatedDate;
	private String ModifiedBy;
	private LocalDateTime ModifiedDate;
	
	
}
