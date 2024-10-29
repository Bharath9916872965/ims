package com.vts.ims.qms.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QmsQmChaptersDto {

	private long ChapterId;
	private long ChapterParentId;
	private long SectionId;
	private String ChapterName;
	private String ChapterContent;
	private String CreatedBy;
	private LocalDateTime CreatedDate;
	private String ModifiedBy;
	private LocalDateTime ModifiedDate;
	private int IsActive;
	
}
