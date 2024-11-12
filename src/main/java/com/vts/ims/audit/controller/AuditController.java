package com.vts.ims.audit.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.service.AuditService;
import com.vts.ims.master.dto.EmployeeDto;



@CrossOrigin("*")
@RestController
public class AuditController {

	private static final Logger logger = LogManager.getLogger(AuditController.class);
	
	@Value("${appStorage}")
	private String storageDrive ;
	
	@Autowired
	AuditService auditService;
	
	
	@PostMapping(value = "/audit-list", produces = "application/json")
	public List<AuditorDto> auditList(@RequestHeader String username) throws Exception {
		List<AuditorDto> dto=auditService.getAditList();
		return dto;
	}
	
	@PostMapping(value = "/get-employee-list", produces = "application/json")
	public List<EmployeeDto> getEmployelist(@RequestHeader String username) throws Exception {
		List<EmployeeDto> dto=auditService.getEmployelist();
		return dto;
	}
	
	@PostMapping(value = "/insert-auditor-employees", produces = "application/json")
	public String insertAuditorEmployees(@RequestHeader String username, @RequestBody AuditorDto auditordto) throws Exception {
		logger.info(new Date() + " Inside insert-selected-employees" );
		String message="";
		try {
			 String[] empIds = auditordto.getEmpIds();
			 long insertAuditor=auditService.insertAuditor(empIds,username);
			 message=insertAuditor > 0 ? "Auditor Added SuccessFully" : "Auditor Added unsuccessful";
		} catch (Exception e) {
			 logger.error(new Date() +"error in insert-selected-employees"+ e.getMessage());
			 e.printStackTrace();
		}
		return message;
	}
	
}
