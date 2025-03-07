package com.vts.ims.qms.model;

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
@Table(name = "ims_qms_dwp_revision_transc")
public class DwpTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long DGTransactionId;
	private Long RevisionRecordId;
    private Long EmpId;
    private String StatusCode;
    private String Remarks;
    private LocalDateTime TransactionDate;

}
