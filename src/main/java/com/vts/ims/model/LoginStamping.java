package com.vts.ims.model;


import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ims_auditstamping")
@Data
public class LoginStamping {
	 @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	 
   @Column(name="AuditStampingId")
	private Long auditStampingId;
	 
	private Long LoginId;
	private String Username;
    private Date LoginDate;
    private LocalDateTime LoginDateTime;
    private String IpAddress;
    private String MacAddress;
    private String LogOutType;
    private LocalDateTime LogOutDateTime;
    

   
}
