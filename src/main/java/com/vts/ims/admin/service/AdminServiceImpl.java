package com.vts.ims.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ims.admin.dto.FormDetailDto;
import com.vts.ims.admin.dto.FormModuleDto;
import com.vts.ims.admin.entity.FormDetail;
import com.vts.ims.admin.entity.FormModule;
import com.vts.ims.admin.repository.FormDetailRepo;
import com.vts.ims.admin.repository.FormModuleRepo;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private FormModuleRepo formModuleRepo;
	
	@Autowired
	private FormDetailRepo formDetailRepo;
	
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
			logger.info(new Date() +" Inside formModuleList ", e );
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
			logger.info(new Date() +" Inside formModuleDetailList ", e );
			e.printStackTrace();
			return new ArrayList<FormDetailDto>();
		}
	}
	
}
