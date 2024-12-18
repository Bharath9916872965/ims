package com.vts.ims.kpi.service;

import java.util.List;

import com.vts.ims.kpi.dto.KpiMasterDto;
import com.vts.ims.kpi.dto.KpiObjectiveDto;
import com.vts.ims.kpi.dto.KpiTargetRatingrDto;
import com.vts.ims.kpi.modal.ImsKpiUnit;
import com.vts.ims.qms.dto.DwpRevisionRecordDto;

public interface KpiService {
	
	public List<ImsKpiUnit> getKpiUnitList() throws Exception;

	public long insertKpi(KpiObjectiveDto kpiObjectiveDto, String username) throws Exception;
	
	public long updateKpi(KpiObjectiveDto kpiObjectiveDto, String username) throws Exception;

	public List<KpiMasterDto> getKpiMasterList(String username)throws Exception;

	public List<KpiTargetRatingrDto> getKpiRatingList()throws Exception;

	public List<DwpRevisionRecordDto> getDwpRevisonList()throws Exception;
}
