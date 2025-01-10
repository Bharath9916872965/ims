package com.vts.ims.admin.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "ims_audit_patches")
public class AuditPatch {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AuditPatchesId")
	private Long auditPatchesId;
	
	@Column(name = "VersionNo")
	private String versionNo;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "PatchDate")
	private LocalDateTime patchDate;
	
	@Column(name = "CreatedDate")
	private LocalDateTime createdDate;
	
	@Lob
	@Column(name = "Attachment", columnDefinition = "LONGBLOB")
	private byte[] attachment;
	
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	
	@Column(name = "ModifiedDate")
	private LocalDateTime modifiedDate;
	
}
