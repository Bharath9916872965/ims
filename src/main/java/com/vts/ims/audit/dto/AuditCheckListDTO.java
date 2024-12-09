package com.vts.ims.audit.dto;

import java.util.List;

import lombok.Data;

@Data
public class AuditCheckListDTO {
    private int scheduleId;
    private int iqaId;
	private List<CheckListItem> checkListMap;

}

