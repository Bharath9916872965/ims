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

	@Query(value = "SELECT a.AuditCheckListId,a.ScheduleId,a.IqaId,a.MocId,a.AuditObsId,a.AuditorRemarks,b.ClauseNo,b.SectionNo,b.MocParentId,b.IsForCheckList,b.MocDescription,a.AuditeeRemarks\r\n"
			+ "FROM ims_audit_check_list a,ims_qms_qm_mapping_classes b WHERE a.IsActive = 1 AND b.IsActive = 1 AND a.MocId = b.MocId AND a.ScheduleId = :scheduleId",nativeQuery = true)
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
}
