package com.vts.ims.risk.model;

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
@Table(name = "ims_risk_register")
public class RiskRegisterModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RiskRegisterId")
	private Long riskRegisterId;
	@Column(name="RevisionRecordId")
	private Long revisionRecordId;
	@Column(name="RiskDescription")
	private String riskDescription;
	@Column(name="Probability")
	private int probability;
	@Column(name="TechnicalPerformance")
	private int technicalPerformance;
	@Column(name="Time")
	private int time;
	@Column(name="Cost")
	private int cost;
	@Column(name="Average")
	private Double average;
	@Column(name="RiskNo")
	private Double riskNo;
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
