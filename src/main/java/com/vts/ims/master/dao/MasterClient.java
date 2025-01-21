package com.vts.ims.master.dao;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.vts.ims.master.dto.ActionAssignDto;
import com.vts.ims.master.dto.ActiveProcurementDto;
import com.vts.ims.master.dto.CommitteeScheduleDto;
import com.vts.ims.master.dto.DivisionEmployeeDto;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmpDesignationDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.ItemReceivedDto;
import com.vts.ims.master.dto.LabMasterDto;
import com.vts.ims.master.dto.ProjectEmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;
import com.vts.ims.master.dto.SupplyOrderDto;

@FeignClient(name = "masterClient", url = "${feign_client_uri}")
public interface MasterClient {

	@PostMapping("/getLabMaster")
	List<LabMasterDto> getAllLabMaster(@RequestHeader("X-API-KEY") String apiKey);

	@PostMapping("/getDivisionMaster")
	List<DivisionMasterDto> getDivisionMaster(@RequestHeader("X-API-KEY") String apiKey);

	@PostMapping("/getDivisionMaster")
	List<DivisionMasterDto> getDivisionDetailsById(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("divisionId") long divisionId);


	@PostMapping("/getDivisionEmpsByDivId")
	List<DivisionEmployeeDto> getDivEmpListByDivId(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("divisionId") long divisionId);

	   @PostMapping("/getEmployee")
	   List<EmployeeDto> getEmployeeList(@RequestHeader("X-API-KEY") String apiKey);
	   
	   @PostMapping("/getEmployeeMaster")
	   List<EmployeeDto> getEmployeeMasterList(@RequestHeader("X-API-KEY") String apiKey);
	   
	   @PostMapping("/getEmpDesigMaster")
	   List<EmpDesignationDto> getEmpDesigMaster(@RequestHeader("X-API-KEY") String apiKey);
	   
	   @PostMapping("/getProjectMaster")
	   List<ProjectMasterDto> getProjectMasterList(@RequestHeader("X-API-KEY") String apiKey);
	   
	   @PostMapping("/getProjectMaster")
	   List<ProjectMasterDto> getProjectMasterById(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("projectId") long projectId);
	   
	   @PostMapping("/getDivisionGroupList")
	   List<DivisionGroupDto> getDivisionGroupList(@RequestHeader("X-API-KEY") String apiKey);

	   @PostMapping("/getDivisionGroup")
	   List<DivisionGroupDto> getDivisionGroup(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("groupId") long groupId);

		@PostMapping("/getDivisionEmployee")
		List<DivisionEmployeeDto> getDivisionEmpDetailsById(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("divisionEmployeeId") long divisionEmployeeId);
	
		@PostMapping("/getDivisionEmployee")
		List<DivisionEmployeeDto> getDivisionEmpDetailsById(@RequestHeader("X-API-KEY") String apiKey);
	
		@PostMapping("/getEmployee")
		List<EmployeeDto> getEmployee(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("empId") long empId);
	
	    @PostMapping("/getProjectEmployee")
	    List<ProjectEmployeeDto> getProjectEmpDetailsById(@RequestHeader("X-API-KEY") String apiKey);
	    
	    @PostMapping("/committeeScheduleList")
		List<CommitteeScheduleDto> committeeScheduleList(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("committeeType") String committeeType);
	    
	    @PostMapping("/actionAssignData")
		List<ActionAssignDto> actionAssignData(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("committeeType") String committeeType);

	    @PostMapping("/getSupplyOrderList.htm")
		List<SupplyOrderDto> getSupplyOrderList(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("labCode") String labCode);

	    @PostMapping("/getItemReceivedList.htm")
		List<ItemReceivedDto> getItemReceivedList(@RequestHeader("X-API-KEY") String apiKey);

	    @PostMapping("/getActiveProcurementList.htm")
		List<ActiveProcurementDto> getActiveProcurementList(@RequestHeader("X-API-KEY") String apiKey);













}
