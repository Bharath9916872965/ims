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
@Table(name = "ims_audit_mail_track")
public class ImsAuditMailTrack {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MailTrackingId")
    private Long mailTrackingId;   
	
	@Column(name = "CreatedDate")
    private LocalDateTime createdDate;
	
	@Column(name = "MailExpectedCount")
    private Long mailExpectedCount; 
	
	@Column(name = "MailSentCount")
    private Long mailSentCount;   
	
	@Column(name = "MailSentDateTime")
    private LocalDateTime mailSentDateTime;   
	
	@Column(name = "MailSentStatus")
    private String mailSentStatus;   

}
