package com.vts.ims.audit.service;

import java.util.List;

import com.vts.ims.audit.dto.AuditTeamEmployeeDto;
import com.vts.ims.audit.dto.AuditTeamMembersDto;
import com.vts.ims.audit.dto.AuditeeDto;
import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.dto.AuditorTeamDto;
import com.vts.ims.audit.dto.IqaDto;
import com.vts.ims.audit.model.AuditTeam;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;

public interface AuditService {

	public List<AuditorDto> getAuditorList() throws Exception;

	public List<EmployeeDto> getEmployelist() throws Exception;

	public long insertAuditor(String[] empIds,String username) throws Exception;

	public long updateAuditor(AuditorDto auditordto, String username) throws Exception;

	public List<IqaDto> getIqaList() throws Exception;

	public long insertIqa(IqaDto iqadto, String username) throws Exception;

	public List<AuditeeDto> getAuditeeList() throws Exception;

	public List<DivisionMasterDto> getDivisionMaster() throws Exception;

	public List<DivisionGroupDto> getDivisionGroupList() throws Exception;

	public List<ProjectMasterDto> getProjectMasterList() throws Exception;

	public long insertAuditee(AuditeeDto auditeedto, String username) throws Exception;
	
	public List<AuditTeam> getTeamList()throws Exception;

	public long updateAuditee(String auditeeId, String username) throws Exception;

	public List<AuditorTeamDto> getAuditTeamMainList() throws Exception;

	public List<AuditorDto> getAuditorIsActiveList() throws Exception;

	public List<AuditTeamMembersDto> getTeamMmberIsActiveList() throws Exception;

	public long insertAuditTeam(AuditorTeamDto auditormemberteamdto, String username) throws Exception;

	public List<AuditTeamEmployeeDto> getauditteammemberlist() throws Exception;

}
