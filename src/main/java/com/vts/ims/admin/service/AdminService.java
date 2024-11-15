package com.vts.ims.admin.service;

import java.util.List;

import com.vts.ims.admin.dto.FormDetailDto;
import com.vts.ims.admin.dto.FormModuleDto;

public interface AdminService {
	
	public List<FormModuleDto> formModuleList(Long imsFormRoleId) throws Exception;
	public List<FormDetailDto> formModuleDetailList(Long imsFormRoleId) throws Exception;
	
}
