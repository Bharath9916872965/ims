package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.audit.model.AuditCheckList;

public interface AuditCheckListRepository extends JpaRepository<AuditCheckList, Long>{

	@Query(value = "SELECT a.AuditCheckListId,a.ScheduleId,a.IqaId,a.MocId,a.AuditObsId,a.AuditorRemarks,b.ClauseNo,b.SectionNo,b.MocParentId,b.IsForCheckList,a.MocDescription,a.AuditeeRemarks,c.ScheduleStatus,(SELECT d.Observation FROM ims_audit_obs d WHERE d.AuditObsId = a.AuditObsId ) AS 'obsName',a.Attachment\r\n"
			+ "FROM ims_audit_check_list a,ims_qms_qm_mapping_classes b,ims_audit_schedule c WHERE a.IsActive = 1 AND b.IsActive = 1 AND a.MocId = b.MocId AND a.ScheduleId = :scheduleId AND a.ScheduleId = c.ScheduleId",nativeQuery = true)
	public List<Object[]> getAuditCheckList(@Param("scheduleId")String scheduleId);
	
	@Query(value = "SELECT b.AuditCheckListId,b.Attachment FROM ims_qms_qm_mapping_classes a,ims_audit_check_list b WHERE a.IsActive = 1 AND b.IsActive = 1 AND a.ClauseNo = '8.3.1' AND a.MocId = b.MocId AND b.ScheduleId = :scheduleId",nativeQuery = true)
	public List<Object[]> getCheckListUpload(@Param("scheduleId")String scheduleId);

	@Query(value = "WITH counts AS(SELECT(SELECT COUNT(*) FROM ims_qms_qm_mapping_classes a WHERE a.IsActive = 1 AND a.IsForCheckList = 'Y') AS moc_count,(SELECT COUNT(*) FROM ims_audit_check_list b WHERE b.IsActive = 1 AND b.ScheduleId = :scheduleId) AS checklist_count)\r\n"
			+ "SELECT CASE WHEN moc_count = checklist_count OR moc_count = checklist_count+1 THEN 1 ELSE 0 END AS 'Result' FROM counts",nativeQuery = true)
	public Integer checkAuditeeFinalAdd(@Param("scheduleId")Integer scheduleId);
	
	@Query(value = "SELECT COUNT(a.MocId) AS 'addCount','M' AS 'checkListType' FROM ims_qms_qm_mapping_classes a WHERE a.IsActive = 1 AND a.IsForCheckList = 'Y'\r\n"
			+ "UNION\r\n"
			+ "SELECT COUNT(b.AuditCheckListId) AS 'addCount','C' AS 'checkListType' FROM ims_audit_check_list b WHERE b.IsActive = 1 AND b.ScheduleId = :scheduleId",nativeQuery = true)
	public List<Object[]> getCheckListAddCount(@Param("scheduleId")Long scheduleId);
	
	
	 @Query(value = """
		      SELECT
              a.IqaId,

              COUNT(IF(a.AuditObsId = '2' AND a.IsActive = '1'  AND a.ScheduleStatus='ABA', 1, NULL)) AS totalNC,
              COUNT(IF(a.AuditObsId = '3' AND a.IsActive = '1'  AND a.ScheduleStatus='ABA', 1, NULL)) AS totalOBS,
              COUNT(IF(a.AuditObsId = '4' AND a.IsActive = '1'  AND a.ScheduleStatus='ABA', 1, NULL)) AS totalOFI

              FROM ims_audit_check_list a 
              GROUP BY a.IqaId;
		    """, nativeQuery = true)
		    List<Object[]> getTotalObsCountByIqa();
		    
		    @Query(value = """
		    		 SELECT 
		    		    a.IqaId,
		    		    f.IqaNo,
                        f.FromDate,
                        f.ToDate,
		    		    a.ScheduleId,
		    		    a.AuditeeId,
		    		    d.EmpId,
		    		    d.DivisionId,
		    		    d.GroupId,
		    		    d.ProjectId,
		    		  

		    		    COUNT(CASE WHEN cl.AuditObsId = '2' AND cl.IsActive = '1'  AND a.ScheduleStatus='ABA'  THEN 1 END) AS totalNC,
		    		    COUNT(CASE WHEN cl.AuditObsId = '3' AND cl.IsActive = '1'  AND a.ScheduleStatus='ABA' THEN 1 END) AS totalOBS,
		    		    COUNT(CASE WHEN cl.AuditObsId = '4' AND cl.IsActive = '1'  AND a.ScheduleStatus='ABA' THEN 1 END) AS totalOFI
		    		FROM 
		    		    ims_audit_schedule a
		    		JOIN 
		    		    ims_audit_auditee d ON d.AuditeeId = a.AuditeeId
		    		JOIN 
		    		    ims_audit_iqa f ON a.IqaId = f.IqaId
		    		LEFT JOIN
		    		    ims_audit_check_list cl ON cl.ScheduleId = a.ScheduleId AND cl.IqaId = a.IqaId
		    		WHERE 
		    		    a.IsActive = 1 
		    		    AND d.IsActive = 1
		    		GROUP BY 
		    		    a.ScheduleId, a.AuditeeId, d.EmpId, d.DivisionId, d.GroupId, d.ProjectId, a.IqaId
		    		ORDER BY COALESCE(totalNC, 0) DESC;
		    		 """, nativeQuery = true)
		    		 List<Object[]> getCheckListObsByIqa();
						    
							
@Query(value = """
 SELECT 
    a.IqaId,
    a.ScheduleId,
    a.AuditeeId,
    d.EmpId,
    d.DivisionId,
    d.GroupId,
    d.ProjectId,
  

    COUNT(CASE WHEN cl.AuditObsId = '2' AND cl.IsActive = '1' AND a.ScheduleStatus='ABA' THEN 1 END) AS totalNC,
    COUNT(CASE WHEN cl.AuditObsId = '3' AND cl.IsActive = '1' AND a.ScheduleStatus='ABA' THEN 1 END) AS totalOBS,
    COUNT(CASE WHEN cl.AuditObsId = '4' AND cl.IsActive = '1' AND a.ScheduleStatus='ABA' THEN 1 END) AS totalOFI
FROM 
    ims_audit_schedule a
JOIN 
    ims_audit_auditee d ON d.AuditeeId = a.AuditeeId
JOIN 
    ims_audit_iqa f ON a.IqaId = f.IqaId
LEFT JOIN
    ims_audit_check_list cl ON cl.ScheduleId = a.ScheduleId AND cl.IqaId = a.IqaId
WHERE 
    a.IsActive = 1 
    AND d.IsActive = 1
GROUP BY 
    a.ScheduleId, a.AuditeeId, d.EmpId, d.DivisionId, d.GroupId, d.ProjectId, a.IqaId
ORDER BY COALESCE(totalNC, 0) DESC;
 """, nativeQuery = true)
 List<Object[]> getCheckListObsByDivPrjGroup();

 @Query(value = "SELECT a.AuditCheckListId,a.ScheduleId,a.IqaId,a.MocId,a.AuditObsId,a.AuditorRemarks,b.ClauseNo,b.SectionNo,\r\n"
 		+ "b.MocParentId,b.IsForCheckList,b.MocDescription,a.AuditeeRemarks,c.ScheduleStatus,d.CarRefNo,d.CarStatus\r\n"
 		+ "FROM\r\n"
 		+ "ims_audit_check_list a\r\n"
 		+ "LEFT JOIN ims_qms_qm_mapping_classes b ON b.MocId = a.MocId AND b.IsActive = 1\r\n"
 		+ "LEFT JOIN ims_audit_schedule c ON c.ScheduleId = a.ScheduleId \r\n"
 		+ "LEFT JOIN ims_audit_corrective_action d ON d.AuditCheckListId=a.AuditCheckListId\r\n"
 		+ "WHERE a.AuditObsId IN (2,3,4) AND a.IsActive = 1 AND  c.ScheduleStatus='ABA' ",nativeQuery = true)
	public List<Object[]> getAuditCheckListbyObsIds();
	 @Query(value = "SELECT a.MocId,b.MocDescription,COUNT(a.MocId) AS QuestionCount,b.ClauseNo,b.SectionNo,b.MocParentId,b.IsForCheckList\r\n"
	 		+ "FROM ims_audit_check_list a\r\n"
	 		+ "LEFT JOIN ims_qms_qm_mapping_classes b ON a.MocId = b.MocId AND b.IsActive = 1 WHERE a.IsActive = 1 AND a.AuditObsId='2'\r\n"
	 		+ "GROUP BY a.MocId ORDER BY COUNT(a.MocId) DESC",nativeQuery = true)
			public List<Object[]> getMostFrequentNC();
			@Query(value = 
				       "SELECT m.ClauseNo, m.MocDescription,d.CarRefNo ,c.AuditObsId\r\n"
				       + "FROM ims_qms_qm_mapping_classes m \r\n"
				       + "INNER JOIN ims_audit_check_list c ON c.MocId = m.MocId \r\n"
				       + "AND c.AuditObsId =:auditObsId AND c.ScheduleId =:scheduleId AND c.IqaId =:iqaId\r\n"
				       + "LEFT JOIN ims_audit_corrective_action d ON d.AuditCheckListId=c.AuditCheckListId;", nativeQuery = true)
				List<Object[]> getMostFqNCMocDes(@Param("scheduleId") Long scheduleId, 
				                                 @Param("auditObsId") Integer auditObsId, 
				                                 @Param("iqaId") Long iqaId);
				@Query(value = "SELECT a.AuditCheckListId,a.MocId,a.AuditorRemarks,b.MocDescription,b.ClauseNo,b.SectionNo,c.CarRefNo, c.CarStatus,b.MocParentId,b.IsForCheckList\r\n"
						+ "FROM ims_audit_check_list a\r\n"
						+ "LEFT JOIN ims_qms_qm_mapping_classes b ON a.MocId = b.MocId AND b.IsActive = 1  \r\n"
						+ "LEFT JOIN  ims_audit_corrective_action c ON c.AuditCheckListId=a.AuditCheckListId\r\n"
						+ "WHERE a.IsActive = 1 AND a.AuditObsId='2' AND a.MocId=:mocId", nativeQuery = true)
			public List<Object[]> getMostFreqNCDetails(@Param("mocId") Long mocId);



				
			
			
	
}

