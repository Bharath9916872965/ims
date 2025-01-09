package com.vts.ims.risk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.risk.model.RiskRegisterModel;

public interface RiskRegisterRepository extends JpaRepository<RiskRegisterModel, Long>{

	@Query(value = "SELECT r.RiskRegisterId,r.RevisionRecordId,r.RiskDescription,r.Probability,r.TechnicalPerformance,r.Time,r.Cost,r.Average,r.RiskNo,b.DocType,b.IssueNo,b.RevisionNo,b.Description,b.DateOfRevision,b.StatusCode FROM ims_risk_register r,ims_qms_dwp_revision_record b WHERE r.RevisionRecordId=b.RevisionRecordId AND b.IssueNo='1' AND r.IsActive='1' AND b.IsActive='1' AND r.RevisionRecordId=:revisionRecordId",nativeQuery = true)
	public List<Object[]> findByRevisionRecordId(@Param("revisionRecordId") Long revisionRecordId);
	@Query(value = "SELECT  a.RiskRegisterId,a.RevisionRecordId,a.RiskDescription,a.Probability,a.TechnicalPerformance,a.Time,a.Cost,a.Average,a.RiskNo,b.DocType,b.GroupDivisionId,b.Description,c.MitigationApproach,\r\n"
			+ "    c.Probability AS 'mitigationProbability',c.TechnicalPerformance AS 'mitigationTp',c.Time AS 'mitigationTime',c.Cost AS 'mitigationCost', c.Average AS 'mitigationAverage',c.RiskNo AS 'mitigationRiskNo'\r\n"
			+ "FROM ims_risk_register a\r\n"
			+ "JOIN ims_qms_dwp_revision_record b  ON a.RevisionRecordId = b.RevisionRecordId\r\n"
			+ "LEFT JOIN  ims_mitigation_risk_register c ON a.RiskRegisterId = c.RiskRegisterId\r\n"
			+ "AND c.MitigationRiskRegisterId = (SELECT MAX(MitigationRiskRegisterId) FROM ims_mitigation_risk_register WHERE RiskRegisterId = a.RiskRegisterId )\r\n"
			+ "WHERE  b.GroupDivisionId = :groupDivisionId AND a.RevisionRecordId=:revisionRecordId AND b.DocType = :docType ORDER BY a.RiskRegisterId ASC",nativeQuery = true)
	public List<Object[]> getRegMitigationList(@Param("groupDivisionId") Long groupDivisionId, @Param("docType") String docType,@Param("revisionRecordId") Long revisionRecordId);
	

}
