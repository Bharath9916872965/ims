package com.vts.ims.audit.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuditCheckListDTO {
    private int scheduleId;
    private int iqaId;
    private String iqaNo;
	private List<CheckListItem> checkListMap;

}

