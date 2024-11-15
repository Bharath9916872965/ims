package com.vts.ims.master.service;

import com.vts.ims.master.dto.DocTemplateAttributesDto;
import com.vts.ims.master.dto.LabMasterDto;

public interface MasterService {

	public LabMasterDto labDetailsList(String username) throws Exception;
	public String LogoImage()throws Exception;
	public DocTemplateAttributesDto getDocTemplateAttributesDto() throws Exception;
	
	
}
