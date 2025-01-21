package com.vts.ims.audit.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

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
	private MultipartFile file;
	private byte[] attachment;
	private String modifiedBy;
	private LocalDateTime modifiedDate;
}
