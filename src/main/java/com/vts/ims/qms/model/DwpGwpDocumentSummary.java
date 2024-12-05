package com.vts.ims.qms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ims_qms_dwp_gwp_document_summary")
public class DwpGwpDocumentSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long DocumentSummaryId;
	private String AdditionalInfo;
	private String Abstract;
	private String Keywords;
	private String Distribution;
	@Column(name = "RevisionRecordId")
	private long revisionRecordId;
	private String CreatedBy;
	private LocalDateTime CreatedDate;
	private String ModifiedBy;
	private LocalDateTime ModifiedDate;
	
}
