package com.vts.ims.admin.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vts.ims.admin.dto.FormDetailDto;
import com.vts.ims.admin.dto.FormModuleDto;
import com.vts.ims.admin.service.AdminService;


@RestController
@CrossOrigin("*")
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private AdminService service;
	
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
	
}
