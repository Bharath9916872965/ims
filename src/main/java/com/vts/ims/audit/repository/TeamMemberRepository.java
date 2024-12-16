package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.vts.ims.audit.model.AuditTeamMembers;

public interface TeamMemberRepository extends JpaRepository<AuditTeamMembers, Long>{

	public List<AuditTeamMembers> findAllByIsActive(int isActive);
	
	@Modifying   
	@Transactional
	@Query(value = "DELETE FROM ims_audit_team_members tm WHERE tm.TeamId =:teamId",nativeQuery = true)
	public int deleteByTeamId(@RequestParam("teamId") Long teamId);
	
	@Query(value = "SELECT COUNT(TeamMemberId) FROM ims_audit_team_members WHERE AuditorId = :AuditorId", nativeQuery = true)
	public Long countTeamMembersByAuditorId(@Param("AuditorId") String auditorId);
	
	@Query(value = "SELECT b.IqaNo,d.EmpId FROM ims_audit_schedule a,ims_audit_iqa b,ims_audit_team_members c,ims_audit_auditor d WHERE a.IsActive = 1 AND b.IsActive = 1 AND c.IsActive = 1 AND d.IsActive = 1 AND c.AuditorId = d.AuditorId AND c.TeamId = a.TeamId AND a.ScheduleId = :scheduleId AND a.IqaId = b.IqaId",nativeQuery = true)
	public List<Object[]> getTeamMembersByScheduleId(@Param("scheduleId") Integer scheduleId);

}
