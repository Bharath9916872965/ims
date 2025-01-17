package com.vts.ims.kpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.kpi.modal.ImsKpiUnit;

public interface KpiUnitRepository extends JpaRepository<ImsKpiUnit, Long>{
	
}
