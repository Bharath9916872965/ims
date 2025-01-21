package com.vts.ims.master.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SupplyOrderDto {

	
	private String soNo;
	private LocalDate soDate;
	private String itemFor;
	private BigDecimal totalCost;
	private String vendorName;
	private String projectCode;
	private String divisionCode;
}
