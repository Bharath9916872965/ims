package com.vts.ims.master.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vts.ims.master.dto.ActionAssignDto;
import com.vts.ims.master.dto.CommitteeScheduleDto;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.DocTemplateAttributesDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.LabMasterDto;
import com.vts.ims.master.dto.ProjectMasterDto;
import com.vts.ims.master.dto.UserDetailsDto;
import com.vts.ims.master.service.MasterService;



@CrossOrigin("*")
@RestController
public class MasterController {


	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MasterService service;

	@Value("${appStorage}")
	private String storageDrive ;

	@GetMapping(value ="/lab-details" , produces="application/json")
	public ResponseEntity<LabMasterDto> LabMasterList(@RequestHeader  String username) throws Exception {
		logger.info("{} Inside lab-details");
		LabMasterDto dto= null;
		try {
			dto=service.labDetailsList(username);
		} catch (Exception e) {
			logger.error(" error in lab-details ");
			e.printStackTrace();
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/lab-logo" , produces ="application/json")
	public ResponseEntity<String> QamLabLogo()throws Exception {

		logger.info("{} Inside lab-details");
		
		String result = service.LogoImage();
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
    @RequestMapping(value = "/drdo-logo" , method = RequestMethod.POST , produces ="application/json")
    public ResponseEntity<String> getDrdoImage(@RequestHeader  String username)throws Exception {
    	logger.info(" Inside drdo-logo " +username);
    	String result = service.getDrdoLogo();
    	if (result != null) {
    		return new ResponseEntity<>(result, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    	}
    }
	
	
	@PostMapping(value = "/get-DocTemplateAttributes" , produces ="application/json")
	public DocTemplateAttributesDto getDocTemplateAttributesDto()throws Exception {
		
		logger.info("{} Inside get-DocTemplateAttributes() ");
		
		return service.getDocTemplateAttributesDto();
	}
	
    @PostMapping(value = "/get-emp-details" , produces = "application/json")
    public ResponseEntity<UserDetailsDto> GetEmpDetails( @RequestHeader String username)throws Exception {			    	
    	
    	UserDetailsDto result=service.GetEmpDetails(username);
	   	if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
    }

    @PostMapping(value = "/get-employee-byid", produces = "application/json")
    public ResponseEntity<List<EmployeeDto>> getemployeebyid(@RequestBody long empId, @RequestHeader  String username) throws Exception {
		logger.info("{} Inside getemployeebyid");
		List<EmployeeDto> dto= null;
		try {
			dto=service.getemployeebyid(empId);
		} catch (Exception e) {
			logger.error(" error in getemployeebyid ");
			e.printStackTrace();
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

    
    @PostMapping(value = "/get-committee-schedule-list" , produces = "application/json")
    public ResponseEntity<List<CommitteeScheduleDto>> GetComitteeScheduleList( @RequestHeader String username,@RequestBody Map<String, String> requestBody)throws Exception {		
    	logger.info( " Inside get-committee-schedule-list" );
    	try {
			String committeeType = requestBody.get("committeeType");
			List<CommitteeScheduleDto> result=service.GetComitteeScheduleList(committeeType);
			return new ResponseEntity<List<CommitteeScheduleDto>>( result,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching get-committee-schedule-list: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
    }
    
    
    @PostMapping(value = "/get-schedule-action-assign-list" , produces = "application/json")
    public ResponseEntity<List<ActionAssignDto>> GetCommitteeScheduleActionAssignList( @RequestHeader String username,@RequestBody Map<String, String> requestBody)throws Exception {			    	
    	try {
    		String committeeType = requestBody.get("committeeType");
			logger.info( " Inside GetCommitteeScheduleActionAssignList" );
			List<ActionAssignDto> result=service.GetCommitteeScheduleActionAssignList(committeeType);
			return new ResponseEntity<List<ActionAssignDto>>( result,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching GetCommitteeScheduleActionAssignList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
    }

	@RequestMapping(value = "/division-master-list", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<DivisionMasterDto>> getDivisionMasterList(@RequestHeader  String username) throws Exception {
		logger.info("{} Inside division-master-list");
		List<DivisionMasterDto> list = null;
		try {
			list = service.getDivisionMasterList(username);
		} catch (Exception e) {
			logger.error(" error in  division-master-list ");
			e.printStackTrace();
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/division-group-master-list", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<DivisionGroupDto>> getDivisionGroupList(@RequestHeader  String username) throws Exception{
		logger.info( " Inside division-group-master-list " );
		List<DivisionGroupDto> list= null;
		try {
			list=service.getDivisonGroupList(username);

		} catch (Exception e) {
			logger.error(" error in division-group-master-list ");
			e.printStackTrace();
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	@GetMapping(value ="project-master-active-list" , produces="application/json")
	public ResponseEntity<List<ProjectMasterDto>> projectMainList(@RequestHeader  String username) throws Exception{
		logger.info("{} Inside project-master-list");
		List<ProjectMasterDto> list=null;
		try {
			list=service.getprojectMasterList(username);
		} catch (Exception e) {
			logger.error("project-master-list ");
			e.printStackTrace();
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
