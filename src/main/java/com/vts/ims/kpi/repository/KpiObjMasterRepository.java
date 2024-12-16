package com.vts.ims.kpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.kpi.modal.ImsKpiObjectiveMaster;

//@Transactional
public interface KpiObjMasterRepository extends JpaRepository<ImsKpiObjectiveMaster, Long>{
	
	@Query(value="SELECT  a.KpiId, KpiObjectives, a.KpiMerics, a.KpiTarget,a.KpiUnitId,b.KpiUnitName FROM ims_kpi_objective_master a,ims_kpi_unit b WHERE a.IsActive = 1 AND a.KpiUnitId = b.KpiUnitId",nativeQuery = true)
	public List<Object[]> getKpiMasterList();
}
