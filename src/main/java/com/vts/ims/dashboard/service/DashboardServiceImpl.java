package com.vts.ims.dashboard.service;

import java.util.ArrayList;
import java.util.Collections;
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
import com.vts.ims.audit.repository.AuditCheckListRepository;
import com.vts.ims.audit.service.AuditService;
import com.vts.ims.dashboard.dto.CheckListObsCountDto;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;
import com.vts.ims.qms.dto.DwpRevisionRecordDto;
import com.vts.ims.qms.dto.QmsDocTypeDto;
import com.vts.ims.qms.dto.QmsQmRevisionRecordDto;
import com.vts.ims.qms.model.DwpRevisionRecord;
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
	
	@Value("${x_api_key}")
	private String xApiKey;
	
	private Long parseLong(Object value) {
	    if (value == null) {
	        return 0L;
	    }
	    return Long.parseLong(value.toString()); 
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
	
}
