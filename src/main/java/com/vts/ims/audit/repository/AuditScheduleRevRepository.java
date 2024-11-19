package com.vts.ims.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.audit.model.AuditScheduleRevision;

public interface AuditScheduleRevRepository extends JpaRepository<AuditScheduleRevision, Long> {
	
	
	   @Query("SELECT a FROM AuditScheduleRevision a WHERE a.scheduleId = :scheduleId AND a.revisionNo = (SELECT MAX(ar.revisionNo) FROM AuditScheduleRevision ar WHERE ar.scheduleId = :scheduleId)")
	   public AuditScheduleRevision findByScheduleId(@Param("scheduleId") Long scheduleId);

}
