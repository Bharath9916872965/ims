package com.vts.ims.audit.service;

import java.util.List;

import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.dto.IqaDto;
import com.vts.ims.master.dto.EmployeeDto;

public interface AuditService {

	public List<AuditorDto> getAuditList() throws Exception;

	public List<EmployeeDto> getEmployelist() throws Exception;

	public long insertAuditor(String[] empIds,String username) throws Exception;

	public long updateAuditor(AuditorDto auditordto, String username) throws Exception;

	public List<IqaDto> getIqaList() throws Exception;

	public long insertIqa(IqaDto iqadto, String username) throws Exception;

	public IqaDto getIqaById(long iqaId) throws Exception;

}
