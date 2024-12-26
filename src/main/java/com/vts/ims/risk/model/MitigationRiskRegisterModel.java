package com.vts.ims.risk.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ims_mitigation_risk_register")
public class MitigationRiskRegisterModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MitigationRiskRegisterId")
	private Long mitigationRiskRegisterId;
	@Column(name="RiskRegisterId")
	private Long riskRegisterId;
	@Column(name="MitigationApproach")
	private String mitigationApproach;
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
	@Column(name = "RevisionNo")
	private int revisionNo;
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
