package com.vts.ims.qms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.qms.model.DwpTransaction;

public interface DwpRevisionTransactionRepo extends JpaRepository<DwpTransaction, Long>{

}
