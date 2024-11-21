package com.vts.ims.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="notification")
@Data
public class ImsNotification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NotificationId")
	private Long notificationId;
	private Long EmpId;
	private Long Notificationby;
	private LocalDateTime NotificationDate;
	private String NotificationMessage;
	private String NotificationUrl;
	private Integer IsActive;
	private String CreatedBy;
	private LocalDateTime CreatedDate;
	private String ModifiedBy;
	private LocalDateTime ModifiedDate;
	

	
}
