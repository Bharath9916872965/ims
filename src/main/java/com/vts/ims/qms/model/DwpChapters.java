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
@Table(name = "ims_qms_dwp_chapters")
public class DwpChapters {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ChapterId")
	private long chapterId;
	@Column(name = "ChapterParentId")
	private long chapterParentId;
	@Column(name = "SectionId")
	private long sectionId;
	@Column(name = "ChapterName")
	private String chapterName;
	@Column(name = "ChapterContent")
	private String chapterContent;
	@Column(name = "IsPagebreakAfter")
	private char isPagebreakAfter;
	@Column(name = "IsLandscape")
	private char isLandscape;
	@Column(name = "CreatedBy")
	private String createdBy;
	@Column(name = "CreatedDate")
	private LocalDateTime createdDate;
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Column(name = "ModifiedDate")
	private LocalDateTime modifiedDate;
	@Column(name = "IsActive")
	private int isActive;
	
}
