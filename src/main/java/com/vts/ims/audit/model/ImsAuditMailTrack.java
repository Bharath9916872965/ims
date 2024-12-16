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
@Table(name = "ims_audit_mail_track")
public class ImsAuditMailTrack {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MailTrackingId;               
    private LocalDateTime CreatedDate;   
    private Long MailExpectedCount;   
    private Long MailSentCount;   
    private LocalDateTime MailSentDateTime;   
    private String MailSentStatus;   

}
