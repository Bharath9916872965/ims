package com.vts.ims.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.audit.model.AuditTransaction;

public interface AuditTransactionRepository extends JpaRepository<AuditTransaction, Long> {

}
