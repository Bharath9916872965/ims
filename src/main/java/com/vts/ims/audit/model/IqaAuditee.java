package com.vts.ims.audit.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ims_audit_iqa_auditee")
public class IqaAuditee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IqaAuditeeId")
	private Long iqaAuditeeId;
	@Column(name = "IqaId")
	private Long iqaId;
	@Column(name = "AuditeeId")
	private Long auditeeId;
	@Column(name = "CreatedBy")
	private String createdBy;
	@Column(name = "CreatedDate")
	private LocalDateTime createdDate;
	
}
