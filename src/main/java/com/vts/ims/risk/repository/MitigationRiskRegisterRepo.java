package com.vts.ims.risk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.risk.model.MitigationRiskRegisterModel;

public interface MitigationRiskRegisterRepo  extends JpaRepository<MitigationRiskRegisterModel, Long>{

	@Query(value = "SELECT a.MitigationRiskRegisterId,a.RiskRegisterId,a.MitigationApproach,a.Probability,a.TechnicalPerformance,a.Time,a.Cost,a.Average,a.RiskNo,a.RevisionNo FROM ims_mitigation_risk_register a,ims_risk_register b WHERE a.RiskRegisterId=b.RiskRegisterId AND a.IsActive='1' AND b.IsActive='1' AND a.RiskRegisterId=:riskRegisterId",nativeQuery = true)
	public List<Object[]> findByRiskRegisterId(@Param("riskRegisterId") Long riskRegisterId);

	@Query(value = "SELECT a.MitigationRiskRegisterId,a.RiskRegisterId,a.MitigationApproach,a.Probability,a.TechnicalPerformance,a.Time,a.Cost,a.Average,a.RiskNo,a.RevisionNo FROM ims_mitigation_risk_register a,ims_risk_register b WHERE a.RiskRegisterId=b.RiskRegisterId AND a.IsActive='1' AND b.IsActive='1'",nativeQuery = true)
	public List<Object[]> getAllMititgationRiskRegisterlist();

}
