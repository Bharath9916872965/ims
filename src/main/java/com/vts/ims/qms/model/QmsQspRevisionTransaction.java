package com.vts.ims.qms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ims_qms_qsp_revision_transc")
public class QmsQspRevisionTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long QspTransactionId;
    private Long RevisionRecordId;
    private Long EmpId;
    private String StatusCode;
    private String Remarks;
    private LocalDateTime TransactionDate;
}
