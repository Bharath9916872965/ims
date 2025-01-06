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

import com.vts.ims.risk.dto.MitigationRiskRegisterDto;
import com.vts.ims.risk.dto.RiskMitigationMergeDto;
import com.vts.ims.risk.dto.RiskRegisterDto;
import com.vts.ims.risk.model.MitigationRiskRegisterModel;
import com.vts.ims.risk.model.RiskRegisterModel;
import com.vts.ims.risk.repository.MitigationRiskRegisterRepo;
import com.vts.ims.risk.repository.RiskRegisterRepository;


@Service
public class RiskServiceImpl implements RiskService{

	private static final Logger logger=LogManager.getLogger(RiskServiceImpl.class);
	
	
	@Autowired
	RiskRegisterRepository riskRepository;
	
	
	@Autowired
	MitigationRiskRegisterRepo mitigationriskRepository;
	
	
	
	@Override
	public List<RiskRegisterDto> getRiskRegisterList(Long revisionRecordId) throws Exception {
		logger.info(new Date() + " RiskServiceImpl Inside method getRiskRegisterList()");
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
			logger.error("RiskServiceImpl Inside method getRiskRegisterList()"+ e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	public long insertRiskRegister(RiskRegisterDto dto, String username) throws Exception {
		logger.info(new Date() + " RiskServiceImpl Inside method insertRiskRegister()");
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
			logger.error("RiskServiceImpl Inside method insertRiskRegister()"+ e);
			return 0;
		}
	}
	
	
	@Override
	public long insertMititgationRiskRegister(MitigationRiskRegisterDto dto, String username) throws Exception {
		logger.info(new Date() + " RiskServiceImpl Inside method insertMititgationRiskRegister()");
		long result=0;
		try {
			if(dto!=null && dto.getMitigationRiskRegisterId()!=null) {
				Optional<MitigationRiskRegisterModel> mitigationRiskRegister = mitigationriskRepository.findById(dto.getMitigationRiskRegisterId());
				if(mitigationRiskRegister.isPresent()) {
			    MitigationRiskRegisterModel model =mitigationRiskRegister.get();
			    if(dto.getAction()!=null && dto.getAction().equalsIgnoreCase("U")) {
				model.setRiskRegisterId(dto.getRiskRegisterId());
				model.setMitigationApproach(dto.getMitigationApproach());
				model.setProbability(dto.getProbability());
				model.setTechnicalPerformance(dto.getTechnicalPerformance());
				model.setTime(dto.getTime());
				model.setCost(dto.getCost());
				model.setAverage(dto.getAverage());
				model.setRiskNo(dto.getRiskNo());
				model.setModifiedBy(username);
				model.setModifiedDate(LocalDateTime.now());
				model.setIsActive(1);
				result=mitigationriskRepository.save(model).getMitigationRiskRegisterId();
				}else if(dto.getAction()!=null && dto.getAction().equalsIgnoreCase("R")) {
					MitigationRiskRegisterModel modelrevise = new MitigationRiskRegisterModel();
					modelrevise.setRiskRegisterId(dto.getRiskRegisterId());
					modelrevise.setMitigationApproach(dto.getMitigationApproach());
					modelrevise.setProbability(dto.getProbability());
					modelrevise.setTechnicalPerformance(dto.getTechnicalPerformance());
					modelrevise.setTime(dto.getTime());
					modelrevise.setCost(dto.getCost());
					modelrevise.setAverage(dto.getAverage());
					modelrevise.setRiskNo(dto.getRiskNo());
					modelrevise.setRevisionNo(model.getRevisionNo()+1);
					modelrevise.setCreatedBy(username);
					modelrevise.setCreatedDate(LocalDateTime.now());
					modelrevise.setIsActive(1);
					result=mitigationriskRepository.save(modelrevise).getRiskRegisterId();
				}
				}
			}else {
			if(dto!=null) {
				MitigationRiskRegisterModel model = new MitigationRiskRegisterModel();
				model.setRiskRegisterId(dto.getRiskRegisterId());
				model.setMitigationApproach(dto.getMitigationApproach());
				model.setProbability(dto.getProbability());
				model.setTechnicalPerformance(dto.getTechnicalPerformance());
				model.setTime(dto.getTime());
				model.setCost(dto.getCost());
				model.setAverage(dto.getAverage());
				model.setRiskNo(dto.getRiskNo());
				model.setRevisionNo(0);
				model.setCreatedBy(username);
				model.setCreatedDate(LocalDateTime.now());
				model.setIsActive(1);
				result=mitigationriskRepository.save(model).getRiskRegisterId();
			}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("RiskServiceImpl Inside method insertMititgationRiskRegister()"+ e);
			return 0;
		}
	}
	
	@Override
	public List<MitigationRiskRegisterDto> getMititgationRiskRegisterlist(long riskRegisterId) throws Exception {
		logger.info(new Date() + " RiskServiceImpl Inside method getMititgationRiskRegisterlist()");
		try {
			List<Object[]> mitigationRiskRegister = mitigationriskRepository.findByRiskRegisterId(riskRegisterId);
			List<MitigationRiskRegisterDto> finalDto = Optional.ofNullable(mitigationRiskRegister).orElse(Collections.emptyList()).stream()
				    .map(obj -> {
				    	MitigationRiskRegisterDto dto = new MitigationRiskRegisterDto();
				    	dto.setMitigationRiskRegisterId(Long.parseLong(obj[0].toString()));
				    	dto.setRiskRegisterId(Long.parseLong(obj[1].toString()));
				    	dto.setMitigationApproach(obj[2].toString());
				    	dto.setProbability(Integer.parseInt(obj[3].toString()));
				    	dto.setTechnicalPerformance(Integer.parseInt(obj[4].toString()));
				    	dto.setTime(Integer.parseInt(obj[5].toString()));
				    	dto.setCost(Integer.parseInt(obj[6].toString()));
				    	dto.setAverage(Double.parseDouble(obj[7].toString()));
				    	dto.setRiskNo(Double.parseDouble(obj[8].toString()));
				    	dto.setRevisionNo(Integer.parseInt(obj[9].toString()));
				    	return dto;
				    	
				    })
				    .sorted(Comparator.comparingLong(MitigationRiskRegisterDto::getMitigationRiskRegisterId).reversed()) 
				    .collect(Collectors.toList());
			return finalDto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("RiskServiceImpl Inside method getMititgationRiskRegisterlist()"+ e);
			return Collections.emptyList();
		}
	}
	@Override
	public List<RiskMitigationMergeDto> getRegMitigationList(Long groupDivisionId,String docType) throws Exception {
		logger.info(new Date() + " RiskServiceImpl Inside method getRiskRegisterList()");
		try {
			List<Object[]> riskRegister = riskRepository.getRegMitigationList(groupDivisionId,docType);
			List<RiskMitigationMergeDto> finalDto = Optional.ofNullable(riskRegister).orElse(Collections.emptyList()).stream()
				    .map(obj -> {
				    	RiskMitigationMergeDto dto = new RiskMitigationMergeDto();
				    	dto.setRiskRegisterId(Long.parseLong(obj[0].toString()));
				    	dto.setRevisionRecordId(Long.parseLong(obj[1].toString()));
				    	dto.setRiskDescription(obj[2].toString());
				    	dto.setProbability(Integer.parseInt(obj[3].toString()));
				    	dto.setTechnicalPerformance(Integer.parseInt(obj[4].toString()));
				    	dto.setTime(Integer.parseInt(obj[5].toString()));
				    	dto.setCost(Integer.parseInt(obj[6].toString()));
				    	dto.setAverage(Double.parseDouble(obj[7].toString()));
				    	dto.setRiskNo(Double.parseDouble(obj[8].toString()));
				    	dto.setDocType(obj[9].toString());
				    	dto.setGroupDivisionId(Integer.parseInt(obj[10].toString()));
				   		dto.setDescription(obj[11].toString());
				   		if (obj[12] != null) {
				   		    dto.setMitigationApproach(obj[12].toString());
				   		} else {
				   		    dto.setMitigationApproach("-"); 
				   		}
				   		if (obj[13] != null) {
				   			dto.setMitigationProbability(Integer.parseInt(obj[13].toString()));
				   		} else {
				   		    dto.setMitigationApproach("-"); 
				   		}
				   		if (obj[14] != null) {
				   			dto.setMitigationTp(Integer.parseInt(obj[14].toString()));
				   		} else {
				   		    dto.setMitigationApproach("-"); 
				   		}
				   		if (obj[15] != null) {
				   			dto.setMitigationTime(Integer.parseInt(obj[15].toString()));
				   		} else {
				   		    dto.setMitigationApproach("-"); 
				   		}
				   		if (obj[16] != null) {
				   			dto.setMitigationCost(Integer.parseInt(obj[16].toString()));
				   		} else {
				   		    dto.setMitigationApproach("-"); 
				   		}
				   		if (obj[17] != null) {
				   			dto.setMitigationAverage(Double.parseDouble(obj[17].toString()));
				   		} else {
				   		    dto.setMitigationApproach("-"); 
				   		}
				   		
				   		if (obj[18] != null) {
				   	   		dto.setMitigationRiskNo(Double.parseDouble(obj[18].toString()));
				   		} else {
				   		    dto.setMitigationApproach("-"); 
				   		}
				   		
				    					    	return dto;
				    	
				    })
				    .sorted(Comparator.comparingLong(RiskMitigationMergeDto::getRiskRegisterId).reversed()) 
				    .collect(Collectors.toList());
			return finalDto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("RiskServiceImpl Inside method getRiskRegisterList()"+ e);
			return Collections.emptyList();
		}
	}
}
