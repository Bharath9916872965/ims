package com.vts.ims.audit.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ims_audit_mail_track_insights")
public class ImsAuditMailTrackInsights {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "MailTrackingInsightsId")
    private Long mailTrackingInsightsId; 
	
	@Column(name = "MailTrackingId")
    private Long mailTrackingId;  
	
	@Column(name = "EmpId")
    private Long empId; 
	
	@Column(name = "Message")
    private String message;   
	
	@Column(name = "MailStatus")
    private Character mailStatus;   
	
	@Column(name = "MailSentDate")
    private LocalDateTime mailSentDate;  
	
	@Column(name = "CreatedDate")
    private LocalDateTime createdDate;   

}
