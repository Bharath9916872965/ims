package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.audit.model.AuditTeam;

public interface TeamRepository extends JpaRepository<AuditTeam, Long> {
	

	 List<AuditTeam> findAllByIsActive(int isActive);

	@Query(value="SELECT b.TeamMemberId,c.EmpId,b.IsLead FROM ims_audit_team a,ims_audit_team_members b,ims_audit_auditor c WHERE a.IsActive=1 AND b.IsActive=1 AND c.IsActive=1 AND a.TeamId = b.TeamId AND b.AuditorId = c.AuditorId AND a.TeamId =:teamId",nativeQuery = true)
	public List<Object[]> getTeamMemberDetails(@Param("teamId") Long teamId)throws Exception;
	
	@Query(value="SELECT c.EmpId,a.IqaId,a.TeamId,b.IsLead,c.AuditorId,b.TeamMemberId FROM  ims_audit_team a,ims_audit_team_members b,ims_audit_auditor c WHERE a.TeamId = b.TeamId AND b.AuditorId = c.AuditorId AND a.IsActive = 1 AND b.IsActive = 1 AND b.IsActive = 1",nativeQuery = true)
	public List<Object[]> getTotalTeamMemberDetails()throws Exception;
	

}
