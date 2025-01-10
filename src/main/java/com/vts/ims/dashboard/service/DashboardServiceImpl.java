package com.vts.ims.dashboard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vts.ims.audit.dto.AuditScheduleListDto;
import com.vts.ims.audit.dto.AuditeeDto;
import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.dto.AuditorTeamDto;
import com.vts.ims.audit.dto.IqaDto;
import com.vts.ims.audit.model.Iqa;
import com.vts.ims.audit.repository.AuditCheckListRepository;
import com.vts.ims.audit.repository.AuditeeRepository;
import com.vts.ims.audit.repository.IqaRepository;
import com.vts.ims.audit.service.AuditService;
import com.vts.ims.dashboard.dto.CheckListObsCountDto;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.DivisionEmployeeDto;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.ProjectEmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;
import com.vts.ims.qms.dto.DwpRevisionRecordDto;
import com.vts.ims.qms.dto.QmsDocTypeDto;
import com.vts.ims.qms.dto.QmsQmRevisionRecordDto;
import com.vts.ims.qms.model.DwpRevisionRecord;
import com.vts.ims.qms.model.QmsDocStatus;
import com.vts.ims.qms.model.QmsQmRevisionRecord;
import com.vts.ims.qms.repository.DwpRevisionRecordRepo;
import com.vts.ims.qms.repository.QmsQmRevisionRecordRepo;
import com.vts.ims.qms.service.QmsServiceImpl;


@Service
public class DashboardServiceImpl implements DashboardService {

	private static final Logger logger=LogManager.getLogger(QmsServiceImpl.class);


	@Autowired
	private QmsQmRevisionRecordRepo qmsQmRevisionRecordRepo;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private AuditCheckListRepository checkListRepo;
	
	@Autowired
	private MasterClient masterClient;
	
	@Autowired
	private DwpRevisionRecordRepo dwpRevisionRecordRepo;
	
	@Autowired
	private AuditeeRepository auditeeRepository;
	
	
	
	
	@Autowired
	IqaRepository iqaRepository;
	
	@Value("${x_api_key}")
	private String xApiKey;
	
	private Long parseLong(Object value) {
	    if (value == null) {
	        return 0L;
	    }
	    return Long.parseLong(value.toString()); 
	}

	
	@Override
	public List<IqaDto> getIqaListForDashboard() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getIqaListForDashboard()");
		try {
			List<Iqa> iqalist = iqaRepository.getIqaListForDashboard();
			List<IqaDto> finalIqaDtoList = iqalist.stream()
				    .map(obj -> {
				        IqaDto dto = new IqaDto();
				        dto.setIqaId(obj.getIqaId());
				        dto.setIqaNo(obj.getIqaNo());
				        dto.setFromDate(obj.getFromDate());
				        dto.setToDate(obj.getToDate());
				        dto.setScope(obj.getScope());
				        dto.setDescription(obj.getDescription());
				        return dto;
				    })
				    .sorted(Comparator.comparingLong(IqaDto::getIqaId).reversed()) 
				    .collect(Collectors.toList());
			return finalIqaDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getIqaListForDashboard()"+ e);
			 return Collections.emptyList();
		}
	}
	
	@Override
	public List<DwpRevisionRecordDto> getAllDwpRevisionListForDashboard() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getDwpRevisionListForDashboard()");
		try {



			
			List<DwpRevisionRecordDto> revisionRecordDtoList = new ArrayList<DwpRevisionRecordDto>();
			List<DwpRevisionRecord> revisionRecord = dwpRevisionRecordRepo.findAllActiveDwpRecords();
			revisionRecord.forEach(revison -> {
	
				
	
				
				DwpRevisionRecordDto qmsQmRevisionRecordDto = DwpRevisionRecordDto.builder()
						
						.RevisionRecordId(revison.getRevisionRecordId())
						.DocType(revison.getDocType())
						.GroupDivisionId(revison.getGroupDivisionId())
						.DocFileName(revison.getDocFileName())
						.DocFilepath(revison.getDocFilepath())
						.Description(revison.getDescription())
						.IssueNo(revison.getIssueNo())
						.RevisionNo(revison.getRevisionNo())
						.DateOfRevision(revison.getDateOfRevision())
						.StatusCode(revison.getStatusCode())
						.AbbreviationIdNotReq(revison.getAbbreviationIdNotReq())
						.InitiatedBy(revison.getInitiatedBy())
						.ReviewedBy(revison.getReviewedBy())
						.ApprovedBy(revison.getApprovedBy())
						.StatusCodeNext(revison.getStatusCodeNext())
						.CreatedBy(revison.getCreatedBy())
						.CreatedDate(revison.getCreatedDate())
						.ModifiedBy(revison.getModifiedBy())
						.ModifiedDate(revison.getModifiedDate())
						.IsActive(revison.getIsActive())
						.build();
				
				revisionRecordDtoList.add(qmsQmRevisionRecordDto);
			});
			
			return revisionRecordDtoList;
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getDwpRevisionListForDashboard()"+ e);
			 return Collections.emptyList();
		}
	}
	
	
	
	@Override
	public List<QmsQmRevisionRecordDto> getQmVersionDetailedDtoList() throws Exception {
		logger.info( " Inside getQmVersionDetailedDtoList() " );
		try {
			
			
			List<QmsQmRevisionRecordDto> qmsQmRevisionRecordDtoList = new ArrayList<QmsQmRevisionRecordDto>();
			List<QmsQmRevisionRecord> qmRevisionRecord = qmsQmRevisionRecordRepo.findAllActiveQmRecords();
			qmRevisionRecord.forEach(revison -> {
				QmsQmRevisionRecordDto qmsQmRevisionRecordDto = QmsQmRevisionRecordDto.builder()
						.RevisionRecordId(revison.getRevisionRecordId())
						.DocFileName(revison.getDocFileName())
						.DocFilepath(revison.getDocFilepath())
						.Description(revison.getDescription())
						.IssueNo(revison.getIssueNo())
						.RevisionNo(revison.getRevisionNo())
						.DateOfRevision(revison.getDateOfRevision())
						.StatusCode(revison.getStatusCode())
						.AbbreviationIdNotReq(revison.getAbbreviationIdNotReq())
						.CreatedBy(revison.getCreatedBy())
						.CreatedDate(revison.getCreatedDate())
						.ModifiedBy(revison.getModifiedBy())
						.ModifiedDate(revison.getModifiedDate())
						.IsActive(revison.getIsActive())
						.build();
				
				qmsQmRevisionRecordDtoList.add(qmsQmRevisionRecordDto);
			});
			
			return qmsQmRevisionRecordDtoList;
		} catch (Exception e) {
			logger.info( " Inside getQmVersionDetailedDtoList() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQmRevisionRecordDto>();
		}
	}
	
	
	public long getNoOfActiveAuditees() throws Exception {
	    logger.info("Inside getNoOfActiveAuditees()");
	    long count = 0; // Default to 0 if the list is empty or null
	    
	    try {
	        List<AuditeeDto> dto = auditService.getAuditeeList(); // Fetch the list
	        if (dto != null) {
	            count = dto.size(); // Set count to the size of the list
	        }
	    } catch (Exception e) {
	        logger.error("Exception in getNoOfActiveAuditees(): ", e); // Log error with details
	    }
	    
	    return count; // Return the count
	}


	
	public long getNoOfActiveAuditors() throws Exception{
		logger.info( " Inside getNoOfActiveAuditors() " );
		long count=0;
		try {
			List<AuditorDto> dto=auditService.getAuditorList();
		        if (dto != null) {
		            count = dto.size(); // Set count to the size of the list
		        }
			
	    } catch (Exception e) {
		logger.info( " Inside getNoOfActiveAuditors() "+ e );
	 	e.printStackTrace();
	  }
		return count;
	}
	

	
	public long getNoOfActiveTeams() throws Exception{
		logger.info( " Inside getNoOfActiveTeams() " );
		long count=0;
		try {
			List<AuditorTeamDto> dto=auditService.getAuditTeamMainList();
		        if (dto != null) {
		            count = dto.size(); // Set count to the size of the list
		        }
			
	    } catch (Exception e) {
		logger.info( " Inside getNoOfActiveTeams() "+ e );
	 	e.printStackTrace();
	  }
		return count;
	}
	
	
	public long getNoOfActiveSchedules() throws Exception{
		logger.info( " Inside getNoOfActiveSchedules() " );
		long count=0;
		try {
			  List<AuditScheduleListDto> dto=auditService.getScheduleList();
		        if (dto != null) {
		            count = dto.size(); 
		        }
		} catch (Exception e) {
		logger.info( " Inside getNoOfActiveSchedules() "+ e );
	 	e.printStackTrace();
	  }
		return count;
	}
	

	@Override
	public List<CheckListObsCountDto> getTotalObsCountByIqa() throws Exception{
		logger.info( " Inside getTotalObsCountByIqa() " );
		try {
			
			
			List<Object[]> getTotalObsCountByIqaWise = checkListRepo.getTotalObsCountByIqa();
		
			   return getTotalObsCountByIqaWise.stream()
			            .map(row -> CheckListObsCountDto.builder()
			                .iqaId(parseLong(row[0])) // iqaId
			                .totalCountNC(parseLong(row[1])) // totalCountNC
			                .totalCountOBS(parseLong(row[2])) // totalCountOBS
			                .totalCountOFI(parseLong(row[3])) // totalCountOFI
			        
			                .build()
			            )
			            .toList(); 
			
		
		} catch (Exception e) {
			logger.info( " Inside getChecklistDetailedList() "+ e );
			e.printStackTrace();
			return new ArrayList<CheckListObsCountDto>();
		}
	}
	

	@Override
	public List<CheckListObsCountDto> getCheckListDataByObservation() throws Exception {
	    logger.info("Inside getCheckListDataByObservation()");

	    try {

	        List<Object[]> rawData = checkListRepo.getCheckListObsByDivPrjGroup();
	        
	        List<EmployeeDto> totalEmployee = masterClient.getEmployeeMasterList(xApiKey);
			List<DivisionMasterDto> divisionMaster = masterClient.getDivisionMaster(xApiKey);
			List<ProjectMasterDto> totalProject = masterClient.getProjectMasterList(xApiKey);
			List<DivisionGroupDto> groupList = masterClient.getDivisionGroupList(xApiKey);
			
		    Map<Long, EmployeeDto> employeeMap = totalEmployee.stream()
		            .filter(employee -> employee.getEmpId() != null)
		            .collect(Collectors.toMap(EmployeeDto::getEmpId, employee -> employee));
		    
		    Map<Long,DivisionMasterDto> divisionMap = divisionMaster.stream()
		    		.filter(division -> division.getDivisionId() !=null)
		    		.collect(Collectors.toMap(DivisionMasterDto::getDivisionId, division -> division));
		    
		    Map<Long,ProjectMasterDto> projectMap = totalProject.stream()
		    		.filter(project -> project.getProjectId()!=null)
		    		.collect(Collectors.toMap(ProjectMasterDto::getProjectId, project -> project));
		    
		    Map<Long,DivisionGroupDto> groupMap = groupList.stream()
		    		.filter(group -> group.getGroupId() !=null)
		    		.collect(Collectors.toMap(DivisionGroupDto::getGroupId, group -> group));
	        
	        List<CheckListObsCountDto> finalCheckListData = Optional.ofNullable(rawData)
	                .orElse(Collections.emptyList())
	                .stream()
	                .map(row -> {
	         
	                	  EmployeeDto employee =	row[3] != null?employeeMap.get(Long.parseLong(row[3].toString())):null;
						  DivisionMasterDto division = (row[4]!=null && !row[4].toString().equalsIgnoreCase("0"))?divisionMap.get(Long.parseLong(row[4].toString())):null;
						  DivisionGroupDto group = (row[5]!=null && !row[5].toString().equalsIgnoreCase("0"))?groupMap.get(Long.parseLong(row[5].toString())):null;
						  ProjectMasterDto project = (row[6]!=null && !row[6].toString().equalsIgnoreCase("0"))?projectMap.get(Long.parseLong(row[6].toString())):null;

	                	
	                    return CheckListObsCountDto.builder()
	                            .iqaId(parseLong(row[0]))
	                            .scheduleId(parseLong(row[1]))
	                            .auditeeId(parseLong(row[2]))
	                            .empId(parseLong(row[3]))
	                            .divisionId(parseLong(row[4]))
	                            .groupId(parseLong(row[5]))
	                            .projectId(parseLong(row[6]))
	                            .divisionName(division !=null?division.getDivisionName():"")
				    			.groupName(group !=null?group.getGroupName():"")
				    			.projectName(project !=null?project.getProjectShortName():"")
				    			.auditeeEmpName(employee != null?employee.getEmpName()+", "+employee.getEmpDesigName():"")
				    	        .countOfNC(parseLong(row[7])) // countOfNC
					            .countOfOBS(parseLong(row[8])) // countOfOBS
					            .countOfOFI(parseLong(row[9])) // countOfOFI
					                
	                            .build();
	                })
	                .collect(Collectors.toList());

	        return finalCheckListData;

	    } catch (Exception e) {
	        logger.error("Error in getCheckListDataByObservation()", e);
	        return new ArrayList<>();
	    }
	}
	
	@Override
	public List<DwpRevisionRecordDto> getAllVersionRecordDtoList(QmsDocTypeDto qmsDocTypeDto) throws Exception {
		logger.info( " Inside getAllVersionRecordDtoList() " );
		try {
			
			System.out.println("getDocType : "+qmsDocTypeDto.getDocType());		
			System.out.println("getGroupDivisionId : "+qmsDocTypeDto.getGroupDivisionId());	
			
			List<DivisionMasterDto> divisionDtoList = masterClient.getDivisionMaster(xApiKey);
			List<DivisionGroupDto> divisiongroupDtoList = masterClient.getDivisionGroupList(xApiKey);
			
			
			List<DwpRevisionRecordDto> revisionRecordDtoList = new ArrayList<DwpRevisionRecordDto>();
			
			List<DwpRevisionRecord> revisionRecord = null;
			if(qmsDocTypeDto.getGroupDivisionId() == 0) {
				 revisionRecord = dwpRevisionRecordRepo.findVersionRecordsByDocType(qmsDocTypeDto.getDocType());
				 System.out.println("Records fetched for findVersionRecordsByDocType:");
				    revisionRecord.forEach(record -> System.out.println(record.toString()));
			
			}else {
			      revisionRecord = dwpRevisionRecordRepo.findAllActiveDwpRecordsByDocType(qmsDocTypeDto.getDocType(), qmsDocTypeDto.getGroupDivisionId());
			      System.out.println("Records fetched for findAllActiveDwpRecordsByDocType:");
			      revisionRecord.forEach(record -> System.out.println(record.toString()));

			}
			revisionRecord.forEach(revison -> {
				
				DivisionMasterDto divisionDto = null;
				DivisionGroupDto divisiongroupDto = null;
				
				if(revison.getDocType().equalsIgnoreCase("dwp")) {
					
					divisionDto = divisionDtoList.stream()
			    	        .filter(division -> division.getDivisionId().equals(revison.getGroupDivisionId()))
			    	        .findFirst()
			    	        .orElse(null);
					
				} else if(revison.getDocType().equalsIgnoreCase("gwp")) {
					
					divisiongroupDto = divisiongroupDtoList.stream()
			    	        .filter(obj -> obj.getGroupId().equals(revison.getGroupDivisionId()))
			    	        .findFirst()
			    	        .orElse(null);
				}
				
				DwpRevisionRecordDto qmsQmRevisionRecordDto = DwpRevisionRecordDto.builder()
						.RevisionRecordId(revison.getRevisionRecordId())
						.DocType(revison.getDocType())
						.GroupDivisionId(revison.getGroupDivisionId())
						.divisionMasterDto(divisionDto)
						.divisionGroupDto(divisiongroupDto)
						.DocFileName(revison.getDocFileName())
						.DocFilepath(revison.getDocFilepath())
						.Description(revison.getDescription())
						.IssueNo(revison.getIssueNo())
						.RevisionNo(revison.getRevisionNo())
						.DateOfRevision(revison.getDateOfRevision())
						.StatusCode(revison.getStatusCode())
						.AbbreviationIdNotReq(revison.getAbbreviationIdNotReq())
						.CreatedBy(revison.getCreatedBy())
						.CreatedDate(revison.getCreatedDate())
						.ModifiedBy(revison.getModifiedBy())
						.ModifiedDate(revison.getModifiedDate())
						.IsActive(revison.getIsActive())
						.build();
				
				revisionRecordDtoList.add(qmsQmRevisionRecordDto);
			});
			
			return revisionRecordDtoList;
		} catch (Exception e) {
			logger.error( " Inside getAllVersionRecordDtoList() "+ e );
			e.printStackTrace();
			return new ArrayList<DwpRevisionRecordDto>();
		}
	}
	
	
	
	@Override
	public List<ProjectMasterDto> getProjectListOfPrjEmps(Integer imsFormRoleId, Long empId) throws Exception{
		logger.info("Inside getDwpProjectMaster()");
		try {
			List<Integer> isAllList = Arrays.asList(1, 2, 3, 4);
			List<ProjectMasterDto> projectDto = masterClient.getProjectMasterList(xApiKey);
			List<ProjectMasterDto> activeAllProjectDto = projectDto.stream()
					.filter(dto -> dto.getIsActive() == 1)
					.collect(Collectors.toList());

			if (isAllList.contains(imsFormRoleId)) {
				return activeAllProjectDto;
			}
			
			

			// Fetch project employee data
			List<ProjectEmployeeDto> projectEmployeeDtoList = masterClient.getProjectEmpDetailsById(xApiKey);
			
	    
	        //  Filter the employee list for active project employees matching empId
			List<ProjectEmployeeDto> projectEmployeeDtoListByEmpId = projectEmployeeDtoList.stream()
					.filter(dto -> dto.getEmpId().equals(empId) && dto.getIsActive() == 1)
					.collect(Collectors.toList());
	    
	        //  Collect project IDs from the filtered project employee list
	        List<Long> projectFromPrjEmps = projectEmployeeDtoListByEmpId.stream()
	                .map(ProjectEmployeeDto::getProjectId)
	                .collect(Collectors.toList());

	        //  Filter activeAllProjectDto by the collected project IDs from project employees
	        List<ProjectMasterDto> returnProjectList = activeAllProjectDto.stream()
	                .filter(obj -> projectFromPrjEmps.contains(obj.getProjectId())
	                		)
	                .collect(Collectors.toList());
	     

			
			//List<Long> auditeeProjectIds = auditeeRepository.findProjectIdsByEmpId(empId);

		
			return returnProjectList;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getProjectListOfPrjEmps() ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<DivisionGroupDto> getGroupListOfDivEmps(Integer imsFormRoleId, Long empId) throws Exception {
		logger.info("Inside getGroupListOfDivEmps()");
		try {

			List<Integer> isAllList = Arrays.asList(1, 2, 3, 4);

			List<DivisionGroupDto> divisiongroupdto = masterClient.getDivisionGroupList(xApiKey);
			List<DivisionGroupDto> activeDivisiongroupdto = divisiongroupdto.stream()
					.filter(dto -> dto.getIsActive()== 1)
					.collect(Collectors.toList());


			if (isAllList.contains(imsFormRoleId)) {
				return activeDivisiongroupdto;
			}

			List<DivisionMasterDto> divisionDto = masterClient.getDivisionMaster(xApiKey);
			List<DivisionMasterDto> activeAllDivisionDto = divisionDto.stream()
					.filter(dto -> dto.getIsActive() == 1)
					.collect(Collectors.toList());
			

			// Fetch division employee data
	        List<DivisionEmployeeDto> divisionEmployeeDtoList = masterClient.getDivisionEmpDetailsById(xApiKey);
	    
	        //  Filter the employee list for active division employees matching empId
	      List<DivisionEmployeeDto> divisionEmployeeDtoListByEmpId = divisionEmployeeDtoList.stream()
	                .filter(dto -> dto.getEmpId().equals(empId) && dto.getIsActive() == 1)
	                .collect(Collectors.toList());


	        //  Collect division IDs from the filtered division employee list
	        List<Long> divisionIdsFromDivEmps = divisionEmployeeDtoListByEmpId.stream()
	                .map(DivisionEmployeeDto::getDivisionId)
	                .collect(Collectors.toList());

	        //  Filter activeAllDivisionDto by the collected division IDs from division employees
	        List<DivisionMasterDto> divisionListFiltered = activeAllDivisionDto.stream()
	                .filter(obj -> divisionIdsFromDivEmps.contains(obj.getDivisionId()))
	                .collect(Collectors.toList());
	     
			List<DivisionGroupDto> returnDivisiongroupdto =  activeDivisiongroupdto.stream().filter(obj -> divisionListFiltered.stream()
					.anyMatch(dto -> dto.getGroupId().equals(obj.getGroupId()))).collect(Collectors.toList());



			return returnDivisiongroupdto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getGroupListOfDivEmps() ", e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public List<DivisionMasterDto> getAllActiveDivisionList(String username) throws Exception {
		logger.info("Inside getAllActiveDivisionList()");
		try {

			
			List<DivisionMasterDto> divisionDto = masterClient.getDivisionMaster(xApiKey);
			List<DivisionMasterDto> activeAllDivisionDto = divisionDto.stream()
					.filter(dto -> dto.getIsActive() == 1)
					.collect(Collectors.toList());


			return activeAllDivisionDto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getAllActiveDivisionList() ", e);
			return Collections.emptyList();
		}
	}
	
	
	
	@Override
	public List<DivisionMasterDto> getDivisionListOfDivEmps(Integer imsFormRoleId, Long empId) throws Exception {
		logger.info("Inside getDivisionListOfDivEmps()");
		try {

			List<Integer> isAllList = Arrays.asList(1, 2, 3, 4);
			List<DivisionMasterDto> divisionDto = masterClient.getDivisionMaster(xApiKey);

			List<DivisionMasterDto> activeAllDivisionDto = divisionDto.stream()
					.filter(dto -> dto.getIsActive() == 1)
					.collect(Collectors.toList());

			if (isAllList.contains(imsFormRoleId)) {
				return activeAllDivisionDto;
			}

	
			// Fetch division employee data
	        List<DivisionEmployeeDto> divisionEmployeeDtoList = masterClient.getDivisionEmpDetailsById(xApiKey);
	    
	        //  Filter the employee list for active division employees matching empId
	      List<DivisionEmployeeDto> divisionEmployeeDtoListByEmpId = divisionEmployeeDtoList.stream()
	                .filter(dto -> dto.getEmpId().equals(empId) && dto.getIsActive() == 1)
	                .collect(Collectors.toList());
	    
	        //  Collect division IDs from the filtered division employee list
	        List<Long> divisionIdsFromDivEmps = divisionEmployeeDtoListByEmpId.stream()
	                .map(DivisionEmployeeDto::getDivisionId)
	                .collect(Collectors.toList());

	        //  Filter activeAllDivisionDto by the collected division IDs from division employees
	        List<DivisionMasterDto> returnDivisionList = activeAllDivisionDto.stream()
	                .filter(obj -> divisionIdsFromDivEmps.contains(obj.getDivisionId()))
	                .collect(Collectors.toList());
	     


			
			
			//List<Long> auditeeDivisionIds = auditeeRepository.findDivisionIdsByEmpId(empId);
			//|| (auditeeDivisionIds.contains(obj.getDivisionId()))
			//List<EmployeeDto> emp = masterClient.getEmployee(xApiKey, empId);
			//EmployeeDto empDto = emp.size() > 0 ? emp.get(0) : EmployeeDto.builder().build();
			//|| empDto.getDivisionId().equals(obj.getDivisionId())



			return returnDivisionList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getDwpDivisionMaster() ", e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<DivisionMasterDto> getDivisionListOfDH(Integer imsFormRoleId, Long empId) throws Exception {
		logger.info("Inside getDivisionListOfDH()");
		try {

			 // Fetch the division data
	        List<DivisionMasterDto> divisionDto = masterClient.getDivisionMaster(xApiKey);

	        // Filter active divisions where isActive == 1
	        List<DivisionMasterDto> activeAllDivisionDto = divisionDto.stream()
	                .filter(dto -> dto.getIsActive() == 1)
	                .collect(Collectors.toList());

	        // Further filter to find divisions where DivisionHeadId matches empId
	        List<DivisionMasterDto> returnDivisionList = activeAllDivisionDto.stream()
	                .filter(dto -> dto.getDivisionHeadId() != null && dto.getDivisionHeadId().equals(empId))
	                .collect(Collectors.toList());

	        // Return the filtered list
	        return returnDivisionList;
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getDwpDivisionMaster() ", e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public List<DivisionMasterDto> getDivisionListOfGH(Integer imsFormRoleId, Long empId) throws Exception {
		logger.info("Inside getDivisionListOfDH()");
		try {

			 // Fetch the division data
	        List<DivisionMasterDto> divisionDto = masterClient.getDivisionMaster(xApiKey);

	        // Filter active divisions where isActive == 1
	        List<DivisionMasterDto> activeAllDivisionDto = divisionDto.stream()
	                .filter(dto -> dto.getIsActive() == 1)
	                .collect(Collectors.toList());

	       //Fetch the Group data 
			List<DivisionGroupDto> divisiongroupdto = masterClient.getDivisionGroupList(xApiKey);

	        
	       // Filter active groups  where isActive == 1
			List<DivisionGroupDto> activeDivisiongroupdto = divisiongroupdto.stream()
					.filter(dto -> dto.getIsActive()== 1)
					.collect(Collectors.toList());
			
		     // Further filter to find group where GroupHeadId matches empId
	        List<DivisionGroupDto> filteredGroupList = activeDivisiongroupdto.stream()
	                .filter(dto -> dto.getGroupHeadId() != null && dto.getGroupHeadId().equals(empId))
	                .collect(Collectors.toList());
	        
	     // Collect the group IDs from filteredGroupList
	        List<Long> groupIds = filteredGroupList.stream()
	                .map(DivisionGroupDto::getGroupId) 
	                .collect(Collectors.toList());
	        
	     // Filter the activeAllDivisionDto list based on groupIds so you will get divisions which belong 
	        List<DivisionMasterDto> filteredDivisionsOfGH = activeAllDivisionDto.stream()
	                .filter(division -> groupIds.contains(division.getGroupId())) 
	                .collect(Collectors.toList());


	        // Return the filtered list
	        return filteredDivisionsOfGH;
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getDwpDivisionMaster() ", e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public List<ProjectMasterDto> getProjectListOfPrjDir(Integer imsFormRoleId, Long empId) throws Exception {
		logger.info("Inside getProjectListOfPrjDir()");
		try {

			List<ProjectMasterDto> projectDto = masterClient.getProjectMasterList(xApiKey);

	        // Filter active projects where isActive == 1
			List<ProjectMasterDto> activeAllProjectDto = projectDto.stream()
					.filter(dto -> dto.getIsActive() == 1)
					.collect(Collectors.toList());



	        // Further filter to find projects where ProjectDirector matches empId
	        List<ProjectMasterDto> returnProjectList = activeAllProjectDto.stream()
	                .filter(dto -> dto.getProjectDirector() != null && dto.getProjectDirector().equals(empId))
	                .collect(Collectors.toList());

	        // Return the filtered list
	        return returnProjectList;
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getProjectListOfPrjDir() ", e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public List<CheckListObsCountDto> getTrendNcObsList() throws Exception {
	    logger.info("Inside getTrendNcObsList()");

	    try {
	        List<Object[]> rawData = checkListRepo.getCheckListObsByIqa();

	        // Group by iqaId and aggregate counts
	        Map<Long, CheckListObsCountDto> aggregatedData = Optional.ofNullable(rawData)
	            .orElse(Collections.emptyList())
	            .stream()
	            .collect(Collectors.groupingBy(
	                row -> parseLong(row[0]), // Group by iqaId
	                Collectors.collectingAndThen(
	                    Collectors.toList(),
	                    rows -> {
	                        // Aggregate counts
	                        Long totalCountNC = rows.stream().mapToLong(r -> parseLong(r[10])).sum();
	                        Long totalCountOBS = rows.stream().mapToLong(r -> parseLong(r[11])).sum();
	                        Long totalCountOFI = rows.stream().mapToLong(r -> parseLong(r[12])).sum();

	                        // Extract data from first row of each group
	                        Object[] firstRow = rows.get(0);
	                        return CheckListObsCountDto.builder()
	                            .iqaId(parseLong(firstRow[0]))
	                            .iqaNo(firstRow[1] != null ? firstRow[1].toString() : null)
	                            .fromDate(firstRow[2] != null ? firstRow[2].toString() : null)
	                            .toDate(firstRow[3] != null ? firstRow[3].toString() : null)
	                            .totalCountNC(totalCountNC)
	                            .totalCountOBS(totalCountOBS)
	                            .totalCountOFI(totalCountOFI)
	                            .build();
	                    }
	                )
	            ));

	        // Return the aggregated data as a list
	        return new ArrayList<>(aggregatedData.values());

	    } catch (Exception e) {
	        logger.error("Error in getTrendNcObsList()", e);
	        return new ArrayList<>();
	    }
	}


	
}
