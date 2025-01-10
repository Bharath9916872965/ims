package com.vts.ims.audit.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuditPatchDto {

	private Long auditPatchesId;
	private String versionNo;
	private String description;
	private LocalDateTime patchDate;
	private LocalDateTime createdDate;
	private byte[] attachment;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
}
