package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.vts.ims.audit.model.AuditTeamMembers;

public interface TeamMemberRepository extends JpaRepository<AuditTeamMembers, Long>{

	public List<AuditTeamMembers> findAllByIsActive(int isActive);
	
	@Modifying   
	@Transactional
	@Query(value = "DELETE FROM ims_audit_team_members tm WHERE tm.TeamId =:teamId",nativeQuery = true)
	public int deleteByTeamId(@RequestParam("teamId") Long teamId);
}
