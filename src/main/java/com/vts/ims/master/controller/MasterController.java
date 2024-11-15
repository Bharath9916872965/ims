package com.vts.ims.master.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.vts.ims.master.dto.DocTemplateAttributesDto;
import com.vts.ims.master.dto.LabMasterDto;
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
		logger.info("{} Inside lab-details", new Date());
		LabMasterDto dto= null;
		try {
			dto=service.labDetailsList(username);
		} catch (Exception e) {
			logger.error(new Date() +" error in lab-details ");
			e.printStackTrace();
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/lab-logo" , produces ="application/json")
	public ResponseEntity<String> QamLabLogo()throws Exception {

		logger.info("{} Inside lab-details", new Date());
		
		String result = service.LogoImage();
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(value = "/get-DocTemplateAttributes" , produces ="application/json")
	public DocTemplateAttributesDto getDocTemplateAttributesDto()throws Exception {
		
		logger.info("{} Inside get-DocTemplateAttributes() ", new Date());
		
		return service.getDocTemplateAttributesDto();
	}


}
