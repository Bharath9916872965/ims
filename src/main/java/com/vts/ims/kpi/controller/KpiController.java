package com.vts.ims.kpi.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.vts.ims.kpi.dto.KpiMasterDto;
import com.vts.ims.kpi.dto.KpiObjectiveDto;
import com.vts.ims.kpi.dto.KpiTargetRatingrDto;
import com.vts.ims.kpi.modal.ImsKpiUnit;
import com.vts.ims.kpi.service.KpiService;
import com.vts.ims.qms.dto.DwpRevisionRecordDto;
import com.vts.ims.util.Response;



@CrossOrigin("*")
@RestController
public class KpiController {
	private static final Logger logger = LogManager.getLogger(KpiController.class);
	
	@Value("${appStorage}")
	private String storageDrive ;
	
	@Autowired
	KpiService kpiService;
	
	
	@PostMapping(value = "/get-kpi-unit-list", produces = "application/json")
	public ResponseEntity<List<ImsKpiUnit>> getKpiUnitList(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getKpiUnitList" );
			List<ImsKpiUnit> dto = kpiService.getKpiUnitList();
			return new ResponseEntity<List<ImsKpiUnit>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getKpiUnitList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/insert-kpi", produces = "application/json")
	public ResponseEntity<Response> insertKpi(@RequestHeader String username, @RequestBody KpiObjectiveDto kpiObjectiveDto) throws Exception {
		try {
			logger.info( " Inside insert-kpi" );
			 long result=kpiService.insertKpi(kpiObjectiveDto,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("KPI Added Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("KPI Add Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in insert-kpi"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/update-kpi", produces = "application/json")
	public ResponseEntity<Response> updateKpi(@RequestHeader String username, @RequestBody KpiObjectiveDto kpiObjectiveDto) throws Exception {
		try {
			logger.info( " Inside update-kpi" );
			 long result=kpiService.updateKpi(kpiObjectiveDto,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("KPI Edited Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("KPI Edit Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in update-kpi"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/get-kpi-master-list", produces = "application/json")
	public ResponseEntity<List<KpiMasterDto>> getKpiMasterList(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getKpiMasterList" );
			List<KpiMasterDto> dto = kpiService.getKpiMasterList(username);
			return new ResponseEntity<List<KpiMasterDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getKpiMasterList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-kpi-rating-list", produces = "application/json")
	public ResponseEntity<List<KpiTargetRatingrDto>> getKpiRatingList(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getKpiRatingList" );
			List<KpiTargetRatingrDto> dto = kpiService.getKpiRatingList();
			return new ResponseEntity<List<KpiTargetRatingrDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getKpiRatingList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-dwp-revision-list", produces = "application/json")
	public ResponseEntity<List<DwpRevisionRecordDto>> getDwpRevisonList(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getDwpRevisonList" );
			List<DwpRevisionRecordDto> dto = kpiService.getDwpRevisonList();
			return new ResponseEntity<List<DwpRevisionRecordDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getDwpRevisonList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
}
