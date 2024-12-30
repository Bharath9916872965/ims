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

	@Query(value = "SELECT COUNT(CorrectiveActionId) FROM ims_audit_corrective_action WHERE IqaId = :IqaId AND IsActive = 1",nativeQuery = true)
	public Integer getActionCount(@Param("IqaId")Long iqaId);
	
	@Query(value = "SELECT a.CorrectiveActionId,a.AuditCheckListId,a.IqaId,a.CarRefNo,a.CarDescription,a.ActionPlan,a.Responsibility,a.TargetDate,b.ScheduleId,c.AuditeeId,a.CarAttachment, a.RootCause, a.CarCompletionDate,a.CarDate\r\n"
			+ "FROM ims_audit_corrective_action a,ims_audit_check_list b,ims_audit_schedule c WHERE b.AuditCheckListId = a.AuditCheckListId AND b.ScheduleId = c.ScheduleId",nativeQuery = true)
	public List<Object[]> getActionTotalList();
	
	@Modifying
	@Query(value = "UPDATE ims_audit_corrective_action SET ActionPlan = :ActionPlan,Responsibility = :Responsibility,TargetDate = :TargetDate,CarDate = :CarDate,ActEmpId = :ActEmpId,ModifiedBy = :ModifiedBy,ModifiedDate = :ModifiedDate WHERE CorrectiveActionId = :CorrectiveActionId",nativeQuery = true)
	public Integer updateActions(@Param("ActionPlan")String actionPlan,@Param("Responsibility")Long responsibility,@Param("TargetDate")LocalDateTime targetDate,@Param("CarDate")LocalDateTime carDate,@Param("ActEmpId")Long actEmpId,@Param("ModifiedBy")String modifiedBy,@Param("ModifiedDate")LocalDateTime modifiedDate,@Param("CorrectiveActionId")Long correctiveActionId); 
	
	@Modifying
	@Query(value = "UPDATE ims_audit_corrective_action SET CarAttachment = :CarAttachment,RootCause = :RootCause,CarCompletionDate = :CarCompletionDate,ModifiedBy = :ModifiedBy,ModifiedDate = :ModifiedDate WHERE CorrectiveActionId = :CorrectiveActionId",nativeQuery = true)
	public Integer updateCarReport(@Param("CarAttachment")String carAttachment,@Param("RootCause")String rootCause,@Param("CarCompletionDate")LocalDateTime carCompletionDate,@Param("ModifiedBy")String modifiedBy,@Param("ModifiedDate")LocalDateTime modifiedDate,@Param("CorrectiveActionId")Long correctiveActionId);
	
}
