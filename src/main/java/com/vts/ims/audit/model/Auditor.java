package com.vts.ims.audit.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ims_audit_auditor")
public class Auditor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long AuditorId;
	private long EmpId;
	private String CreatedBy;
	private LocalDateTime CreatedDate;
	private String ModifiedBy;
	private LocalDateTime ModifiedDate;
	private int IsActive;
}
