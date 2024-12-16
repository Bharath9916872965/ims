package com.vts.ims.kpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.kpi.modal.ImsKpiTargerRating;

import jakarta.transaction.Transactional;

@Transactional
public interface KpiTargetRatingRepository extends JpaRepository<ImsKpiTargerRating, Long>{
	
	@Query(value="SELECT a.KpiTargetRatingId, a.KpiId,a.StartValue, a.EndValue, a.KpiRating FROM ims_kpi_target_rating a WHERE a.IsActive = 1",nativeQuery = true)
	public List<Object[]> getTargetRatingList();
	
	@Modifying
	@Query(value = "DELETE FROM ims_kpi_target_rating WHERE KpiId = :KpiId",nativeQuery = true)
	public Integer deleteRatings(@Param("KpiId")Long kpiId);
}
