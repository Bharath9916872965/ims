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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ims.audit.dto.AuditTeamEmployeeDto;
import com.vts.ims.audit.dto.AuditTeamMembersDto;
import com.vts.ims.audit.dto.AuditeeDto;
import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.dto.AuditorTeamDto;
import com.vts.ims.audit.dto.IqaDto;
import com.vts.ims.audit.model.AuditTeam;
import com.vts.ims.audit.model.AuditTeamMembers;
import com.vts.ims.audit.model.Auditee;
import com.vts.ims.audit.model.Auditor;
import com.vts.ims.audit.model.Iqa;
import com.vts.ims.audit.repository.AuditeeRepository;
import com.vts.ims.audit.repository.AuditorRepository;
import com.vts.ims.audit.repository.IqaRepository;
import com.vts.ims.audit.repository.TeamMemberRepository;
import com.vts.ims.audit.repository.TeamRepository;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;


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
	TeamMemberRepository teamMemberRepository;
	
	@Autowired
	private MasterClient masterClient;
	
	@Override
	public List<AuditorDto> getAuditorList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getAuditorList()");
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
			logger.error("AuditServiceImpl Inside method getAuditorList()"+ e);
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
	                    }
	                } else {
	                    Auditor model = new Auditor();
	                    model.setEmpId(empId);
	                    model.setCreatedBy(username);
	                    model.setCreatedDate(LocalDateTime.now());
	                    model.setIsActive(1);
	                    result = auditRepository.save(model).getAuditorId();
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
				    	dto.setProjectCode(project !=null?project.getProjectCode():"");
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
	public List<AuditorTeamDto> getAuditTeamMainList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getAuditTeamList()");
		try {
			List<AuditTeam> auditTeamList=teamRepository.findAllByIsActive(1);
			List<AuditorTeamDto> finalAuditTeamList  = auditTeamList.stream()
				    .map(obj -> {
				    	AuditorTeamDto dto = new AuditorTeamDto();
				        dto.setIqaId(obj.getIqaId());
				        dto.setTeamId(obj.getTeamId());
				        dto.setTeamCode(obj.getTeamCode());
				        dto.setIsActive(obj.getIsActive());
				        return dto;
				    })
				    .collect(Collectors.toList());
			return finalAuditTeamList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getAuditTeamList()"+ e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public List<AuditorDto> getAuditorIsActiveList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getAuditorList()");
		try {
			List<Auditor> auditors = auditRepository.findAllByIsActive(1);
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
				        return auditorDto;
				    })
				    .collect(Collectors.toList());
			return finalDto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getAuditorList()"+ e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<AuditTeamMembersDto> getTeamMmberIsActiveList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getTeamMmberIsActiveList()");
		try {
			List<AuditTeamMembers> teamMembers = teamMemberRepository.findAllByIsActive(1);
			List<AuditTeam> auditTeamList=teamRepository.findAllByIsActive(1);
			Map<Long, AuditTeam> auditTeamMap = auditTeamList.stream()
		            .filter(auditteam -> auditteam.getTeamId() != null)
		            .collect(Collectors.toMap(AuditTeam::getTeamId, auditteam -> auditteam));
			List<AuditTeamMembersDto> finalDto = teamMembers.stream()
				    .map(obj -> {
				    	AuditTeam team= auditTeamMap.get(obj.getTeamId());
				    	AuditTeamMembersDto teamMemberDto= new AuditTeamMembersDto();
				    	teamMemberDto.setTeamMemberId(obj.getTeamMemberId());
				    	teamMemberDto.setAuditorId(obj.getAuditorId());
				    	teamMemberDto.setTeamId(obj.getTeamId());
				    	teamMemberDto.setIIsLead(obj.getIIsLead());	
				    	teamMemberDto.setIqaId(team.getIqaId());
				    	return teamMemberDto;
				    })
				    .collect(Collectors.toList());
			return finalDto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getTeamMmberIsActiveList()"+ e);
			return Collections.emptyList();
		}
		
	}
	
	
	@Modifying
    @Transactional
	@Override
	public long insertAuditTeam(AuditorTeamDto auditormemberteamdto, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method insertAuditTeam()");
		long result=0;
		try {
			if(auditormemberteamdto!=null && auditormemberteamdto.getTeamId()!=null) {
				int delete =teamMemberRepository.deleteByTeamId(auditormemberteamdto.getTeamId());
				if(delete>0) {
					AuditTeamMembers teamleadmodel=new AuditTeamMembers();
					teamleadmodel.setTeamId(auditormemberteamdto.getTeamId());
					teamleadmodel.setAuditorId(auditormemberteamdto.getTeamLeadEmpId());
					teamleadmodel.setIIsLead(1L);
					teamleadmodel.setCreatedBy(username);
					teamleadmodel.setCreatedDate(LocalDateTime.now());
					teamleadmodel.setIsActive(1);
					teamleadmodel.setIsActive(1);
					result=teamMemberRepository.save(teamleadmodel).getTeamMemberId();
					if(auditormemberteamdto.getTeamMemberEmpId()!=null && auditormemberteamdto.getTeamMemberEmpId().length>0) {
						for(int i=0;i<auditormemberteamdto.getTeamMemberEmpId().length;i++) {
							AuditTeamMembers teammembermodel=new AuditTeamMembers();
							teammembermodel.setTeamId(auditormemberteamdto.getTeamId());
							teammembermodel.setAuditorId(auditormemberteamdto.getTeamMemberEmpId()[i]);
							teammembermodel.setIIsLead(0L);
							teammembermodel.setCreatedBy(username);
							teammembermodel.setCreatedDate(LocalDateTime.now());
							teammembermodel.setIsActive(1);
							teamMemberRepository.save(teammembermodel);
						}
					}
				}
			}else {
				AuditTeam model=new AuditTeam();
				model.setTeamCode(auditormemberteamdto.getTeamCode());
				model.setIqaId(auditormemberteamdto.getIqaId());
				model.setCreatedBy(username);
				model.setCreatedDate(LocalDateTime.now());
				model.setIsActive(1);
				result=teamRepository.save(model).getTeamId();
				if(result>0) {
				AuditTeamMembers teamleadmodel=new AuditTeamMembers();
				teamleadmodel.setTeamId(result);
				teamleadmodel.setAuditorId(auditormemberteamdto.getTeamLeadEmpId());
				teamleadmodel.setIIsLead(1L);
				teamleadmodel.setCreatedBy(username);
				teamleadmodel.setCreatedDate(LocalDateTime.now());
				teamleadmodel.setIsActive(1);
				teamleadmodel.setIsActive(1);
				teamMemberRepository.save(teamleadmodel);
				if(auditormemberteamdto.getTeamMemberEmpId()!=null && auditormemberteamdto.getTeamMemberEmpId().length>0) {
					for(int i=0;i<auditormemberteamdto.getTeamMemberEmpId().length;i++) {
						AuditTeamMembers teammembermodel=new AuditTeamMembers();
						teammembermodel.setTeamId(result);
						teammembermodel.setAuditorId(auditormemberteamdto.getTeamMemberEmpId()[i]);
						teammembermodel.setIIsLead(0L);
						teammembermodel.setCreatedBy(username);
						teammembermodel.setCreatedDate(LocalDateTime.now());
						teammembermodel.setIsActive(1);
						teamMemberRepository.save(teammembermodel);
					}
				}
			  }
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method insertAuditTeam()"+ e);
			return result;
		}
	}
	
	
	@Override
	public List<AuditTeamEmployeeDto> getauditteammemberlist() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getauditteammemberlist()");
		try {
			List<Object[]> getAuditTeamMemberList=teamRepository.getAuditTeamMemberList();
			List<EmployeeDto> totalEmployee = masterClient.getEmployeeMasterList("VTS");
		    Map<Long, EmployeeDto> employeeMap = totalEmployee.stream()
		            .filter(employee -> employee.getEmpId() != null)
		            .collect(Collectors.toMap(EmployeeDto::getEmpId, employee -> employee));
			List<AuditTeamEmployeeDto> finalAuditTeamMemberList  = getAuditTeamMemberList.stream()
					.map(obj -> {
					    EmployeeDto employee =	obj[0] != null?employeeMap.get(obj[0]):null;
					    AuditTeamEmployeeDto dto = new AuditTeamEmployeeDto();
					    dto.setTeamMemberIds(Long.parseLong(obj[6].toString()));
					    dto.setTeamId(Long.parseLong(obj[5].toString()));
					    dto.setTeamMembers(employee != null?employee.getEmpName()+", "+employee.getEmpDesigName():"");
					    dto.setIsLead(Long.parseLong(obj[3].toString()));
					    dto.setAuditorId(Long.parseLong(obj[4].toString()));
				        return dto;
				    })
				    .collect(Collectors.toList());
			return finalAuditTeamMemberList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getauditteammemberlist()"+ e);
			return Collections.emptyList();
		}
	}
}
