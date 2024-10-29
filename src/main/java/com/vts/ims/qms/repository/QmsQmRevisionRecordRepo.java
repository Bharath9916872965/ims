package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.qms.model.QmsQmRevisionRecord;

public interface QmsQmRevisionRecordRepo extends JpaRepository<QmsQmRevisionRecord, Long> {

	@Query("SELECT a FROM QmsQmRevisionRecord a WHERE a.isActive = 1 ORDER BY a.revisionRecordId DESC")
    List<QmsQmRevisionRecord> findAllActiveQmRecords();
	
}
