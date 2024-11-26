package com.vts.ims.master.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name="ims_doc_template_attributes")
public class DocTemplateAttributes {

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
