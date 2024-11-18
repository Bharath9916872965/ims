package com.vts.ims.master.dao;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.vts.ims.master.dto.DivisionEmployeeDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmpDesignationDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.LabMasterDto;

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

	@PostMapping("/getDivisionEmployee")
	List<DivisionEmployeeDto> getDivisionEmpDetailsById(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("divisionEmployeeId") long divisionEmployeeId);

	@PostMapping("/getDivisionEmployee")
	List<DivisionEmployeeDto> getDivisionEmpDetailsById(@RequestHeader("X-API-KEY") String apiKey);


	@PostMapping("/getEmployee")
	List<EmployeeDto> getEmployee(@RequestHeader("X-API-KEY") String apiKey, @RequestParam("empId") long empId);

	@PostMapping("/getEmployee")
	List<EmployeeDto> getEmployeeList(@RequestHeader("X-API-KEY") String apiKey);

	@PostMapping("/getEmployeeMaster")
	List<EmployeeDto> getEmployeeMasterList(@RequestHeader("X-API-KEY") String apiKey);

	@PostMapping("/getEmpDesigMaster")
	List<EmpDesignationDto> getEmpDesigMaster(@RequestHeader("X-API-KEY") String apiKey);











}
