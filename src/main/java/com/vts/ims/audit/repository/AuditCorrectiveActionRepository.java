package com.vts.ims.audit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.audit.model.AuditCorrectiveAction;

import jakarta.transaction.Transactional;

@Transactional
public interface AuditCorrectiveActionRepository extends JpaRepository<AuditCorrectiveAction, Long>{

	@Query(value = "SELECT COUNT(CorrectiveActionId) FROM ims_audit_corrective_action WHERE IqaId = :IqaId AND ScheduleId = :ScheduleId AND CarFlag = :CarFlag AND IsActive = 1",nativeQuery = true)
	public Integer getActionCount(@Param("IqaId")Long iqaId,@Param("ScheduleId")Long scheduleId,@Param("CarFlag")String carFlag);
	
	@Query(value = "SELECT a.CorrectiveActionId,a.AuditCheckListId,a.IqaId,a.CarRefNo,a.CarDescription,a.ActionPlan,a.Responsibility,a.TargetDate,b.ScheduleId,c.AuditeeId,a.CarAttachment, a.RootCause,\r\n"
			+ "a.CarCompletionDate,a.CarDate,a.CorrectiveActionTaken,a.CarStatus,(SELECT d.StatusName FROM ims_audit_status d WHERE d.AuditStatus = a.CarStatus) AS 'statusName',e.EmpId\r\n"
			+ "FROM ims_audit_corrective_action a,ims_audit_check_list b,ims_audit_schedule c,ims_audit_auditee e WHERE b.AuditCheckListId = a.AuditCheckListId AND b.ScheduleId = c.ScheduleId AND e.AuditeeId = c.AuditeeId",nativeQuery = true)
	public List<Object[]> getActionTotalList();
	
	@Modifying
	@Query(value = "UPDATE ims_audit_corrective_action SET ActionPlan = :ActionPlan,Responsibility = :Responsibility,TargetDate = :TargetDate,CarDate = :CarDate,ActEmpId = :ActEmpId,ModifiedBy = :ModifiedBy,ModifiedDate = :ModifiedDate WHERE CorrectiveActionId = :CorrectiveActionId",nativeQuery = true)
	public Integer updateActions(@Param("ActionPlan")String actionPlan,@Param("Responsibility")Long responsibility,@Param("TargetDate")LocalDateTime targetDate,@Param("CarDate")LocalDateTime carDate,@Param("ActEmpId")Long actEmpId,@Param("ModifiedBy")String modifiedBy,@Param("ModifiedDate")LocalDateTime modifiedDate,@Param("CorrectiveActionId")Long correctiveActionId); 
	
	@Modifying
	@Query(value = "UPDATE ims_audit_corrective_action SET RootCause = :RootCause,CorrectiveActionTaken = :CorrectiveActionTaken,CarCompletionDate = :CarCompletionDate,ModifiedBy = :ModifiedBy,ModifiedDate = :ModifiedDate WHERE CorrectiveActionId = :CorrectiveActionId",nativeQuery = true)
	public Integer updateCarReport(@Param("RootCause")String rootCause,@Param("CorrectiveActionTaken")String correctiveActionTaken,@Param("CarCompletionDate")LocalDateTime carCompletionDate,@Param("ModifiedBy")String modifiedBy,@Param("ModifiedDate")LocalDateTime modifiedDate,@Param("CorrectiveActionId")Long correctiveActionId);
	
	@Modifying
	@Query(value = "UPDATE ims_audit_corrective_action SET CarStatus = :CarStatus,ModifiedBy = :ModifiedBy,ModifiedDate = :ModifiedDate WHERE CorrectiveActionId = :CorrectiveActionId",nativeQuery = true)
	public Integer updateCarStatus(@Param("CarStatus")String carStatus,@Param("ModifiedBy")String modifiedBy,@Param("ModifiedDate")LocalDateTime modifiedDate,@Param("CorrectiveActionId")Long correctiveActionId);
	
	@Query(value = "(SELECT b.EmpId,b.TransactionDate,b.AuditStatus FROM ims_audit_corrective_action a,ims_audit_trans b WHERE b.AuditType = 'C' AND b.ScheduleId = a.CorrectiveActionId AND b.AuditStatus IN ('FWD') AND a.CorrectiveActionId = :correctiveActionId AND b.TransactionDate >\r\n"
			+ "(SELECT IFNULL(MAX(d.TransactionDate),'1970-01-01') FROM ims_audit_corrective_action c,ims_audit_trans d WHERE d.ScheduleId = c.CorrectiveActionId AND d.AuditType = 'C' AND d.AuditStatus IN ('CRH','CMR') AND c.CorrectiveActionId = :correctiveActionId))\r\n"
			+ "UNION\r\n"
			+ "(SELECT b.EmpId,b.TransactionDate,b.AuditStatus FROM ims_audit_corrective_action a,ims_audit_trans b WHERE b.AuditType = 'C' AND b.ScheduleId = a.CorrectiveActionId AND b.AuditStatus IN ('CRM') AND a.CorrectiveActionId = :correctiveActionId AND b.TransactionDate >\r\n"
			+ "(SELECT IFNULL(MAX(d.TransactionDate),'1970-01-01') FROM ims_audit_corrective_action c,ims_audit_trans d WHERE d.ScheduleId = c.CorrectiveActionId AND d.AuditType = 'C' AND d.AuditStatus IN ('CRH','CMR') AND c.CorrectiveActionId = :correctiveActionId))\r\n"
			+ "UNION\r\n"
			+ "(SELECT b.EmpId,b.TransactionDate,b.AuditStatus FROM ims_audit_corrective_action a,ims_audit_trans b WHERE b.AuditType = 'C' AND b.ScheduleId = a.CorrectiveActionId AND b.AuditStatus IN ('CAP') AND a.CorrectiveActionId = :correctiveActionId AND b.TransactionDate >\r\n"
			+ "(SELECT IFNULL(MAX(d.TransactionDate),'1970-01-01') FROM ims_audit_corrective_action c,ims_audit_trans d WHERE d.ScheduleId = c.CorrectiveActionId AND d.AuditType = 'C' AND d.AuditStatus IN ('CRH','CMR') AND c.CorrectiveActionId = :correctiveActionId))",nativeQuery = true)
	public List<Object[]> getApproveEmpDataList(@Param("correctiveActionId")String correctiveActionId);
	
}
