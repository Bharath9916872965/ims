package com.vts.ims.dashboard.service;

import java.util.List;

import com.vts.ims.audit.dto.AuditorTeamDto;
import com.vts.ims.audit.dto.IqaDto;
import com.vts.ims.dashboard.dto.CheckListObsCountDto;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.ProjectMasterDto;
import com.vts.ims.qms.dto.DwpRevisionRecordDto;
import com.vts.ims.qms.dto.QmsDocTypeDto;
import com.vts.ims.qms.dto.QmsQmRevisionRecordDto;
import com.vts.ims.qms.model.DwpGwpDocumentSummary;

public interface DashboardService {
	
	public List<IqaDto> getIqaListForDashboard() throws Exception;
	public List<DwpRevisionRecordDto> getAllDwpRevisionListForDashboard() throws Exception;
	
	
	public List<QmsQmRevisionRecordDto> getQmVersionDetailedDtoList() throws Exception;

	public long getNoOfActiveAuditees() throws Exception;
	public long getNoOfActiveAuditors() throws Exception;
	public long getNoOfActiveTeams() throws Exception;
	public long getNoOfActiveSchedules() throws Exception;
	
	public List<CheckListObsCountDto> getTotalObsCountByIqa() throws Exception;
	public List<CheckListObsCountDto> getCheckListDataByObservation() throws Exception;
	public List<DwpRevisionRecordDto> getAllVersionRecordDtoList(QmsDocTypeDto qmsDocTypeDto) throws Exception;
	

	public List<DivisionGroupDto> getGroupListOfDivEmps(Integer imsFormRoleId, Long empId) throws Exception;


	public List<ProjectMasterDto> getProjectListOfPrjEmps(Integer imsFormRoleId, Long empId) throws Exception;
	public List<DivisionMasterDto> getAllActiveDivisionList(String username) throws Exception;
	public List<DivisionMasterDto> getDivisionListOfDivEmps(Integer imsFormRoleId, Long empId) throws Exception;
	public List<DivisionMasterDto> getDivisionListOfDH(Integer imsFormRoleId, Long empId) throws Exception;
	public List<DivisionMasterDto> getDivisionListOfGH(Integer imsFormRoleId, Long empId) throws Exception;
	public List<ProjectMasterDto> getProjectListOfPrjDir(Integer imsFormRoleId, Long empId) throws Exception;
	
	
	public List<CheckListObsCountDto> getTrendNcObsList() throws Exception;
}
