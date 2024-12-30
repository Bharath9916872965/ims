package com.vts.ims.kpi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.kpi.modal.ImsKpiObjective;

import jakarta.transaction.Transactional;

@Transactional
public interface KpiObjectiveRepository extends JpaRepository<ImsKpiObjective, Long>{
	
	@Query(value="SELECT a.KpiObjectiveId, a.KpiId, a.KpiValue, a.KpiRating, a.IqaId, a.RevisionRecordId, a.ActEmpId, b.KpiObjectives,b.KpiMerics, b.KpiUnitId, c.KpiUnitName, b.KpiTarget, d.IqaNo,b.KpiNorms\r\n"
			+ "FROM ims_kpi_objective a,ims_kpi_objective_master b,ims_kpi_unit c,ims_audit_iqa d WHERE a.IsActive = 1 AND b.IsActive = 1 AND a.KpiId = b.KpiId AND b.KpiUnitId = c.KpiUnitId AND a.IqaId = d.IqaId",nativeQuery = true)
	public List<Object[]> getKpiObjRatingList();
	
	@Modifying
	@Query(value = "UPDATE ims_kpi_objective SET KpiValue = :KpiValue, KpiRating = :KpiRating, ModifiedBy = :ModifiedBy, ModifiedDate = :ModifiedDate WHERE KpiObjectiveId = :KpiObjectiveId",nativeQuery = true)
	public Integer updateKpiObjRatings(@Param("KpiValue")String kpiValue, @Param("KpiRating")Long kpiRating, @Param("ModifiedBy")String modifiedBy, @Param("ModifiedDate")LocalDateTime modifiedDate, @Param("KpiObjectiveId")Long kpiObjectiveId);
}
