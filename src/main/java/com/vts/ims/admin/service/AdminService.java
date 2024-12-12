package com.vts.ims.admin.service;

import java.util.List;

import com.vts.ims.admin.dto.*;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.LoginDetailsDto;
import com.vts.ims.model.LoginStamping;

public interface AdminService {
	
	public List<FormModuleDto> formModuleList(Long imsFormRoleId) throws Exception;
	public List<FormDetailDto> formModuleDetailList(Long imsFormRoleId) throws Exception;
	public List<LoginDetailsDto> loginDetailsList(String user) throws Exception;
	public List<EmployeeDto> employeeList() throws Exception;
	public List<AuditStampingDto> getAuditStampinglist(AuditStampingDto stamping)throws Exception;
	public long loginStampingInsert(LoginStamping Stamping)throws Exception;
	public long lastLoginStampingId(long LoginId)throws Exception;
	public long loginStampingUpdate(LoginStamping Stamping)throws Exception;
	public List<UserManagerListDto> UserManagerList(String username)throws Exception;
	public List<FormRoleDto> roleList()throws Exception;
	public List<FormModuleDto> getformModulelist()throws Exception;
	public List<FormroleAccessDto> getformRoleAccessList(String roleId, String formModuleId);
	public String updateformroleaccess(FormroleAccessDto accessDto, String username);
}
