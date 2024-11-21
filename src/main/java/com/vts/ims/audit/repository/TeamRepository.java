package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.audit.model.AuditTeam;

public interface TeamRepository extends JpaRepository<AuditTeam, Long> {
	

	public List<AuditTeam> findAllByIsActive(int isActive);

	@Query(value = "SELECT c.EmpId,a.TeamCode,d.IqaNo,b.IsLead,c.AuditorId,a.TeamId,b.TeamMemberId,d.IqaId FROM ims_audit_team a,ims_audit_team_members b,ims_audit_auditor c,ims_audit_iqa d WHERE a.TeamId=b.TeamId AND b.AuditorId=c.AuditorId AND a.IqaId=d.IqaId AND a.IsActive='1' AND b.IsActive='1' AND c.IsActive='1' AND d.IsActive='1'",nativeQuery = true)
	public List<Object[]> getAuditTeamMemberList();

	
	

}
