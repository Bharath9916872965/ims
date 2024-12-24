package com.vts.ims.master.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.vts.ims.master.dto.DocTemplateAttributesDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.LabMasterDto;
import com.vts.ims.master.dto.LoginDetailsDto;
import com.vts.ims.master.dto.UserDetailsDto;

public interface MasterService {

	public LabMasterDto labDetailsList(String username) throws Exception;
	public String LogoImage()throws Exception;
	public DocTemplateAttributesDto getDocTemplateAttributesDto() throws Exception;
	public List<LoginDetailsDto> loginDetailsList(String user) throws Exception;
	public String getDrdoLogo()throws Exception;
	public UserDetailsDto GetEmpDetails(String  username)throws Exception;
	public List<EmployeeDto> getemployeebyid(long empId) throws ExecutionException;
	
}
