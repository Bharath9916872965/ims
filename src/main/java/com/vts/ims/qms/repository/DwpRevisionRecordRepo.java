package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.qms.model.DwpRevisionRecord;

public interface DwpRevisionRecordRepo extends JpaRepository<DwpRevisionRecord, Long> {

	@Query("SELECT a FROM DwpRevisionRecord a WHERE a.isActive = 1 ORDER BY a.revisionRecordId DESC")
    List<DwpRevisionRecord> findAllActiveDwpRecords();
	
}
