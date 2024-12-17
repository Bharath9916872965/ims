package com.vts.ims.risk.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ims.audit.model.Iqa;
import com.vts.ims.risk.dto.RiskRegisterDto;
import com.vts.ims.risk.model.RiskRegisterModel;
import com.vts.ims.risk.repository.RiskRegisterRepository;


@Service
public class RiskServiceImpl implements RiskService{

	private static final Logger logger=LogManager.getLogger(RiskServiceImpl.class);
	
	
	@Autowired
	RiskRegisterRepository riskRepository;
	
	
	
	
	@Override
	public List<RiskRegisterDto> getRiskRegisterList(Long revisionRecordId) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method getRiskRegisterList()");
		try {
			List<Object[]> riskRegister = riskRepository.findByRevisionRecordId(revisionRecordId);
			List<RiskRegisterDto> finalDto = Optional.ofNullable(riskRegister).orElse(Collections.emptyList()).stream()
				    .map(obj -> {
				    	RiskRegisterDto dto = new RiskRegisterDto();
				    	dto.setRevisionRecordId(Long.parseLong(obj[1].toString()));
				    	dto.setRiskRegisterId(Long.parseLong(obj[0].toString()));
				    	dto.setRiskDescription(obj[2].toString());
				    	dto.setProbability(Integer.parseInt(obj[3].toString()));
				    	dto.setTechnicalPerformance(Integer.parseInt(obj[4].toString()));
				    	dto.setTime(Integer.parseInt(obj[5].toString()));
				    	dto.setCost(Integer.parseInt(obj[6].toString()));
				    	dto.setAverage(Double.parseDouble(obj[7].toString()));
				    	dto.setRiskNo(Double.parseDouble(obj[8].toString()));
				    	dto.setDocType(obj[9].toString());
				    	dto.setIssueNo(Integer.parseInt(obj[10].toString()));
				    	dto.setRevisionNo(Integer.parseInt(obj[11].toString()));
				    	dto.setDescription(obj[12].toString());
				    	dto.setStatusCode(obj[14].toString());
				    	
				    	return dto;
				    	
				    })
				    .sorted(Comparator.comparingLong(RiskRegisterDto::getRiskRegisterId).reversed()) 
				    .collect(Collectors.toList());
			return finalDto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method getRiskRegisterList()"+ e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public long insertRiskRegister(RiskRegisterDto dto, String username) throws Exception {
		logger.info(new Date() + " AuditServiceImpl Inside method insertRiskRegister()");
		long result=0;
		try {
			if(dto!=null && dto.getRiskRegisterId()!=null) {
				Optional<RiskRegisterModel> riskRegister = riskRepository.findById(dto.getRiskRegisterId());
				if(riskRegister.isPresent()) {
				RiskRegisterModel model =riskRegister.get();
				model.setRevisionRecordId(dto.getRevisionRecordId());
				model.setRiskDescription(dto.getRiskDescription());
				model.setProbability(dto.getProbability());
				model.setTechnicalPerformance(dto.getTechnicalPerformance());
				model.setTime(dto.getTime());
				model.setCost(dto.getCost());
				model.setAverage(dto.getAverage());
				model.setRiskNo(dto.getRiskNo());
				model.setModifiedBy(username);
				model.setModifiedDate(LocalDateTime.now());
				model.setIsActive(1);
				result=riskRepository.save(model).getRiskRegisterId();
				}
			}else {
			if(dto!=null) {
				RiskRegisterModel model = new RiskRegisterModel();
				model.setRevisionRecordId(dto.getRevisionRecordId());
				model.setRiskDescription(dto.getRiskDescription());
				model.setProbability(dto.getProbability());
				model.setTechnicalPerformance(dto.getTechnicalPerformance());
				model.setTime(dto.getTime());
				model.setCost(dto.getCost());
				model.setAverage(dto.getAverage());
				model.setRiskNo(dto.getRiskNo());
				model.setCreatedBy(username);
				model.setCreatedDate(LocalDateTime.now());
				model.setIsActive(1);
				result=riskRepository.save(model).getRiskRegisterId();
			}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AuditServiceImpl Inside method insertRiskRegister()"+ e);
			return 0;
		}
	}
}
