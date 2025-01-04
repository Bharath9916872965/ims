package com.vts.ims.kpi.service;

import java.util.List;

import com.vts.ims.kpi.dto.GroupDivisionDto;
import com.vts.ims.kpi.dto.KpiMasterDto;
import com.vts.ims.kpi.dto.KpiObjListDto;
import com.vts.ims.kpi.dto.KpiObjRatingDto;
import com.vts.ims.kpi.dto.KpiObjectiveDto;
import com.vts.ims.kpi.dto.KpiTargetRatingrDto;
import com.vts.ims.kpi.modal.ImsKpiUnit;

public interface KpiService {
	
	public List<ImsKpiUnit> getKpiUnitList() throws Exception;

	public long insertKpi(KpiObjectiveDto kpiObjectiveDto, String username) throws Exception;
	
	public long updateKpi(KpiObjectiveDto kpiObjectiveDto, String username) throws Exception;

	public List<KpiMasterDto> getKpiMasterList(String username)throws Exception;

	public List<KpiTargetRatingrDto> getKpiRatingList()throws Exception;
	
	public List<KpiObjRatingDto> getKpiObjRatingList()throws Exception;

	public List<GroupDivisionDto> getGroupDivisionList()throws Exception;

	public long insertKpiObjective(KpiObjListDto kpiObjListDto, String username)throws Exception;
	
	public int updateKpiObjective(KpiObjListDto kpiObjListDto, String username)throws Exception;
}
