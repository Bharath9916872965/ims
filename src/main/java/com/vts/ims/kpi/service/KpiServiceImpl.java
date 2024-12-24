package com.vts.ims.kpi.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

import com.vts.ims.kpi.dto.KpiMasterDto;
import com.vts.ims.kpi.dto.KpiObjDto;
import com.vts.ims.kpi.dto.KpiObjListDto;
import com.vts.ims.kpi.dto.KpiObjRatingDto;
import com.vts.ims.kpi.dto.KpiObjectiveDto;
import com.vts.ims.kpi.dto.KpiTargetRatingrDto;
import com.vts.ims.kpi.dto.RatingDto;
import com.vts.ims.kpi.modal.ImsKpiObjective;
import com.vts.ims.kpi.modal.ImsKpiObjectiveMaster;
import com.vts.ims.kpi.modal.ImsKpiTargerRating;
import com.vts.ims.kpi.modal.ImsKpiUnit;
import com.vts.ims.kpi.repository.KpiObjMasterRepository;
import com.vts.ims.kpi.repository.KpiObjectiveRepository;
import com.vts.ims.kpi.repository.KpiTargetRatingRepository;
import com.vts.ims.kpi.repository.KpiUnitRepository;
import com.vts.ims.login.Login;
import com.vts.ims.login.LoginRepository;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.qms.dto.DwpRevisionRecordDto;
import com.vts.ims.qms.model.DwpRevisionRecord;
import com.vts.ims.qms.repository.DwpRevisionRecordRepo;


@Service
public class KpiServiceImpl implements KpiService{
	
	private static final Logger logger = LogManager.getLogger(KpiServiceImpl.class);
	
	@Value("${x_api_key}")
	private String xApiKey;
	
	@Autowired
	LoginRepository loginRepo;
	
	@Autowired
	private MasterClient masterClient;
	
	@Autowired
	private KpiUnitRepository kpiUnitRepository;
	
	@Autowired
	private KpiObjMasterRepository kpiObjMasterRepository;
	
	@Autowired
	private KpiTargetRatingRepository kpiTargetRatingRepository;
	
	@Autowired
	private DwpRevisionRecordRepo dwpRevisionRecordRepo;
	
	@Autowired
	private KpiObjectiveRepository kpiObjectiveRepository;
	
	@Override
	public List<ImsKpiUnit> getKpiUnitList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getKpiUnitList()");
		try {
			return kpiUnitRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getKpiUnitList()"+ e);
			return Collections.emptyList();
		}
	}

	@Override
	public long insertKpi(KpiObjectiveDto kpiObjectiveDto, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method insertKpi()");
		long result=0;
		try {
			
			ImsKpiObjectiveMaster kpiMas = new ImsKpiObjectiveMaster();
			
			List<ImsKpiTargerRating> targetList = new ArrayList<ImsKpiTargerRating>();
			
			kpiMas.setKpiObjectives(kpiObjectiveDto.getObjective());
			kpiMas.setKpiMerics(kpiObjectiveDto.getMetrics());
			kpiMas.setKpiTarget(Long.parseLong(kpiObjectiveDto.getTarget()));
			kpiMas.setRevisionRecordId(Long.parseLong(kpiObjectiveDto.getRevisionRecordId()));
			kpiMas.setKpiUnitId(kpiObjectiveDto.getKpiUnitId());
			kpiMas.setCreatedBy(username);
			kpiMas.setCreatedDate(LocalDateTime.now());
			kpiMas.setIsActive(1);
			
			for(RatingDto dto : kpiObjectiveDto.getRatings()) {
				
				ImsKpiTargerRating tarRating = new ImsKpiTargerRating();
				
				tarRating.setStartValue(Long.parseLong(dto.getStartValue()));
				tarRating.setEndValue(Long.parseLong(dto.getEndValue()));
				tarRating.setKpiRating(Long.parseLong(dto.getRating()));
				tarRating.setImsKpiObjectiveMaster(kpiMas);
				tarRating.setCreatedBy(username);
				tarRating.setCreatedDate(LocalDateTime.now());
				tarRating.setIsActive(1);
				targetList.add(tarRating);
			}
			kpiMas.setImsKpiTargerRating(targetList);
			
			result = kpiObjMasterRepository.save(kpiMas).getKpiId();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method insertKpi()"+ e);
			return 0;
		}
	}
	
	@Override
	public long updateKpi(KpiObjectiveDto kpiObjectiveDto, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method updateKpi()");
		long result=0;
		try {
			result = kpiTargetRatingRepository.deleteRatings(kpiObjectiveDto.getKpiId());
			if(result > 0) {
				ImsKpiObjectiveMaster kpiMas = kpiObjMasterRepository.findById(kpiObjectiveDto.getKpiId()).get();
				
				List<ImsKpiTargerRating> targetList = new ArrayList<ImsKpiTargerRating>();
				
				kpiMas.setKpiObjectives(kpiObjectiveDto.getObjective());
				kpiMas.setKpiMerics(kpiObjectiveDto.getMetrics());
				kpiMas.setKpiTarget(Long.parseLong(kpiObjectiveDto.getTarget()));
				kpiMas.setKpiUnitId(kpiObjectiveDto.getKpiUnitId());
				kpiMas.setModifiedBy(username);
				kpiMas.setModifiedDate(LocalDateTime.now());
				
				for(RatingDto dto : kpiObjectiveDto.getRatings()) {
					
					ImsKpiTargerRating tarRating = new ImsKpiTargerRating();
					
					tarRating.setStartValue(Long.parseLong(dto.getStartValue()));
					tarRating.setEndValue(Long.parseLong(dto.getEndValue()));
					tarRating.setKpiRating(Long.parseLong(dto.getRating()));
					tarRating.setImsKpiObjectiveMaster(kpiMas);
					tarRating.setCreatedBy(username);
					tarRating.setCreatedDate(LocalDateTime.now());
					tarRating.setIsActive(1);
					targetList.add(tarRating);
				}
				kpiMas.setImsKpiTargerRating(targetList);
				
				result = kpiObjMasterRepository.save(kpiMas).getKpiId();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method updateKpi()"+ e);
			return 0;
		}
	}

	@Override
	public List<KpiMasterDto> getKpiMasterList(String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getKpiUnitList()");
		try {
	    	Login login = loginRepo.findByUsername(username);
			EmployeeDto employeeLogIn = masterClient.getEmployee(xApiKey,login.getEmpId()).get(0);
			List<Object[]> result = kpiObjMasterRepository.getKpiMasterList();
			List<DivisionMasterDto> divisionMaster = masterClient.getDivisionMaster(xApiKey);
			List<DivisionGroupDto> groupList = masterClient.getDivisionGroupList(xApiKey);
			
		    Map<Long,DivisionMasterDto> divisionMap = divisionMaster.stream()
		    		.filter(division -> division.getDivisionId() !=null)
		    		.collect(Collectors.toMap(DivisionMasterDto::getDivisionId, division -> division));
		    
		    Map<Long,DivisionGroupDto> groupMap = groupList.stream()
		    		.filter(group -> group.getGroupId() !=null)
		    		.collect(Collectors.toMap(DivisionGroupDto::getGroupId, group -> group));
		    
			 return Optional.ofNullable(result).orElse(Collections.emptyList()).stream()
					    .map(obj -> {
					    	String divisionGroupCode = "";
					    	if(obj[8].toString().equalsIgnoreCase("LAB")) {
					    		divisionGroupCode = employeeLogIn.getLabCode();
					    	}else if(obj[8].toString().equalsIgnoreCase("dwp")){
							    DivisionMasterDto division = obj[7] !=null ? divisionMap.get(Long.parseLong(obj[7].toString())):null;
							    if(division != null) {
							    	divisionGroupCode = division.getDivisionCode();
							    }

					    	}else {
							    DivisionGroupDto group = obj[7] !=null ? groupMap.get(Long.parseLong(obj[7].toString())):null;
							    
							    if(group != null) {
							    	divisionGroupCode = group.getGroupCode();
							    }
					    	}
						    	return KpiMasterDto.builder()
						    		.kpiId(obj[0]!=null?Long.parseLong(obj[0].toString()):0L)
						    		.kpiObjectives(obj[1]!=null?obj[1].toString():"")
						    		.kpiMerics(obj[2]!=null?obj[2].toString():"")
						    		.kpiTarget(obj[3]!=null?obj[3].toString():"")
						    		.kpiUnitId(obj[4]!=null?Long.parseLong(obj[4].toString()):0L)
						    		.kpiUnitName(obj[5]!=null?obj[5].toString():"")
						    		.revisionRecordId(obj[6]!=null?obj[6].toString():"")
						    		.groupDivisionId(obj[7]!=null?Long.parseLong(obj[7].toString()):0L)
						    		.docType(obj[8]!=null?obj[8].toString():"")
						    		.groupDivisionCode(divisionGroupCode !=null ? divisionGroupCode:"")
					    			.build();
					    })
					    .collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getKpiUnitList()"+ e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<KpiTargetRatingrDto> getKpiRatingList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getKpiRatingList()");
		try {
			List<Object[]> result = kpiTargetRatingRepository.getTargetRatingList();
			 return Optional.ofNullable(result).orElse(Collections.emptyList()).stream()
					    .map(obj -> {
						    	return KpiTargetRatingrDto.builder()
						    		.kpiTargetRatingId(obj[0]!=null?Long.parseLong(obj[0].toString()):0L)
						    		.kpiId(obj[1]!=null?Long.parseLong(obj[1].toString()):0L)
						    		.startValue(obj[2]!=null?Long.parseLong(obj[2].toString()):0L)
						    		.endValue(obj[3]!=null?Long.parseLong(obj[3].toString()):0L)
						    		.kpiRating(obj[4]!=null?Long.parseLong(obj[4].toString()):0L)
					    			.build();
					    })
					    .collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getKpiRatingList()"+ e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<KpiObjRatingDto> getKpiObjRatingList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getKpiObjRatingList()");
		try {
			List<Object[]> result = kpiObjectiveRepository.getKpiObjRatingList();
			 return Optional.ofNullable(result).orElse(Collections.emptyList()).stream()
					    .map(obj -> {
						    	return KpiObjRatingDto.builder()
						    		.kpiObjectiveId(obj[0]!=null?Long.parseLong(obj[0].toString()):0L)
						    		.kpiId(obj[1]!=null?Long.parseLong(obj[1].toString()):0L)
						    		.kpiValue(obj[2]!=null?obj[2].toString():"")
						    		.kpiRating(obj[3]!=null?Long.parseLong(obj[3].toString()):0L)
						    		.iqaId(obj[4]!=null?Long.parseLong(obj[4].toString()):0L)
						    		.revisionRecordId(obj[5]!=null?Long.parseLong(obj[5].toString()):0L)
						    		.actEmpId(obj[6]!=null?Long.parseLong(obj[6].toString()):0L)
						    		.kpiObjectives(obj[7]!=null?obj[7].toString():"")
						    		.kpiMerics(obj[8]!=null?obj[8].toString():"")
						    		.kpiUnitId(obj[9]!=null?Long.parseLong(obj[9].toString()):0L)
						    		.kpiUnitName(obj[10]!=null?obj[10].toString():"")
						    		.kpiTarget(obj[11]!=null?obj[11].toString():"") 
						    		.iqaNo(obj[12]!=null?obj[12].toString():"") 
					    			.build();
					    })
					    .collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getKpiObjRatingList()"+ e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<DwpRevisionRecordDto> getDwpRevisonList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getKpiUnitList()");
		try {
			List<DivisionMasterDto> divisionDtoList = masterClient.getDivisionMaster(xApiKey);
			List<DivisionGroupDto> divisiongroupDtoList = masterClient.getDivisionGroupList(xApiKey);
			
			List<DwpRevisionRecordDto> revisionRecordDtoList = new ArrayList<DwpRevisionRecordDto>();
			
			List<DwpRevisionRecord> revisionRecord = dwpRevisionRecordRepo.findAll();
			
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
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getKpiUnitList()"+ e);
			return Collections.emptyList();
		}
	}

	@Override
	public long insertKpiObjective(KpiObjListDto kpiObjListDto, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method insertKpiObjective()");
		long result = 0;
		try {
			Login login = loginRepo.findByUsername(username);
			
			for(KpiObjDto dto : kpiObjListDto.getRatingList()) {
				
				ImsKpiObjective objective = new ImsKpiObjective();
				
				objective.setKpiId(dto.getKpiId());
				objective.setKpiValue(Long.parseLong(dto.getKpiValue()));
				objective.setKpiRating(dto.getKpiRating());
				objective.setIqaId(kpiObjListDto.getIqaId());
				objective.setRevisionRecordId(kpiObjListDto.getRevisionRecordId());
				objective.setActEmpId(login.getEmpId());
				objective.setCreatedBy(username);
				objective.setCreatedDate(LocalDateTime.now());
				objective.setIsActive(1);
				
				result = kpiObjectiveRepository.save(objective).getKpiObjectiveId();
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method insertKpiObjective()"+ e);
		}
		return result;
	}
	
	@Override
	public int updateKpiObjective(KpiObjListDto kpiObjListDto, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method updateKpiObjective()");
		int result = 0;
		try {
			Login login = loginRepo.findByUsername(username);
			
			for(KpiObjDto dto : kpiObjListDto.getRatingList()) {
				
				result = kpiObjectiveRepository.updateKpiObjRatings(dto.getKpiValue(),dto.getKpiRating(),username,LocalDateTime.now(),dto.getKpiObjectiveId());
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method updateKpiObjective()"+ e);
		}
		return result;
	}
	

	}
