package com.vts.ims.audit.model;

import java.sql.Date;
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
@Table(name = "ims_audit_iqa")
public class Iqa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IqaId")
	private Long iqaId;
	@Column(name="IqaNo")
	private String iqaNo;
	@Column(name="FromDate")
	private Date fromDate;
	@Column(name="ToDate")
	private Date toDate;
	@Column(name="Scope")
	private String scope;
	@Column(name="Description")
	private String description;
	@Column(name="CreatedBy")
	private String createdBy;
	@Column(name="CreatedDate")
	private LocalDateTime createdDate;
	@Column(name="ModifiedBy")
	private String modifiedBy;
	@Column(name="ModifiedDate")
	private LocalDateTime modifiedDate;
	@Column(name="IsActive")
	private int isActive;
}
