package com.vts.ims.audit.service;

import java.util.List;

import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.master.dto.EmployeeDto;

public interface AuditService {

	public List<AuditorDto> getAditList() throws Exception;

	public List<EmployeeDto> getEmployelist() throws Exception;

	public long insertAuditor(String[] empIds,String username) throws Exception;

}
