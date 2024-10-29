package com.vts.ims.qms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="ims_qms_abbreviation")
public class QmsAbbreviations {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long AbbreviationId;
	private String Abbreviation;
	private String Meaning;
//	private String DocumentType;
	private long RevisionRecordId;
}
