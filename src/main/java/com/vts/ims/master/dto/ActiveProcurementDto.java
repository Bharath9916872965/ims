package com.vts.ims.master.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActiveProcurementDto {

	private String demandNo;
	private LocalDate demandDate;
	private String itemFor;
	private BigDecimal estimatedCost;
	private String projectCode;
	private String divisionCode;
	
}
