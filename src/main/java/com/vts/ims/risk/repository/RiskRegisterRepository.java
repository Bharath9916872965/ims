package com.vts.ims.risk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.risk.model.RiskRegisterModel;

public interface RiskRegisterRepository extends JpaRepository<RiskRegisterModel, Long>{

	@Query(value = "SELECT r.RiskRegisterId,r.RevisionRecordId,r.RiskDescription,r.Probability,r.TechnicalPerformance,r.Time,r.Cost,r.Average,r.RiskNo,b.DocType,b.IssueNo,b.RevisionNo,b.Description,b.DateOfRevision,b.StatusCode FROM ims_risk_register r,ims_qms_dwp_revision_record b WHERE r.RevisionRecordId=b.RevisionRecordId AND b.IssueNo='1' AND r.IsActive='1' AND b.IsActive='1' AND r.RevisionRecordId=:revisionRecordId",nativeQuery = true)
	public List<Object[]> findByRevisionRecordId(@Param("revisionRecordId") Long revisionRecordId);

}
