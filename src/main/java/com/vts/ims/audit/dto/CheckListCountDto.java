package com.vts.ims.audit.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CheckListCountDto {

	private Long addCount;
	private String checkListType;
}
