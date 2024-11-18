package com.vts.ims.audit.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vts.ims.audit.dto.AuditeeDto;
import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.dto.IqaDto;
import com.vts.ims.audit.model.AuditTeam;
import com.vts.ims.audit.service.AuditService;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;



@CrossOrigin("*")
@RestController
public class AuditController {

	private static final Logger logger = LogManager.getLogger(AuditController.class);
	
	@Value("${appStorage}")
	private String storageDrive ;
	
	@Autowired
	AuditService auditService;
	
	
	@PostMapping(value = "/audit-list", produces = "application/json")
	public ResponseEntity<List<AuditorDto>> auditList(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside auditList" );
			List<AuditorDto> dto=auditService.getAuditList();
			return new ResponseEntity<List<AuditorDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching auditList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-employee-list", produces = "application/json")
	public ResponseEntity<List<EmployeeDto>> getEmployelist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getEmployelist" );
			List<EmployeeDto> dto=auditService.getEmployelist();
			return new ResponseEntity<List<EmployeeDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getEmployelist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/insert-auditor-employees", produces = "application/json")
	public ResponseEntity<String> insertAuditorEmployees(@RequestHeader String username, @RequestBody String[] empIds) throws Exception {
		try {
			logger.info(new Date() + " Inside insert-selected-employees" );
			 long insertAuditor=auditService.insertAuditor(empIds,username);
			 if(insertAuditor > 0) {
				 return new ResponseEntity<String>("200" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error(new Date() +"error in insert-selected-employees"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	
	@RequestMapping (value="/auditor-inactive", method=RequestMethod.POST,produces="application/json")
    public ResponseEntity<String> auditorInactive(@RequestBody AuditorDto auditordto, @RequestHeader  String username) throws Exception{
   		 try {
   			logger.info("{} Inside auditor-inactive");
   			long result=auditService.updateAuditor(auditordto,username);
		    if(result > 0) {
		    	return new ResponseEntity<String>("200" , HttpStatus.OK);
		    }else {
		    	return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
		    }
   		 } catch (Exception e) {
  			  logger.error(new Date() +" error in auditor-inactive");
  		         e.printStackTrace();
  		       return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
  		}
	}
	
	
	@PostMapping(value = "/iqa-list", produces = "application/json")
	public ResponseEntity<List<IqaDto>> iqalist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside iqalist" );
			List<IqaDto> dto=auditService.getIqaList();
			return new ResponseEntity<List<IqaDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching iqalist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/insert-iqa", produces = "application/json")
	public ResponseEntity<String> insertIqa(@RequestHeader String username, @RequestBody IqaDto iqadto) throws Exception {
		try {
			logger.info(new Date() + " Inside insert-iqa" );
			long insertIqa=auditService.insertIqa(iqadto,username);
			 if(insertIqa > 0) {
				 return new ResponseEntity<String>("200" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error(new Date() +"error in insert-iqa"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	
	@PostMapping(value = "/auditee-list", produces = "application/json")
	public ResponseEntity<List<AuditeeDto>> auditeelist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside auditeelist" );
			List<AuditeeDto> dto=auditService.getAuditeeList();
			return new ResponseEntity<List<AuditeeDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching auditeelist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-division-list", produces = "application/json")
	public ResponseEntity<List<DivisionMasterDto>> getDivisionlist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getDivisionlist" );
			List<DivisionMasterDto> dto=auditService.getDivisionMaster();
			return new ResponseEntity<List<DivisionMasterDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getDivisionlist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-division-group-list", produces = "application/json")
	public ResponseEntity<List<DivisionGroupDto>> getDivisionGrouplist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getDivisionGrouplist" );
			List<DivisionGroupDto> dto=auditService.getDivisionGroupList();
			return new ResponseEntity<List<DivisionGroupDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getDivisionGrouplist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-project-list", produces = "application/json")
	public ResponseEntity<List<ProjectMasterDto>> getProjectlist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getProjectlist" );
			List<ProjectMasterDto> dto=auditService.getProjectMasterList();
			return new ResponseEntity<List<ProjectMasterDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getProjectlist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@PostMapping(value = "/auditee-insert", produces = "application/json")
	public ResponseEntity<String> auditeeinsert(@RequestHeader String username, @RequestBody AuditeeDto auditeedto) throws Exception {
		try {
			logger.info(new Date() + " Inside auditeeinsert" );
			System.out.println("auditeedto:"+auditeedto);
			long insertAuditee=auditService.insertAuditee(auditeedto,username);
			 if(insertAuditee > 0) {
				 return new ResponseEntity<String>("200" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error(new Date() +"error in auditeeinsert"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	
	@RequestMapping (value="/auditee-inactive", method=RequestMethod.POST,produces="application/json")
    public ResponseEntity<String> auditeeinactive(@RequestBody String auditeeId, @RequestHeader  String username) throws Exception{
   		 try {
   			logger.info("{} Inside auditee-inactive");
   			long result=auditService.updateAuditee(auditeeId,username);
		    if(result > 0) {
		    	return new ResponseEntity<String>("200" , HttpStatus.OK);
		    }else {
		    	return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
		    }
   		 } catch (Exception e) {
  			  logger.error(new Date() +" error in auditee-inactive");
  		         e.printStackTrace();
  		       return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
  		}
	}
	
	
	@PostMapping(value = "/get-team-list", produces = "application/json")
	public ResponseEntity<List<AuditTeam>> getTeamList(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getTeamList" );
			List<AuditTeam> dto=auditService.getTeamList();
			return new ResponseEntity<List<AuditTeam>>(dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getTeamList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
