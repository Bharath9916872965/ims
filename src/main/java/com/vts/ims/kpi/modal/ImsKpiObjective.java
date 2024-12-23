package com.vts.ims.kpi.modal;


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
@Table(name="ims_kpi_objective")
public class ImsKpiObjective {
	         
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "KpiObjectiveId")
	private Long kpiObjectiveId;
	
	@Column(name = "KpiId")
	private Long kpiId;
	
	@Column(name = "KpiValue")
	private Long kpiValue;
	  
	@Column(name = "KpiRating")
	private Long kpiRating;
	
	@Column(name = "IqaId")
	private Long iqaId;
	
	@Column(name = "RevisionRecordId")
	private Long revisionRecordId;
	
	@Column(name = "ActEmpId")
	private Long actEmpId;
	
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
