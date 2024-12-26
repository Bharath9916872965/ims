package com.vts.ims.audit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.audit.model.AuditCheckList;

import jakarta.transaction.Transactional;

@Transactional
public interface AuditCheckListRepository extends JpaRepository<AuditCheckList, Long>{

	@Query(value = "SELECT a.AuditCheckListId,a.ScheduleId,a.IqaId,a.MocId,a.AuditObsId,a.AuditorRemarks,b.ClauseNo,b.SectionNo,b.MocParentId,b.IsForCheckList,b.MocDescription,a.AuditeeRemarks,c.ScheduleStatus,(SELECT d.Observation FROM ims_audit_obs d WHERE d.AuditObsId = a.AuditObsId ) AS 'obsName'\r\n"
			+ "FROM ims_audit_check_list a,ims_qms_qm_mapping_classes b,ims_audit_schedule c WHERE a.IsActive = 1 AND b.IsActive = 1 AND a.MocId = b.MocId AND a.ScheduleId = :scheduleId AND a.ScheduleId = c.ScheduleId",nativeQuery = true)
	public List<Object[]> getAuditCheckList(@Param("scheduleId")String scheduleId);
	
	@Query(value = "SELECT b.AuditCheckListId,b.Attachment FROM ims_qms_qm_mapping_classes a,ims_audit_check_list b WHERE a.IsActive = 1 AND b.IsActive = 1 AND a.ClauseNo = '8.3.1' AND a.MocId = b.MocId AND b.ScheduleId = :scheduleId",nativeQuery = true)
	public List<Object[]> getCheckListUpload(@Param("scheduleId")String scheduleId);
	
	@Modifying
	@Query(value = "UPDATE ims_audit_check_list SET Attachment = :Attachment, ModifiedBy = :ModifiedBy, ModifiedDate = :ModifiedDate WHERE AuditCheckListId = :AuditCheckListId",nativeQuery = true)
	public Integer updateUpload(@Param("Attachment")String attachment,@Param("ModifiedBy")String modifiedBy,@Param("ModifiedDate")LocalDateTime modifiedDate,@Param("AuditCheckListId")String auditCheckListId);
	
	@Modifying
	@Query(value = "UPDATE ims_audit_check_list SET AuditeeRemarks = :AuditeeRemarks, ModifiedBy = :ModifiedBy, ModifiedDate = :ModifiedDate WHERE AuditCheckListId = :AuditCheckListId",nativeQuery = true)
	public Integer updateAuditeeRemarks(@Param("AuditeeRemarks")String auditeeRemarks,@Param("ModifiedBy")String modifiedBy,@Param("ModifiedDate")LocalDateTime modifiedDate,@Param("AuditCheckListId")Integer auditCheckListId);
	
	@Modifying
	@Query(value = "UPDATE ims_audit_check_list SET AuditObsId = :AuditObsId, AuditorRemarks = :AuditorRemarks, ModifiedBy = :ModifiedBy, ModifiedDate = :ModifiedDate WHERE AuditCheckListId = :AuditCheckListId",nativeQuery = true)
	public Integer updateAuditorRemarks(@Param("AuditObsId")Integer auditObsId,@Param("AuditorRemarks")String auditorRemarks,@Param("ModifiedBy")String modifiedBy,@Param("ModifiedDate")LocalDateTime modifiedDate,@Param("AuditCheckListId")Integer auditCheckListId);
	
	@Query(value = "SELECT CASE WHEN (SELECT COUNT(a.MocId) FROM ims_qms_qm_mapping_classes a WHERE a.IsActive = 1 AND a.IsForCheckList = 'Y') = ((SELECT COUNT(b.AuditCheckListId) FROM ims_audit_check_list b WHERE b.IsActive = 1 AND b.ScheduleId = :scheduleId) + 1) THEN 1 ELSE 0 END AS 'Result'",nativeQuery = true)
	public Integer checkAuditeeFinalAdd(@Param("scheduleId")Integer scheduleId);
	
	
	 @Query(value = """
		      SELECT
              a.IqaId,

              COUNT(IF(a.AuditObsId = '2' AND a.IsActive = '1', 1, NULL)) AS totalNC,
              COUNT(IF(a.AuditObsId = '3' AND a.IsActive = '1', 1, NULL)) AS totalOBS,
              COUNT(IF(a.AuditObsId = '4' AND a.IsActive = '1', 1, NULL)) AS totalOFI

              FROM ims_audit_check_list a 
              GROUP BY a.IqaId;
		    """, nativeQuery = true)
		    List<Object[]> getTotalObsCountByIqa();
		    
		    
			
//			 @Query(value = """
//				      SELECT 
//    a.ScheduleId,
//    a.AuditeeId,
//    d.EmpId,
//    d.DivisionId,
//    d.GroupId,
//    d.ProjectId,
//    a.IqaId,
//
//    (SELECT COUNT(*)
//     FROM ims_audit_check_list cl
//     WHERE cl.ScheduleId = a.ScheduleId 
//       AND cl.IqaId = a.IqaId
//       AND cl.AuditObsId = '2' 
//       AND cl.IsActive = '1') AS totalNC,
//
//    (SELECT COUNT(*)
//     FROM ims_audit_check_list cl
//     WHERE cl.ScheduleId = a.ScheduleId 
//       AND cl.IqaId = a.IqaId
//       AND cl.AuditObsId = '3' 
//       AND cl.IsActive = '1') AS totalOBS,
//
//    (SELECT COUNT(*)
//     FROM ims_audit_check_list cl
//     WHERE cl.ScheduleId = a.ScheduleId 
//       AND cl.IqaId = a.IqaId
//       AND cl.AuditObsId = '4' 
//       AND cl.IsActive = '1') AS totalOFI
//FROM 
//    ims_audit_schedule a
//JOIN 
//    ims_audit_auditee d ON d.AuditeeId = a.AuditeeId
//JOIN 
//    ims_audit_iqa f ON a.IqaId = f.IqaId
//WHERE 
//    a.IsActive = 1 
//    AND d.IsActive = 1
//ORDER BY 
//    a.ScheduleId DESC;
//				    """, nativeQuery = true)
//				    List<Object[]> getCheckListObsByDivPrjGroup();
				    
				    
					

						    
							
@Query(value = """
 SELECT 
    a.IqaId,
    a.ScheduleId,
    a.AuditeeId,
    d.EmpId,
    d.DivisionId,
    d.GroupId,
    d.ProjectId,
  

    COUNT(CASE WHEN cl.AuditObsId = '2' AND cl.IsActive = '1' THEN 1 END) AS totalNC,
    COUNT(CASE WHEN cl.AuditObsId = '3' AND cl.IsActive = '1' THEN 1 END) AS totalOBS,
    COUNT(CASE WHEN cl.AuditObsId = '4' AND cl.IsActive = '1' THEN 1 END) AS totalOFI
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
ORDER BY 
    a.ScheduleId DESC;
 """, nativeQuery = true)
 List<Object[]> getCheckListObsByDivPrjGroup();
	
}
