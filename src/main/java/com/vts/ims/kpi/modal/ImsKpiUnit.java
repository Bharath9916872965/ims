package com.vts.ims.kpi.modal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ims_kpi_unit")
public class ImsKpiUnit {
	         
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "KpiUnitId")
	private Long kpiUnitId;
	
	@Column(name = "KpiUnitName")
	private String kpiUnitName;
}
