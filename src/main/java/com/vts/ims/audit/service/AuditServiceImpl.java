package com.vts.ims.audit.service;

import java.time.LocalDateTime;
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
import org.springframework.stereotype.Service;

import com.vts.ims.audit.dto.AuditScheduleDto;
import com.vts.ims.audit.dto.AuditScheduleListDto;
import com.vts.ims.audit.dto.AuditeeDto;
import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.dto.IqaDto;
import com.vts.ims.audit.model.AuditSchedule;
import com.vts.ims.audit.model.AuditScheduleRevision;
import com.vts.ims.audit.model.AuditTeam;
import com.vts.ims.audit.model.Auditee;
import com.vts.ims.audit.model.Auditor;
import com.vts.ims.audit.model.Iqa;
import com.vts.ims.audit.repository.AuditScheduleRepository;
import com.vts.ims.audit.repository.AuditScheduleRevRepository;
import com.vts.ims.audit.repository.AuditeeRepository;
import com.vts.ims.audit.repository.AuditorRepository;
import com.vts.ims.audit.repository.IqaRepository;
import com.vts.ims.audit.repository.TeamRepository;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;
import com.vts.ims.util.DLocalConvertion;


@Service
public class AuditServiceImpl implements AuditService{

	private static final Logger logger=LogManager.getLogger(AuditServiceImpl.class);
	
	@Autowired
	AuditorRepository auditRepository;
	
	@Autowired
	IqaRepository iqaRepository;
	
	@Autowired
	AuditeeRepository auditeeRepository;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	private MasterClient masterClient;
	
	@Autowired
	private AuditScheduleRepository auditScheduleRepository;
	
	@Autowired
	private AuditScheduleRevRepository auditScheduleRevRepository;
	

	
	@Override
	public List<AuditorDto> getAuditList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getAuditList()");
		try {
			List<Auditor> auditors = auditRepository.findAll();
			List<EmployeeDto> employeeList=masterClient.getEmployeeList("VTS");
		    Map<Long, EmployeeDto> employeeMap = employeeList.stream()
		            .filter(employee -> employee.getEmpId() != null)
		            .collect(Collectors.toMap(EmployeeDto::getEmpId, employee -> employee));
			List<AuditorDto> finalDto = auditors.stream()
				    .map(obj -> {
				        AuditorDto auditorDto = new AuditorDto();
				       // EmployeeDto employeeDto = masterClient.getEmployee("VTS", obj.getEmpId()).get(0);
				        EmployeeDto employeeDto =  employeeMap.get(obj.getEmpId());
				        auditorDto.setEmpId(obj.getEmpId());
				        auditorDto.setEmpName(employeeDto.getEmpName());
				        auditorDto.setDesignation(employeeDto.getEmpDesigName());
				        auditorDto.setDivisionName(employeeDto.getEmpDivCode());
				        auditorDto.setAuditorId(obj.getAuditorId());
				        auditorDto.setIsActive(obj.getIsActive());
				        return auditorDto;
				    })
				    .sorted(Comparator.comparingLong(AuditorDto::getAuditorId).reversed()) 
				    .collect(Collectors.toList());
			return finalDto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getAuditList()"+ e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public List<EmployeeDto> getEmployelist() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getEmployelist()");
		try {
			List<EmployeeDto> empdto=masterClient.getEmployeeList("VTS");
			Comparator<EmployeeDto> comparator = Comparator
		            .comparingLong((EmployeeDto dto) -> dto.getSrNo() == 0 ? 1 : 0) 
		            .thenComparingLong(EmployeeDto::getSrNo);
			return empdto.stream().sorted(comparator).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getEmployelist()"+ e);
			return Collections.emptyList();
		}
		
	}
	
	
	@Override
	public long insertAuditor(String[] empIds, String username) throws Exception {
	    logger.info(new Date() + " AuditServiceImpl Inside method insertAuditor()");
	    long result = 0;
	    try {
	        if (empIds != null && empIds.length > 0) {
	            List<Object[]> inactiveAuditors = auditRepository.isActiveAuditorList(0);
	            Map<Long, Long> inactiveAuditorMap = inactiveAuditors.stream()
	                    .collect(Collectors.toMap(auditor -> (Long) auditor[0], auditor -> (Long) auditor[1]));
	            for (String empIdStr : empIds) {
	                Long empId = Long.parseLong(empIdStr);
	                if (inactiveAuditorMap.containsKey(empId)) {
	                    Long auditorId = inactiveAuditorMap.get(empId);
	                    Optional<Auditor> model = auditRepository.findById(auditorId); 
	                    if (model.isPresent()) {
	                        Auditor data = model.get();
	                        data.setIsActive(1);
	                        data.setModifiedBy(username);
	                        data.setModifiedDate(LocalDateTime.now());
	                        result = auditRepository.save(data).getAuditorId();
	                        logger.info("Reactivated Auditor: " + auditorId);
	                    }
	                } else {
	                    Auditor model = new Auditor();
	                    model.setEmpId(empId);
	                    model.setCreatedBy(username);
	                    model.setCreatedDate(LocalDateTime.now());
	                    model.setIsActive(1);
	                    result = auditRepository.save(model).getAuditorId();
	                    logger.info("Created new Auditor for EmpId: " + empId);
	                }
	            }
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method insertAuditor() " + e);
	    }
	    return result;
	}

	
	
	@Override
	public long updateAuditor(AuditorDto auditordto, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method updateAuditor()");
		long result=0;
		try {
			Optional<Auditor> model =auditRepository.findById(auditordto.getAuditorId());
			System.out.println("model.isPresent():"+model.isPresent());
			if(model.isPresent()) {
				Auditor data = model.get();
				data.setIsActive(auditordto.getIsActive());
				data.setModifiedBy(username);
				data.setModifiedDate(LocalDateTime.now());
				result=auditRepository.save(data).getAuditorId();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method updateAuditor()"+ e);
			return 0;
		}
	}
	
	@Override
	public List<IqaDto> getIqaList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getIqaList()");
		try {
			List<Iqa> iqalist = iqaRepository.findAll();
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
			logger.error("AuditServiceImpl Inside method getIqaList()"+ e);
			 return Collections.emptyList();
		}
	}
	
	@Override
	public long insertIqa(IqaDto iqadto, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method insertIqa()");
		long result=0;
		try {
			if(iqadto!=null && iqadto.getIqaId()!=null) {
				Optional<Iqa> iqaEntity = iqaRepository.findById(iqadto.getIqaId());
				if(iqaEntity.isPresent()) {
				Iqa	model =iqaEntity.get();
				model.setIqaNo(iqadto.getIqaNo());
				model.setFromDate(iqadto.getFromDate());
				model.setToDate(iqadto.getToDate());
				model.setScope(iqadto.getScope().trim());
				model.setDescription(iqadto.getDescription().trim());
				model.setModifiedBy(username);
				model.setModifiedDate(LocalDateTime.now());
				result=iqaRepository.save(model).getIqaId();
				}
			}else {
				Iqa model=new Iqa();
				model.setIqaNo(iqadto.getIqaNo());
				model.setFromDate(iqadto.getFromDate());
				model.setToDate(iqadto.getToDate());
				model.setScope(iqadto.getScope().trim());
				model.setDescription(iqadto.getDescription().trim());
				model.setCreatedBy(username);
				model.setCreatedDate(LocalDateTime.now());
				model.setIsActive(1);
				result=iqaRepository.save(model).getIqaId();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getIqaList()"+ e);
			return result ;
		}
	}
	
	
	@Override
	public List<AuditeeDto> getAuditeeList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getAuditeeList()");
		try {
			List<Auditee> auditeeList = auditeeRepository.findAllByIsActive(1); 
			List<EmployeeDto> totalEmployee = masterClient.getEmployeeMasterList("VTS");
			List<DivisionMasterDto> divisionMaster = masterClient.getDivisionMaster("VTS");
			List<ProjectMasterDto> totalProject = masterClient.getProjectMasterList("VTS");
			List<DivisionGroupDto> groupList = masterClient.getDivisionGroupList("VTS");
			
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
			
			List<AuditeeDto> finalAuditeeDtoList = auditeeList.stream()
					.map(obj -> {
					    EmployeeDto employee =	obj.getEmpId() != null?employeeMap.get(obj.getEmpId()):null;
					    DivisionMasterDto division = (obj.getDivisionId()!=null && !obj.getDivisionId().toString().equalsIgnoreCase("0"))?divisionMap.get(obj.getDivisionId()):null;
					    DivisionGroupDto group = (obj.getGroupId()!=null && !obj.getGroupId().toString().equalsIgnoreCase("0"))?groupMap.get(obj.getGroupId()):null;
					    ProjectMasterDto project = (obj.getProjectId()!=null && !obj.getProjectId().toString().equalsIgnoreCase("0"))?projectMap.get(obj.getProjectId()):null;
					    
				    	AuditeeDto dto = new AuditeeDto();
				    	dto.setAuditeeId(obj.getAuditeeId());
				    	dto.setEmpId(obj.getEmpId());
				    	dto.setGroupId(obj.getGroupId());
				    	dto.setDivisionId(obj.getDivisionId());
				    	dto.setProjectId(obj.getProjectId());
				    	dto.setCreatedBy(obj.getCreatedBy());
				    	dto.setCreatedDate(obj.getCreatedDate());
				    	dto.setModifiedBy(obj.getModifiedBy());
				    	dto.setModifiedDate(obj.getModifiedDate());
				    	dto.setIsActive(obj.getIsActive());
				    	dto.setAuditee(employee != null?employee.getEmpName()+", "+employee.getEmpDesigName():"");
				    	dto.setDivisionName(division !=null?division.getDivisionCode():"");
				    	dto.setGroupName(group !=null?group.getGroupCode():"");
				    	dto.setProjectName(project !=null?project.getProjectName():"");
				        return dto;
				    })
				    .sorted(Comparator.comparingLong(AuditeeDto::getAuditeeId).reversed()) 
				    .collect(Collectors.toList());
			return finalAuditeeDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getAuditeeList()"+ e);
			 return Collections.emptyList();
		}
	}
	
	
	@Override
	public List<DivisionMasterDto> getDivisionMaster() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getDivisionMaster()");
		try {
			List<DivisionMasterDto> divisiondto=masterClient.getDivisionMaster("VTS");
			System.out.println("divisiondto:"+divisiondto);
			 return divisiondto.stream()
                     .filter(dto -> dto.getIsActive() == 1)
                     .collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getDivisionMaster()"+ e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public List<DivisionGroupDto> getDivisionGroupList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getDivisionGroupList()");
		try {
			List<DivisionGroupDto> divisiongroupdto=masterClient.getDivisionGroupList("VTS");
			return divisiongroupdto.stream()
					.filter(dto -> dto.getIsActive()== 1)
					.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getDivisionGroupList()"+ e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<ProjectMasterDto> getProjectMasterList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getProjectMasterList()");
		try {
			List<ProjectMasterDto> projectmasterdto=masterClient.getProjectMasterList("VTS");
			return projectmasterdto.stream()
					.filter(dto -> dto.getIsActive() == 1)
					.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getProjectMasterList()"+ e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public long insertAuditee(AuditeeDto auditeedto, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method insertAuditee()");
		long result=0;
		try {
			if(auditeedto!=null && auditeedto.getAuditeeId()!=null) {
				Optional<Auditee> auditeeEntity = auditeeRepository.findById(auditeedto.getAuditeeId());
				if(auditeeEntity.isPresent()) {
					Auditee model=auditeeEntity.get();
					model.setAuditeeId(auditeedto.getAuditeeId());
					model.setEmpId(auditeedto.getEmpId());
					if(auditeedto.getHeadType().equalsIgnoreCase("D")){
						model.setDivisionId(auditeedto.getDivisionId());
						model.setGroupId(0L);
						model.setProjectId(0L);
					}else if(auditeedto.getHeadType().equalsIgnoreCase("G")) {
						model.setGroupId(auditeedto.getGroupId());
						model.setProjectId(0L);
						model.setDivisionId(0L);
					}else if (auditeedto.getHeadType().equalsIgnoreCase("P")) {
						model.setProjectId(auditeedto.getProjectId());
						model.setDivisionId(0L);
						model.setGroupId(0L);
					}
					model.setModifiedBy(username);
					model.setModifiedDate(LocalDateTime.now());
					model.setIsActive(1);
					result=auditeeRepository.save(model).getAuditeeId();
					}
			}else {
				Auditee model=new Auditee();
				model.setEmpId(auditeedto.getEmpId());
				if(auditeedto.getHeadType().equalsIgnoreCase("D")){
					model.setDivisionId(auditeedto.getDivisionId());
					model.setGroupId(0L);
					model.setProjectId(0L);
				}else if(auditeedto.getHeadType().equalsIgnoreCase("G")) {
					model.setGroupId(auditeedto.getGroupId());
					model.setProjectId(0L);
					model.setDivisionId(0L);
				}else if (auditeedto.getHeadType().equalsIgnoreCase("P")) {
					model.setProjectId(auditeedto.getProjectId());
					model.setDivisionId(0L);
					model.setGroupId(0L);
				}
				model.setCreatedBy(username);
				model.setCreatedDate(LocalDateTime.now());
				model.setIsActive(1);
				result=auditeeRepository.save(model).getAuditeeId();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method insertAuditee()"+ e);
			return result ;
		}
	}
	
	@Override
	public List<AuditTeam> getTeamList() throws Exception {
	    logger.info(new Date() + " AuditServiceImpl Inside method getTeamList()");
	    try {
	    	return teamRepository.findAllByIsActive(1);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method getTeamList() " + e);
	        return List.of();
	    }
	}
	
	
	@Override
	public long updateAuditee(String auditeeId, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method updateAuditee()");
		long result=0;
		try {
			Optional<Auditee> model =auditeeRepository.findById(Long.parseLong(auditeeId));
			System.out.println("model.isPresent():"+model.isPresent());
			if(model.isPresent()) {
				Auditee data = model.get();
				data.setIsActive(0);
				data.setModifiedBy(username);
				data.setModifiedDate(LocalDateTime.now());
				result=auditeeRepository.save(data).getAuditeeId();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method updateAuditee()"+ e);
			return 0;
		}
	}
	
	@Override
	public long insertAuditSchedule(AuditScheduleDto auditScheduleDto, String username) throws Exception {
	    logger.info(new Date() + " AuditServiceImpl Inside method insertAuditSchedule()");
	    long result = 0;
	    try {
	        auditScheduleDto.setScheduleDate(DLocalConvertion.converLocalTime(auditScheduleDto.getScheduleDate()));
	    	
	    	AuditSchedule schedule = new AuditSchedule();
	    	schedule.setAuditeeId(auditScheduleDto.getAuditeeId());
	    	schedule.setScheduleDate(auditScheduleDto.getScheduleDate());
	    	schedule.setTeamId(auditScheduleDto.getTeamId());
	    	schedule.setCreatedBy(username);
	    	schedule.setCreatedDate(LocalDateTime.now());
	    	schedule.setIsActive(1);
	    	result = auditScheduleRepository.save(schedule).getScheduleId();
	    	
	    	AuditScheduleRevision scheduleRev = new AuditScheduleRevision();
	    	scheduleRev.setScheduleId(result);
	    	scheduleRev.setAuditeeId(auditScheduleDto.getAuditeeId());
	    	scheduleRev.setScheduleDate(auditScheduleDto.getScheduleDate());
	    	scheduleRev.setTeamId(auditScheduleDto.getTeamId());
	    	scheduleRev.setRemarks(auditScheduleDto.getRemarks());
	    	scheduleRev.setCreatedBy(username);
	    	scheduleRev.setCreatedDate(LocalDateTime.now());
	    	scheduleRev.setIsActive(1);
	    	scheduleRev.setRevisionNo(0);
	    	result = auditScheduleRevRepository.save(scheduleRev).getRevScheduleId();
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method insertAuditSchedule() " + e);
	    }
	    return result;
	}
	
	
	@Override
	public long editAuditSchedule(AuditScheduleDto auditScheduleDto, String username) throws Exception {
	    logger.info(new Date() + " AuditServiceImpl Inside method editAuditSchedule()");
	    long result = 0;
	    try {
	    	auditScheduleDto.setScheduleDate(DLocalConvertion.converLocalTime(auditScheduleDto.getScheduleDate()));
	    	AuditSchedule schedule = auditScheduleRepository.findById(auditScheduleDto.getScheduleId()).get();
	    	schedule.setAuditeeId(auditScheduleDto.getAuditeeId());
	    	schedule.setScheduleDate(auditScheduleDto.getScheduleDate());
	    	schedule.setTeamId(auditScheduleDto.getTeamId());
	    	schedule.setModifiedBy(username);
	    	schedule.setModifiedDate(LocalDateTime.now());
	    	result = auditScheduleRepository.save(schedule).getScheduleId();
	    	
	    	AuditScheduleRevision scheduleRev = auditScheduleRevRepository.findByScheduleId(auditScheduleDto.getScheduleId());
	    	scheduleRev.setScheduleId(result);
	    	scheduleRev.setAuditeeId(auditScheduleDto.getAuditeeId());
	    	scheduleRev.setScheduleDate(auditScheduleDto.getScheduleDate());
	    	scheduleRev.setTeamId(auditScheduleDto.getTeamId());
	    	scheduleRev.setModifiedBy(username);
	    	scheduleRev.setModifiedDate(LocalDateTime.now());
	    	result = auditScheduleRevRepository.save(scheduleRev).getRevScheduleId();
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method editAuditSchedule() " + e);
	    }
	    return result;
	}
	
	@Override
	public long insertAuditReSchedule(AuditScheduleDto auditScheduleDto, String username) throws Exception {
	    logger.info(new Date() + " AuditServiceImpl Inside method insertAuditReSchedule()");
	    long result = 0;
	    try {
	    	auditScheduleDto.setScheduleDate(DLocalConvertion.converLocalTime(auditScheduleDto.getScheduleDate()));
	    	AuditSchedule schedule = auditScheduleRepository.findById(auditScheduleDto.getScheduleId()).get();
	    	schedule.setAuditeeId(auditScheduleDto.getAuditeeId());
	    	schedule.setScheduleDate(auditScheduleDto.getScheduleDate());
	    	schedule.setTeamId(auditScheduleDto.getTeamId());
	    	schedule.setModifiedBy(username);
	    	schedule.setModifiedDate(LocalDateTime.now());
	    	result = auditScheduleRepository.save(schedule).getScheduleId();
	    	
	    	AuditScheduleRevision scheduleRev = new AuditScheduleRevision();
	    	scheduleRev.setScheduleId(result);
	    	scheduleRev.setAuditeeId(auditScheduleDto.getAuditeeId());
	    	scheduleRev.setRemarks(auditScheduleDto.getRemarks());
	    	scheduleRev.setScheduleDate(auditScheduleDto.getScheduleDate());
	    	scheduleRev.setTeamId(auditScheduleDto.getTeamId());
	    	scheduleRev.setCreatedBy(username);
	    	scheduleRev.setCreatedDate(LocalDateTime.now());
	    	scheduleRev.setIsActive(1);
	    	scheduleRev.setRevisionNo(auditScheduleDto.getRevision()+1);
	    	result = auditScheduleRevRepository.save(scheduleRev).getRevScheduleId();
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method insertAuditReSchedule() " + e);
	    }
	    return result;
	}
	
	@Override
	public List<AuditScheduleListDto> getScheduleList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getScheduleList()");
		try {
			 List<Object[]> scheduleList = auditScheduleRepository.getScheduleList();
			List<EmployeeDto> totalEmployee = masterClient.getEmployeeMasterList("VTS");
			List<DivisionMasterDto> divisionMaster = masterClient.getDivisionMaster("VTS");
			List<ProjectMasterDto> totalProject = null;//masterClient.getProjectMasterList("VTS");
			List<DivisionGroupDto> groupList = null; //masterClient.getDivisionGroupList("VTS");
			
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
		    
			 List<AuditScheduleListDto> finalScheduleDtoList = Optional.ofNullable(scheduleList).orElse(Collections.emptyList()).stream()
				    .map(obj -> {
					    EmployeeDto employee =	obj[5] != null?employeeMap.get(Long.parseLong(obj[5].toString())):null;
					    DivisionMasterDto division = (obj[6]!=null && !obj[6].toString().equalsIgnoreCase("0"))?divisionMap.get(Long.parseLong(obj[6].toString())):null;
					    DivisionGroupDto group = (obj[7]!=null && !obj[7].toString().equalsIgnoreCase("0"))?groupMap.get(Long.parseLong(obj[7].toString())):null;
					    ProjectMasterDto project = (obj[8]!=null && !obj[8].toString().equalsIgnoreCase("0"))?projectMap.get(Long.parseLong(obj[8].toString())):null;
					    	return AuditScheduleListDto.builder()
				    			.scheduleId(obj[0]!=null?Long.parseLong(obj[0].toString()):0L)
				    			.scheduleDate(obj[1]!=null?obj[1].toString():"")
				    			.auditeeId(obj[2]!=null?Long.parseLong(obj[2].toString()):0L)
				    			.teamId(obj[3]!=null?Long.parseLong(obj[3].toString()):0L)
				    			.teamCode(obj[4]!=null?obj[4].toString():"")
				    			.auditeeEmpId(obj[5]!=null?Long.parseLong(obj[5].toString()):0L)
				    			.divisionId(obj[6]!=null?Long.parseLong(obj[6].toString()):0L)
				    			.groupId(obj[7]!=null?Long.parseLong(obj[7].toString()):0L)
				    			.projectId(obj[8]!=null?Long.parseLong(obj[8].toString()):0L)
				    			.revision(obj[9]!=null?Integer.parseInt(obj[9].toString()):0)
				    			.auditeeEmpName(employee != null?employee.getEmpName()+", "+employee.getEmpDesigName():"")
				    			.divisionName(division !=null?division.getDivisionName():"")
				    			.groupName(group !=null?group.getGroupName():"")
				    			.projectName(project !=null?project.getProjectName():"")
				    			.build();
				    })
				    .collect(Collectors.toList());
			return finalScheduleDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getScheduleList()"+ e);
			 return Collections.emptyList();
		}
	}



}
