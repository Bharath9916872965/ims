package com.vts.ims.kpi.modal;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ims_kpi_target_rating")
public class ImsKpiTargerRating {
	         
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "KpiTargetRatingId")
	private Long kpiTargetRatingId;
	
	@Column(name = "StartValue")
	private Long startValue;
	
	@Column(name = "EndValue")
	private Long endValue;
	
	@Column(name = "KpiRating")
	private Long kpiRating;
	
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
	
	@JoinColumn(name = "KpiId")
	@ManyToOne
	private ImsKpiObjectiveMaster imsKpiObjectiveMaster;
}
