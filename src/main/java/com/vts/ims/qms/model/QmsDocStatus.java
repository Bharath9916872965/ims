package com.vts.ims.qms.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ims_qms_doc_status")
public class QmsDocStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StatusId")
	private Long statusId;
	@Column(name = "StatusCode")
	private String statusCode;
	@Column(name = "Status")
	private String status;
	@Column(name = "StatusColor")
	private String statusColor;
}
