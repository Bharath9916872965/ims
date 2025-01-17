package com.vts.ims.kpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.kpi.modal.ImsKpiObjective;

public interface KpiObjectiveRepository extends JpaRepository<ImsKpiObjective, Long>{
	
	@Query(value="SELECT a.KpiObjectiveId, a.KpiId, a.KpiValue, a.KpiRating, a.IqaId, a.GroupDivisionId, a.ActEmpId,b.KpiObjectives,b.KpiMerics, b.KpiUnitId, c.KpiUnitName, b.KpiTarget, d.IqaNo,b.KpiNorms,a.KpiType\r\n"
			+ "FROM ims_kpi_objective a,ims_kpi_objective_master b,ims_kpi_unit c,ims_audit_iqa d WHERE a.IsActive = 1 AND b.IsActive = 1 AND a.KpiId = b.KpiId AND b.KpiUnitId = c.KpiUnitId AND a.IqaId = d.IqaId",nativeQuery = true)
	public List<Object[]> getKpiObjRatingList();
	
}
