package com.vts.ims.kpi.modal;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ims_kpi_objective_master")
public class ImsKpiObjectiveMaster {
	         
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "KpiId")
	private Long kpiId;
	
	@Column(name = "RevisionRecordId")
	private Long revisionRecordId;
	
	@Column(name = "KpiObjectives")
	private String kpiObjectives;
	
	@Column(name = "KpiMerics")
	private String kpiMerics;
	
	@Column(name = "KpiTarget")
	private Long kpiTarget;
	
	@Column(name = "KpiUnitId")
	private Long kpiUnitId;
	
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
	
	@OneToMany(mappedBy = "imsKpiObjectiveMaster",cascade = CascadeType.ALL)
	private List<ImsKpiTargerRating> imsKpiTargerRating;
}
