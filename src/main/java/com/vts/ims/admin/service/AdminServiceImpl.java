package com.vts.ims.admin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vts.ims.admin.dto.FormDetailDto;
import com.vts.ims.admin.dto.FormModuleDto;
import com.vts.ims.admin.entity.FormDetail;
import com.vts.ims.admin.entity.FormModule;
import com.vts.ims.admin.entity.ImsFormRole;
import com.vts.ims.admin.repository.FormDetailRepo;
import com.vts.ims.admin.repository.FormModuleRepo;
import com.vts.ims.admin.repository.ImsFormRoleRepo;
import com.vts.ims.login.Login;
import com.vts.ims.login.LoginRepository;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.LoginDetailsDto;
import com.vts.ims.master.service.MasterService;
import com.vts.ims.model.LoginStamping;
import com.vts.ims.admin.repository.UserManagerRepo;
import com.vts.ims.admin.repository.AuditStampingRepo;
import com.vts.ims.admin.dto.AuditStampingDto;


@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private FormModuleRepo formModuleRepo;
	
	@Autowired
	private FormDetailRepo formDetailRepo;
	
	@Autowired
	private MasterClient masterClient;
	
	@Autowired
	MasterService  masterservice;

    @Autowired
    private AuditStampingRepo auditStampingRepo;
    
	@Autowired
	UserManagerRepo userManagerRepo;
	
	
	@Value("${x_api_key}")
	private String xApiKey;
	
	@Override
	public List<FormModuleDto> formModuleList(Long imsFormRoleId) throws Exception {
		logger.info(new Date() +" Inside formModuleList " );
		try {
			List<FormModuleDto> formModuleDtoList = new ArrayList<FormModuleDto>();
			List<FormModule> formModuleList = formModuleRepo.findDistinctFormModulesByRoleId(imsFormRoleId);
			
			formModuleList.forEach(detail -> {
				FormModuleDto formModuleDto = FormModuleDto.builder()
						.FormModuleId(detail.getFormModuleId())
						.FormModuleName(detail.getFormModuleName())
						.ModuleUrl(detail.getModuleUrl())
						.ModuleIcon(detail.getModuleIcon())
						.SerialNo(detail.getSerialNo())
						.IsActive(detail.getIsActive())
						.build();
				
				formModuleDtoList.add(formModuleDto);
			});
			
			return formModuleDtoList;
		} catch (Exception e) {
			logger.error(new Date() +" Inside formModuleList ", e );
			e.printStackTrace();
			return new ArrayList<FormModuleDto>();
		}
	}
	
	
	@Override
	public List<FormDetailDto> formModuleDetailList(Long imsFormRoleId) throws Exception {
		logger.info(new Date() +" Inside formModuleDetailList " );
		try {
			List<FormDetailDto> formDetailDtoList = new ArrayList<FormDetailDto>();
			List<FormDetail> formDetailList = formDetailRepo.findDistinctFormModulesDetailsByRoleId(imsFormRoleId);
			
			formDetailList.forEach(detail -> {
				FormDetailDto formModuleDto = FormDetailDto.builder()
						.FormModuleId(detail.getFormModuleId())
						.FormName(detail.getFormName())
						.FormUrl(detail.getFormUrl())
						.FormDispName(detail.getFormDispName())
						.FormSerialNo(detail.getFormSerialNo())
						.FormColor(detail.getFormColor())
						.ModifiedBy(detail.getModifiedBy())
						.ModifiedDate(detail.getModifiedDate())
						.IsActive(detail.getIsActive())
						.build();
				
				formDetailDtoList.add(formModuleDto);
			});
			
			return formDetailDtoList;
		} catch (Exception e) {
			logger.error(new Date() +" Inside formModuleDetailList ", e );
			e.printStackTrace();
			return new ArrayList<FormDetailDto>();
		}
	}
	
	
	//mdn connection established in below method
		@Override
		public List<LoginDetailsDto> loginDetailsList(String username) {
			 logger.info(new Date() + " AdminServiceImpl Inside method loginDetailsList");
			 List<LoginDetailsDto> list =null;
			 try {
		
				 list = masterservice.loginDetailsList(username);
				 
			 } catch (Exception e) {
		    	 logger.info(new Date() + " AdminServiceImpl Inside method loginDetailsList"+ e.getMessage());
		        return new ArrayList<>(); // Return an empty list in case of an error
		    }
			 return list;
		}
		        
		        
		
		@Override
		public List<EmployeeDto> employeeList() throws Exception {
			logger.info(new Date() + " AuditServiceImpl Inside method employeeList()");
			try {

				List<EmployeeDto> empdto=masterClient.getEmployeeList(xApiKey);
				 Comparator<EmployeeDto> comparator = Comparator
					        .comparingLong((EmployeeDto dto) -> dto.getSrNo() == 0 ? 1 : 0) 
					        .thenComparingLong(EmployeeDto::getSrNo);

					    return empdto.stream()
					                 .filter(dto -> dto.getIsActive() == 1) // Filter for isActive == 1
					                 .sorted(comparator) // Sort after filtering
					                 .collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("AuditServiceImpl Inside method getEmployelist()"+ e);
				return Collections.emptyList();
			}
		}
		

		@Override
		public long loginStampingInsert(LoginStamping Stamping)throws Exception{
			logger.info(new Date() + " AdminServiceImpl Inside method LoginStampingInsert " );
			long result = 0;
			try{
				result =  auditStampingRepo.save(Stamping).getAuditStampingId();
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" error in AdminServiceImpl Inside method LoginStampingInsert "+ e.getMessage());
			}
			return result;
		}
		
		@Override
		public long  lastLoginStampingId(long loginId)throws Exception{
			logger.info(new Date() + " AdminServiceImpl Inside method LastLoginStampingId " );
			        try {
			            Optional<Long> result = auditStampingRepo.findLastLoginStampingId(loginId);
			            return result.isPresent() ? result.get() : 0L; 
			        } catch (Exception e) {
			        	logger.error(new Date() +" error in AdminServiceImpl Inside method LastLoginStampingId "+ e.getMessage());
			            throw new Exception("Error while fetching last login stamping ID", e);
			        }
			    
		}

		
		@Override
		public long loginStampingUpdate(LoginStamping stamping)throws Exception{

			logger.info(new Date() + " AdminServiceImpl Inside method LoginStampingUpdate " );
			long result = 0;
			try{
				LoginStamping prevStampingDetails = auditStampingRepo.findByAuditStampingId(stamping.getAuditStampingId());
				prevStampingDetails.setAuditStampingId(stamping.getAuditStampingId());
				prevStampingDetails.setLogOutType(stamping.getLogOutType());
				prevStampingDetails.setLogOutDateTime(stamping.getLogOutDateTime());
				
				result =  auditStampingRepo.save(prevStampingDetails).getAuditStampingId();
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" error in AdminServiceImpl Inside method LoginStampingUpdate "+ e.getMessage());
			}
			return result;
		}

		
		
		 
		@Override
		public List<AuditStampingDto> getAuditStampinglist(AuditStampingDto stamping)throws Exception{
			
			 logger.info(new Date() + " AdminServiceImpl Inside method getAuditStampinglist " );
				List<Object[]> AuditStampinglist = null;
			 try {
			
		
			long loginId = 0;
			Long loginIdForEmpId  = userManagerRepo.findLoginIdByEmpId(Long.parseLong(stamping.getEmpId())) ;
			if(loginIdForEmpId != null) {
				loginId = loginIdForEmpId;
			}
			
			
			stamping.setLoginId(loginId);
			if(stamping.getLoginId()>=0) {
				   AuditStampinglist =  auditStampingRepo.getAuditStampingList(stamping.getLoginId(),stamping.getFromDate(),stamping.getToDate());
			   if(AuditStampinglist!=null && AuditStampinglist.size()>0) {
				   
				   return AuditStampinglist.stream()
			        		.map((resultData)  -> AuditStampingDto.builder()
			        			 .auditStampingId(resultData[0] != null ? Long.parseLong(resultData[0].toString()) : 0L)
			        			 .loginId(resultData[1] != null ? Long.parseLong(resultData[1].toString()) : 0L)
			        			 .username(resultData[2]!=null?resultData[2].toString():"")
			        		     .ipAddress(resultData[3]!=null?resultData[3].toString():"")
			                     .macAddress(resultData[4]!=null?resultData[4].toString():"")
			        			 .loginDate(resultData[5]!=null?resultData[5].toString():"")
			                     .loginDateTime(resultData[6]!=null?resultData[6].toString():"")
		                         .logOutDateTime(resultData[7]!=null?resultData[7].toString():"")
		                         .logOutType(resultData[8]!=null?resultData[8].toString():"")
		           
		                         .loginTime(resultData[9]!=null?resultData[9].toString():"")
		                         .logoutTypeDisp(resultData[10]!=null?resultData[10].toString():"")
		                         
		                     .build())
			                .collect(Collectors.toList());
				   
			   }else {
					return List.of();
			   }
			   
			}
			return List.of();
			
			 } catch (Exception e) {
		        	logger.error(new Date() +" error in AdminServiceImpl Inside method getAuditStampinglist "+ e.getMessage());
				         e.printStackTrace();
				     	return List.of();
				}
			
		
		}


		
	
	
	
}
