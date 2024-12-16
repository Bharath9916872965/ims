package com.vts.ims.audit.model;

import java.time.LocalDateTime;

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
    private Long MailTrackingInsightsId; 
    private Long MailTrackingId;   
    private Long EmpId; 
    private String Message;   
    private Character MailPurpose;    
    private Character MailStatus;   
    private LocalDateTime MailSentDate;   
    private LocalDateTime CreatedDate;   

}
