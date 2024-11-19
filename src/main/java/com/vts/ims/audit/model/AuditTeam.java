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
@Table(name = "ims_audit_team")
public class AuditTeam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TeamId")
	private Long teamId;
	@Column(name = "IqaId")
	private Long iqaId;
	@Column(name = "TeamCode")
	private String teamCode;
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
