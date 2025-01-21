package com.vts.ims.master.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.vts.ims.master.dto.*;

public interface MasterService {

	public LabMasterDto labDetailsList(String username) throws Exception;
	public String LogoImage()throws Exception;
	public DocTemplateAttributesDto getDocTemplateAttributesDto() throws Exception;
	public List<LoginDetailsDto> loginDetailsList(String user) throws Exception;
	public String getDrdoLogo()throws Exception;
	public UserDetailsDto GetEmpDetails(String  username)throws Exception;
	public List<EmployeeDto> getemployeebyid(long empId) throws ExecutionException;
	public List<CommitteeScheduleDto> GetComitteeScheduleList(String committeeType) throws Exception;
	public List<ActionAssignDto> GetCommitteeScheduleActionAssignList(String committeeType) throws Exception;
	public List<DivisionMasterDto> getDivisionMasterList(String username) throws Exception;
	public List<DivisionGroupDto> getDivisonGroupList(String username) throws Exception;
	public List<ProjectMasterDto> getprojectMasterList(String username) throws Exception;
	public List<SupplyOrderDto> getSupplyOrderList(String labCode) throws Exception;
	public List<ItemReceivedDto> getItemReceivedList() throws Exception;
	public List<ActiveProcurementDto> getActiveProcurementList() throws Exception;
	
}