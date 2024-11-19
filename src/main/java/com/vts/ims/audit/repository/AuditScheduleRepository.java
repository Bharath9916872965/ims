package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.audit.model.AuditSchedule;

public interface AuditScheduleRepository extends JpaRepository<AuditSchedule, Long> {
	
	@Query(value="SELECT a.ScheduleId,a.ScheduleDate,a.AuditeeId,a.TeamId,c.TeamCode,d.EmpId,d.DivisionId,d.GroupId,d.ProjectId,(SELECT MAX(b.RevisionNo) FROM ims_audit_schedule_rev b WHERE a.ScheduleId= b.ScheduleId AND b.IsActive = 1) AS 'revision' FROM ims_audit_schedule a,ims_audit_team c,ims_audit_auditee d WHERE a.IsActive = 1 AND a.IsActive = 1 AND a.TeamId = c.TeamId AND d.IsActive = 1 AND d.AuditeeId = a.AuditeeId ORDER BY a.ScheduleId DESC",nativeQuery = true)
	public List<Object[]> getScheduleList();
}
