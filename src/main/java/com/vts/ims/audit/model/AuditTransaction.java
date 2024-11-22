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
@Table(name="ims_audit_trans")
public class AuditTransaction {
	             
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AuditScheduleTransactionId")
	private Long auditScheduleTransactionId;
	
	@Column(name = "ScheduleId")
	private Long scheduleId;
	
	@Column(name = "EmpId")
	private Long empId;
	
	@Column(name = "TransactionDate")
	private LocalDateTime transactionDate;
	
	@Column(name = "AuditStatus")
	private String auditStatus;
	
	@Column(name = "Remarks")
	private String remarks;
}
