package com.vts.ims.audit.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="ims_audit_corrective_action")
public class AuditCorrectiveAction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CorrectiveActionId")
	private Long correctiveActionId;
	
	@Column(name = "IqaId")
	private Long iqaId;
	
	@Column(name = "ScheduleId")
	private Long scheduleId;
	
	@Column(name = "AuditCheckListId")
	private Long auditCheckListId;
	
	@Column(name = "CarDate")
	private LocalDateTime carDate;
	
	@Column(name = "CarRefNo")
	private String carRefNo;
	
	@Column(name = "CarFlag")
	private String carFlag;

	@Column(name = "CarDescription")
	private String carDescription;
	
	@Column(name = "ActionPlan")
	private String actionPlan;
	
	@Column(name = "Responsibility")
	private Long responsibility;
	
	@Column(name = "TargetDate")
	private LocalDateTime targetDate;
	
	@Column(name = "ActEmpId")
	private Long actEmpId;
	
	@Column(name = "CarStatus")
	private String carStatus;
	    
	@Column(name = "CarAttachment")
	private String carAttachment;
	
	@Column(name = "RootCause")
	private String rootCause;
	
	@Column(name = "CorrectiveActionTaken")
	private String correctiveActionTaken;
	
	@Column(name = "CarCompletionDate")
	private LocalDateTime carCompletionDate;
	
	@Column(name = "CreatedBy")
	private String createdBy;
	
	@Column(name = "CreatedDate")
	private LocalDateTime createdDate;
	
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	
	@Column(name = "ModifiedDate")
	private LocalDateTime modifiedDate;
	
	@Column(name = "IsActive")
	private int isActive;
}
