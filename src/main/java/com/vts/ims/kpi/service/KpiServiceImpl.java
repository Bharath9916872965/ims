package com.vts.ims.kpi.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ims.kpi.dto.KpiMasterDto;
import com.vts.ims.kpi.dto.KpiObjectiveDto;
import com.vts.ims.kpi.dto.KpiTargetRatingrDto;
import com.vts.ims.kpi.dto.RatingDto;
import com.vts.ims.kpi.modal.ImsKpiObjectiveMaster;
import com.vts.ims.kpi.modal.ImsKpiTargerRating;
import com.vts.ims.kpi.modal.ImsKpiUnit;
import com.vts.ims.kpi.repository.KpiObjMasterRepository;
import com.vts.ims.kpi.repository.KpiTargetRatingRepository;
import com.vts.ims.kpi.repository.KpiUnitRepository;


@Service
public class KpiServiceImpl implements KpiService{
	
	private static final Logger logger = LogManager.getLogger(KpiServiceImpl.class);
	
	@Autowired
	private KpiUnitRepository kpiUnitRepository;
	
	@Autowired
	private KpiObjMasterRepository kpiObjMasterRepository;
	
	@Autowired
	private KpiTargetRatingRepository kpiTargetRatingRepository;
	
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
	public List<KpiMasterDto> getKpiMasterList() throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getKpiUnitList()");
		try {
			List<Object[]> result = kpiObjMasterRepository.getKpiMasterList();
			 return Optional.ofNullable(result).orElse(Collections.emptyList()).stream()
					    .map(obj -> {
						    	return KpiMasterDto.builder()
						    		.kpiId(obj[0]!=null?Long.parseLong(obj[0].toString()):0L)
						    		.kpiObjectives(obj[1]!=null?obj[1].toString():"")
						    		.kpiMerics(obj[2]!=null?obj[2].toString():"")
						    		.kpiTarget(obj[3]!=null?obj[3].toString():"")
						    		.kpiUnitId(obj[4]!=null?Long.parseLong(obj[4].toString()):0L)
						    		.kpiUnitName(obj[5]!=null?obj[5].toString():"")
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
						    		.KpiRating(obj[4]!=null?Long.parseLong(obj[4].toString()):0L)
					    			.build();
					    })
					    .collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getKpiRatingList()"+ e);
			return Collections.emptyList();
		}
	}
	

	}
