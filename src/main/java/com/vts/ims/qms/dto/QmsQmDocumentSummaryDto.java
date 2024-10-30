package com.vts.ims.qms.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class QmsQmDocumentSummaryDto {

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
