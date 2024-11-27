package com.vts.ims.admin.controller;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vts.ims.admin.dto.FormDetailDto;
import com.vts.ims.admin.dto.FormModuleDto;
import com.vts.ims.admin.service.AdminService;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.LoginDetailsDto;
import com.vts.ims.login.Login;
import com.vts.ims.model.LoginStamping;
import com.vts.ims.login.LoginRepository;

import jakarta.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.vts.ims.admin.dto.AuditStampingDto;


@RestController
@CrossOrigin("*")
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private AdminService service;
	
	@Autowired
	LoginRepository loginRepo;
	
	
	@RequestMapping (value="header-module", method=RequestMethod.POST,produces="application/json")
	public List<FormModuleDto> headerModule(@RequestBody Long imsFormRoleId) throws Exception {
		logger.info(new Date() + " Inside header-module ");
		return service.formModuleList(imsFormRoleId);
	}
	
	
	@RequestMapping (value="header-detail", method=RequestMethod.POST,produces="application/json")
	public List<FormDetailDto> headerDetail(@RequestBody Long imsFormRoleId) throws Exception {
		logger.info(new Date() + " Inside header-detail ");
		return service.formModuleDetailList(imsFormRoleId);
	}
	
	
	

	@PostMapping(value="/login/{user}", produces="application/json")
	public ResponseEntity<List<LoginDetailsDto>> getLoginDetails(
			@PathVariable("user") String user,
			@RequestHeader("Username") String username) throws Exception {
		
		logger.info(new Date() +" Inside login data fetch ");
		List<LoginDetailsDto> details = null;
		try {
		if (username == null || !username.equals(user)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		details = service.loginDetailsList(user);
		
	    } catch (Exception e) {
		  logger.error(new Date() +" error Inside login data fetch ");
	         e.printStackTrace();
	     }
		return ResponseEntity.ok(details);
	}
	  
	
	
	
	@PostMapping(value = "/employee-list", produces = "application/json")
	public ResponseEntity<List<EmployeeDto>> getEmployelist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getEmployelist" );
			List<EmployeeDto> dto=service.employeeList();
			return new ResponseEntity<List<EmployeeDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getEmployelist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	 /* ---------------------------AuditStampinglist------------------------------ */
   
	@PostMapping(value="custom-audit-stamping-login" ,produces="application/json")
	public String logIn(@RequestBody String username, Authentication authentication, HttpServletRequest request)throws Exception {
		
		logger.info(new Date() +" Inside custom-audit-stamping-login " +authentication.getName());
		long result=0;
		
		
		username = username.replace("\"", "");
		String IpAddress="Not Available";

     		try{
     			IpAddress = request.getRemoteAddr();
     		 
	     		if("0:0:0:0:0:0:0:1".equalsIgnoreCase(IpAddress))
	     		{	     			
	     			InetAddress ip = InetAddress.getLocalHost();
	     			IpAddress= ip.getHostAddress();
	     		}
     		
     		}
     		catch(Exception e)
     		{
     		IpAddress="Not Available";	
     		e.printStackTrace();	
     		}
     		try{
     			Login login=loginRepo.findByUsername(username);
     			LoginStamping stamping=new LoginStamping();
		        stamping.setLoginId(login.getLoginId());
		        stamping.setLoginDate(new java.sql.Date(new Date().getTime()));
		        stamping.setUsername(login.getUsername());
		        stamping.setIpAddress(IpAddress);
		        stamping.setLoginDateTime(LocalDateTime.now());
		        result = service.loginStampingInsert(stamping);
     		}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" error in custom-audit-stamping-login "+ e.getMessage());
			}
		
	
	 		return String.valueOf(result);
		
	}	
	
	
	 @PostMapping(value = "custom-audit-stamping-logout", produces = "application/json")
	 public String logout(@RequestBody JsonNode requestBody, Authentication authentication) throws Exception {
	     logger.info(new Date() + " Inside custom-auditStamping-logout " + authentication.getName());
	     long result = 0;
	     long loginid = 0;

	     String username = requestBody.get("username").asText();
	     String logoutType = requestBody.get("logoutType").asText();
	     Login login = loginRepo.findByUsername(username);
	     loginid = login.getLoginId();

	     try {
	         if (loginid > 0) {
	             LoginStamping stamping = new LoginStamping();
	             stamping.setAuditStampingId(service.lastLoginStampingId(loginid));
	             stamping.setLogOutType(logoutType);
	             stamping.setLogOutDateTime(LocalDateTime.now());
	             result = service.loginStampingUpdate(stamping);
	         }
	     } catch (Exception e) {
	         e.printStackTrace();
	         logger.error(new Date() +" error in custom-audit-stamping-logout "+ e.getMessage());
	     }
	     return String.valueOf(result);
	 }

	
	
	
	
	@PostMapping(value="audit-stamping-list",produces="application/json")
	 public ResponseEntity<List<AuditStampingDto>> AuditStampingList(@RequestBody AuditStampingDto stamping, @RequestHeader  String username)throws Exception{
    	logger.info(new Date() + " Inside audit-stamping-list " );
    	   List<AuditStampingDto> list = null;
    	try {
    		list =  service.getAuditStampinglist(stamping);
    	} catch (Exception e) {
			 logger.error(new Date() +"error in audit-stamping-list "+ e.getMessage());
			 e.printStackTrace();
		}
		 return new ResponseEntity<>(list, HttpStatus.OK);

	 }

	
	
	
	
}
