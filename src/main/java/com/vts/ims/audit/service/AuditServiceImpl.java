package com.vts.ims.audit.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.model.Auditor;
import com.vts.ims.audit.repository.AuditorRepository;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.EmployeeDto;


@Service
public class AuditServiceImpl implements AuditService{

	private static final Logger logger=LogManager.getLogger(AuditServiceImpl.class);
	
	@Autowired
	AuditorRepository auditRepository;
	
	@Autowired
	private MasterClient masterClient;
	
	@Override
	public List<AuditorDto> getAditList() throws Exception {
		try {
			List<Auditor> auditor=auditRepository.findAll();
			List<AuditorDto> finalDto = new ArrayList<AuditorDto>();
			auditor.forEach(obj ->{
			AuditorDto auditorDto= new AuditorDto();
			EmployeeDto employeeDto=masterClient.getEmployee("VTS", obj.getEmpId()).get(0);
			auditorDto.setEmpId(obj.getEmpId());
			auditorDto.setEmpName(employeeDto.getEmpName());
			auditorDto.setDesignation(employeeDto.getEmpDesigName());
			auditorDto.setDivisionName(employeeDto.getEmpDivCode());
			finalDto.add(auditorDto);
			});
			return finalDto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Inside ");
			return new ArrayList<AuditorDto>();
		}
	}
	
	
	@Override
	public List<EmployeeDto> getEmployelist() throws Exception {
		try {
			List<EmployeeDto> empdto=masterClient.getEmployeeList("VTS");
			return empdto;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		
	}
	
	
	@Override
	public long insertAuditor(String[] empIds,String username) throws Exception {
		long result=0;
		try {
			if(empIds!=null && empIds.length>0) {
				for(int i=0;i<empIds.length;i++) {
					Auditor model=new Auditor();
					model.setEmpId(Long.parseLong(empIds[i]));
					model.setCreatedBy(username);
					model.setCreatedDate(LocalDateTime.now());
					model.setIsActive(1);
					result=auditRepository.save(model).getAuditorId();
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}
}
