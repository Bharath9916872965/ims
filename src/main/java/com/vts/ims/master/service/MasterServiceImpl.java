package com.vts.ims.master.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vts.ims.admin.entity.ImsFormRole;
import com.vts.ims.admin.repository.ImsFormRoleRepo;
import com.vts.ims.login.Login;
import com.vts.ims.login.LoginRepository;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.DocTemplateAttributesDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.LabMasterDto;
import com.vts.ims.master.dto.LoginDetailsDto;
import com.vts.ims.master.dto.UserDetailsDto;
import com.vts.ims.master.entity.DocTemplateAttributes;
import com.vts.ims.master.repository.DocTemplateAttributesRepo;



@Service
public class MasterServiceImpl implements MasterService {

	private static final Logger logger = LoggerFactory.getLogger(MasterService.class);
	                                                                
	@Value("${appStorage}")
	private String storageDrive;
	
	@Value("${x_api_key}")
	private String xApiKey;
	
	@Autowired
	private MasterClient masterClient;
	
	@Autowired
	LoginRepository loginRepo;
	
	
	@Autowired
	ImsFormRoleRepo imsFormRoleRepo;
	
	@Autowired
	DocTemplateAttributesRepo docTemplateAttributesRepo;
	
	@Override
	public LabMasterDto labDetailsList(String username) throws Exception {
		 logger.info(new Date() + " MasterServiceImpl Inside method labDetailsList");
		 try {
		List<LoginDetailsDto> loginDtoList = loginDetailsList(username);
			String labCode = loginDtoList.stream()
					    .filter(dto -> dto.getUsername().equals(username)) 
					    .map(LoginDetailsDto::getLabCode)                   
					    .findFirst()                                        
					    .orElse(null); 
	        List<LabMasterDto> allLabMasterData = masterClient.getAllLabMaster(xApiKey);
	        return allLabMasterData.stream()
	            .filter(labMasterDto -> labMasterDto.getLabCode().equals(labCode))
	            .findFirst()
	            .orElse(null); 
		 } catch (Exception e) {
			 logger.info(new Date() + " MasterServiceImpl Inside method labDetailsList"+ e.getMessage());
	        return null;
	    }
	}
	
	
	@Override
	public List<LoginDetailsDto> loginDetailsList(String username) {
		 logger.info(new Date() + " MasterServiceImpl Inside method loginDetailsList");
		try {
			
	        // Fetch login data using the username
	        Login loginData = loginRepo.findByUsername(username);
	        if (loginData == null) {
	            throw new Exception("User not found!");
	        }
	        
	        // Fetch employee data from the master client
	        List<EmployeeDto> empData = masterClient.getEmployee(xApiKey, loginData.getEmpId());
	        
	        if (!empData.isEmpty()) {
	            EmployeeDto eDto = empData.get(0); // Get the first element from the employee data list

	         // Fetch role data using the form role ID from login data
		        ImsFormRole formRoleData = imsFormRoleRepo.findByImsFormRoleId(loginData.getImsFormRoleId());

	     
	            // Map the data to LoginDetailsDto
	            return List.of(
	                    LoginDetailsDto.builder()
	                            .loginId(loginData.getLoginId())
	                            .username(loginData.getUsername())
	                            .empId(loginData.getEmpId())
	                            .empNo(eDto.getEmpNo())
	                            .empName(eDto.getEmpName())
	                            .empDesigCode(eDto.getEmpDesigCode()) 
	                            .imsFormRoleId(loginData.getImsFormRoleId())
	                            .photo(eDto.getPhoto())
	                            .divisionId(loginData.getDivisionId())
	                            .loginType(loginData.getLoginType())
	                            .formRoleName(formRoleData.getFormRoleName()) 
	                            .labCode(eDto.getLabCode())
	                            .empDesigName(eDto.getEmpDesigName())
	                            .build()
	            );
	        } else {
	            throw new Exception("Employee data not found for the given employee ID");
	        }
	
	    } catch (Exception e) {
	    	 logger.info(new Date() + " MasterServiceImpl Inside method loginDetailsList"+ e.getMessage());
	        return new ArrayList<>(); 
	    }
	}
	
	@Override
	public String LogoImage()throws Exception {
		String logoimage=null;
		try {
			
			Path filePath=null;
			
				  filePath = Paths.get(storageDrive,"Logo","lablogo.png");
			File f = filePath.toFile();
			if(f.exists() && !f.isDirectory()) { 
			logoimage=encodeFileToBase64Binary(f);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return logoimage;
	}
	
	
	private static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
           encodedfile = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
           fileInputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return encodedfile;
        } catch (IOException e) {
            e.printStackTrace();
            return encodedfile;
        }

        return encodedfile;
    }
	
	
	@Override
	public String getDrdoLogo()throws Exception {
		String logoimage=null;
		try {

			Path filePath=null;

			filePath = Paths.get(storageDrive,"Logo","drdo.png");
			File f = filePath.toFile();
			if(f.exists() && !f.isDirectory()) { 
				logoimage=encodeFileToBase64Binary(f);
			}
		}	catch (Exception e) {
			e.printStackTrace();
		}
		return logoimage;
	}
	
	
	@Override
	public DocTemplateAttributesDto getDocTemplateAttributesDto() throws Exception {
		logger.info(new Date() + " Inside getDocTemplateAttributesDto() " );
		try {
			DocTemplateAttributesDto qmsQmChaptersDto = DocTemplateAttributesDto.builder().build();
			Optional<DocTemplateAttributes> optionalChapters = docTemplateAttributesRepo.findById(1l);
			if(optionalChapters.isPresent()) {
				DocTemplateAttributes chapter = optionalChapters.get();
						qmsQmChaptersDto = DocTemplateAttributesDto.builder()
						.AttributeId(chapter.getAttributeId())
						.HeaderFontSize(chapter.getHeaderFontSize())
						.SubHeaderFontsize(chapter.getSubHeaderFontsize())
						.SuperHeaderFontsize(chapter.getSuperHeaderFontsize())
						.ParaFontSize(chapter.getParaFontSize())
						.FontFamily(chapter.getFontFamily())
						.RestrictionOnUse(chapter.getRestrictionOnUse())
						.CreatedBy(chapter.getCreatedBy())
						.CreatedDate(chapter.getCreatedDate())
						.ModifiedBy(chapter.getModifiedBy())
						.ModifiedDate(chapter.getModifiedDate())
						.IsActive(chapter.getIsActive())
						.build();
				
			}
			return qmsQmChaptersDto;
		} catch (Exception e) {
			logger.error(new Date() + " Inside getDocTemplateAttributesDto() "+ e );
			e.printStackTrace();
			return DocTemplateAttributesDto.builder().build();
		}
	}
	
	@Override
	public UserDetailsDto GetEmpDetails(String  username)throws Exception
	{
	try {
		 Login login=loginRepo.findByUsername(username);
		 if(login !=null) {
			 return UserDetailsDto.builder()
		 			  .loginId(login.getLoginId())	  
		 			  .username(login.getUsername())	  
		 			  .empId(login.getEmpId())	  
		 			  .divisionId(login.getDivisionId())	  
		 			  .imsFormRoleId(login.getImsFormRoleId())	  
		 			  .build();	
		 }else {
			 return null;
		 }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
