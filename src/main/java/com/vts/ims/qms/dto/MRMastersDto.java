package com.vts.ims.qms.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MRMastersDto {

	private Long MRsId;
	private Long EmpId;
	private String MRType;
	private LocalDate MRFrom;
	private LocalDate MRTo;
	private int IsActive;
}
