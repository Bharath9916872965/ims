package com.vts.ims.master.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.vts.ims.master.dto.*;
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
								.title(eDto.getTitle())
								.salutation(eDto.getSalutation())
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
		 Object[] login = loginRepo.getLoginDetails(username).get(0);
		 
		 if(login !=null) {
				EmployeeDto employeeLogIn = masterClient.getEmployee(xApiKey,Long.parseLong(login[2].toString())).get(0);
				List<DivisionMasterDto> divisionDto = masterClient.getDivisionDetailsById(xApiKey,Long.parseLong(login[3].toString()));
				return UserDetailsDto.builder()
		 			  .loginId(Long.parseLong(login[0].toString()))	  
		 			  .username(login[1].toString())	    
		 			  .empId(Long.parseLong(login[2].toString()))	  
		 			  .divisionId(Long.parseLong(login[3].toString()))	
		 			  .groupId(divisionDto.isEmpty() ? 0 : divisionDto.get(0).getGroupId())
		 			  .imsFormRoleId(Long.parseLong(login[4].toString()))	
		 			  .roleName(login[5].toString())	
		 			  .labCode(employeeLogIn.getLabCode())	
		 			  .build();	
		 }else {
			 return null;
		 }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public List<EmployeeDto> getemployeebyid(long empId) throws ExecutionException {
		logger.info(new Date() + " MasterServiceImpl Inside method getemployeebyid");
		 try {
			 List<EmployeeDto> getEmployee=masterClient.getEmployee(xApiKey,empId);
			 return getEmployee;
		 } catch (Exception e) {
			 logger.info(new Date() + " MasterServiceImpl Inside method getemployeebyid"+ e.getMessage());
	        return null;
	    }
	}
	
	
	@Override
	public List<CommitteeScheduleDto> GetComitteeScheduleList(String committeeType) throws Exception {
		logger.info(new Date() + " MasterServiceImpl Inside method GetComitteeScheduleList");
		 try {
			 List<CommitteeScheduleDto> getcommitteSchedule=masterClient.committeeScheduleList(xApiKey,committeeType);
			 return getcommitteSchedule;
		 } catch (Exception e) {
			 logger.info(new Date() + " MasterServiceImpl Inside method GetComitteeScheduleList"+ e.getMessage());
	        return null;
	    }
	}
	
	@Override
	public List<ActionAssignDto> GetCommitteeScheduleActionAssignList(String committeeType) throws Exception {
		logger.info(new Date() + " MasterServiceImpl Inside method GetCommitteeScheduleActionAssignList");
		 try {
			 List<ActionAssignDto> GetCommitteeScheduleActionAssignList=masterClient.actionAssignData(xApiKey,committeeType);
			 return GetCommitteeScheduleActionAssignList;
		 } catch (Exception e) {
			 logger.info(new Date() + " MasterServiceImpl Inside method GetCommitteeScheduleActionAssignList"+ e.getMessage());
	        return null;
	    }
	}


	@Override
	public List<DivisionMasterDto> getDivisionMasterList(String username) throws Exception {
		logger.info(new Date() + " MasterServiceImpl Inside method getDivisionMasterList");
		try {
			List<LoginDetailsDto> loginDtoList = loginDetailsList(username);
			// Extract labCode safely
			String labCode = loginDtoList.stream()
					.filter(dto -> dto.getUsername().equals(username))
					.map(LoginDetailsDto::getLabCode)
					.findFirst()
					.orElse(null);

			List<DivisionMasterDto> divisionMasterList = masterClient.getDivisionMaster(xApiKey);

			List<EmployeeDto> employeeList = masterClient.getEmployeeList(xApiKey);

			// Create a set of active division head IDs based on the lab code
			Set<Long> divisionHeadIds = (divisionMasterList != null ? divisionMasterList.stream()
					.filter(project -> project.getIsActive() == 1 && labCode != null && labCode.equals(project.getLabCode()))
					.map(DivisionMasterDto::getDivisionHeadId)
					.collect(Collectors.toSet()) : Collections.emptySet());

			// Create a mapping of employee IDs to employee info
			Map<Long, EmpInfoDto> empIdToInfoMap = (employeeList != null ? employeeList.stream()
					.filter(emp -> divisionHeadIds.contains(emp.getEmpId()))
					.collect(Collectors.toMap(
							EmployeeDto::getEmpId,
							emp -> new EmpInfoDto(emp.getEmpName(), emp.getEmpDesigName()),
							(existing, replacement) -> existing // Handle duplicates if necessary
					)) : Collections.emptyMap());


			if (divisionMasterList != null && !divisionMasterList.isEmpty()) {
				return divisionMasterList.stream()
						.filter(dto -> dto.getIsActive() == 1) // Filter isActive == 1
						.filter(dto -> dto.getLabCode() != null && labCode.equals(dto.getLabCode())) // Filter by labCode

						.sorted(Comparator.nullsLast(
								Comparator.comparing(DivisionMasterDto::getDivisionCode, Comparator.nullsLast(Comparator.naturalOrder()))
						))

						.map(divData -> DivisionMasterDto.builder()

								.divisionId(divData.getDivisionId())
								.labCode(divData.getLabCode())
								.divisionCode(divData.getDivisionCode())
								.divisionName(divData.getDivisionName())
								.divisionHeadId(divData.getDivisionHeadId())
								.groupId(divData.getGroupId())
								.isActive(divData.getIsActive())
								.divGroupName(divData.getDivGroupName())
								.divHeadName(empIdToInfoMap.getOrDefault(divData.getDivisionHeadId(), new EmpInfoDto("", "")).getEmpName())
								.divHeadDesig(empIdToInfoMap.getOrDefault(divData.getDivisionHeadId(), new EmpInfoDto("", "")).getEmpDesigCode())
								.build())
						.collect(Collectors.toList());
			} else {
				return List.of();
			}
		} catch (Exception e) {
			logger.info(new Date() + " MasterServiceImpl Inside method getDivisionMasterList"+ e.getMessage());
			return null;
		}
	}

	@Override
	public List<DivisionGroupDto> getDivisonGroupList(String username) throws Exception {
		logger.info(new Date() + " MasterServiceImpl Inside method getDivisonGroupList");
		try {
			List<LoginDetailsDto> loginDtoList = loginDetailsList(username);
			// Extract labCode safely
			String labCode = loginDtoList.stream()
					.filter(dto -> dto.getUsername().equals(username))
					.map(LoginDetailsDto::getLabCode)
					.findFirst()
					.orElse(null);

			// List<Object[]> rawResults = divisionGroupRepository.getDivisionGroupListRaw(labCode);
			List<DivisionGroupDto> divisionGroupList  = masterClient.getDivisionGroupList(xApiKey);
			// Filter the list based on isActive and labCode
			List<DivisionGroupDto> filteredDivisionGroupList = divisionGroupList.stream()
					.filter(dto -> dto.getIsActive() == 1) // isActive returns an int
					.filter(dto -> dto.getLabCode() != null && dto.getLabCode().equals(labCode))
					.collect(Collectors.toList());

			// Create a set of active division head IDs based on the lab code
			Set<Long> groupHeadIds = (filteredDivisionGroupList != null ? filteredDivisionGroupList.stream()
					.map(DivisionGroupDto::getGroupHeadId)
					.collect(Collectors.toSet()) : Collections.emptySet());

			List<EmployeeDto> employeeList = masterClient.getEmployeeList(xApiKey);
			// Create a mapping of employee IDs to employee info
			Map<Long, EmpInfoDto> empIdToInfoMap = (employeeList != null ? employeeList.stream()
					.filter(emp -> groupHeadIds.contains(emp.getEmpId()))
					.collect(Collectors.toMap(
							EmployeeDto::getEmpId,
							emp -> new EmpInfoDto(emp.getEmpName(), emp.getEmpDesigName()),
							(existing, replacement) -> existing // Handle duplicates if necessary
					)) : Collections.emptyMap());

			List<DivisionGroupDto> finalDivisionGroupList = filteredDivisionGroupList.stream()
					.map(dto -> DivisionGroupDto.builder()
							.groupId(dto.getGroupId())
							.groupCode(dto.getGroupCode())
							.groupName(dto.getGroupName())
							.groupHeadId(dto.getGroupHeadId())
							.isActive(dto.getIsActive())
							.groupHeadName(empIdToInfoMap.getOrDefault(dto.getGroupHeadId(), new EmpInfoDto("", "")).getEmpName())
							.groupHeadDesig(empIdToInfoMap.getOrDefault(dto.getGroupHeadId(), new EmpInfoDto("", "")).getEmpDesigCode())

							.tdName(dto.getTdName())
							.tdId(dto.getTdId())
							.build())
					.collect(Collectors.toList());
			return finalDivisionGroupList;
		} catch (Exception e) {
			logger.info(new Date() + " MasterServiceImpl Inside method getDivisonGroupList"+ e.getMessage());
			return null; // Return an empty list in case of an error
		}
	}

	@Override
	public List<ProjectMasterDto> getprojectMasterList(String username) throws Exception {
		logger.info(new Date() + " MasterServiceImpl Inside method getprojectMasterList");
		try {
			List<LoginDetailsDto> loginDtoList = loginDetailsList(username);
			String labCode = loginDtoList.stream()
					.filter(dto -> dto.getUsername().equals(username))
					.map(LoginDetailsDto::getLabCode)
					.findFirst()
					.orElse(null);

			if (labCode == null) {
				return Collections.emptyList();
			}

			List<ProjectMasterDto> projectList = masterClient.getProjectMasterList(xApiKey);

			if (projectList == null || projectList.isEmpty()) {
				return Collections.emptyList();
			}

			List<EmployeeDto> employeeList = masterClient.getEmployeeList(xApiKey);
			Set<Long> directorEmpIds = projectList.stream()
					.filter(project -> project.getIsActive() == 1 && project.getLabCode().equals(labCode))
					.map(ProjectMasterDto::getProjectDirector)
					.collect(Collectors.toSet());

			Map<Long, EmpInfoDto> empIdToInfoMap = employeeList.stream()
					.filter(emp -> directorEmpIds.contains(emp.getEmpId()))
					.collect(Collectors.toMap(
							EmployeeDto::getEmpId,
							emp -> new EmpInfoDto(emp.getEmpName(), emp.getEmpDesigName())
					));

			return projectList.stream()
					.filter(project -> project.getIsActive() == 1 && project.getLabCode().equals(labCode))
					.sorted(Comparator.comparing(ProjectMasterDto::getCreatedDate, Comparator.nullsLast(Comparator.naturalOrder())))
					.map(dto -> {
						EmpInfoDto empInfo = empIdToInfoMap.get(dto.getProjectDirector());
						return ProjectMasterDto.builder()
								.projectId(dto.getProjectId())
								.projectMainId(dto.getProjectMainId())
								.labCode(dto.getLabCode())
								.projectCode(dto.getProjectCode())
								.projectShortName(dto.getProjectShortName())
								.projectImmsCd(dto.getProjectImmsCd())
								.projectName(dto.getProjectName())
								.projectDescription(dto.getProjectDescription())
								.unitCode(dto.getUnitCode())
								.projectType(dto.getProjectType())
								.projectCategory(dto.getProjectCategory())
								.sanctionNo(dto.getSanctionNo())
								.sanctionDate(dto.getSanctionDate())
								.totalSanctionCost(dto.getTotalSanctionCost())
								.sanctionCostRE(dto.getSanctionCostRE())
								.sanctionCostFE(dto.getSanctionCostFE())
								.pdc(dto.getPdc())
								.projectDirector(dto.getProjectDirector())
								.projSancAuthority(dto.getProjSancAuthority())
								.boardReference(dto.getBoardReference())
								.revisionNo(dto.getRevisionNo())
								.isMainWC(dto.getIsMainWC())
								.workCenter(dto.getWorkCenter())
								.objective(dto.getObjective())
								.deliverable(dto.getDeliverable())
								.endUser(dto.getEndUser())
								.application(dto.getApplication())
								.labParticipating(dto.getLabParticipating())
								.scope(dto.getScope())
								.prjDirectorName(empInfo != null ? empInfo.getEmpName() : null)
								.prjDirectorDesig(empInfo != null ? empInfo.getEmpDesigCode() : null)
								.build();
					})
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.info(new Date() + " MasterServiceImpl Inside method getprojectMasterList"+ e.getMessage());
			return null;
		}
	}

}
