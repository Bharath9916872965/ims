package com.vts.ims.kpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.kpi.modal.ImsKpiUnit;

import jakarta.transaction.Transactional;

@Transactional
public interface KpiUnitRepository extends JpaRepository<ImsKpiUnit, Long>{
	
}
