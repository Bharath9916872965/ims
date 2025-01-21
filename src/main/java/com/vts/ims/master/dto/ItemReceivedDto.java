package com.vts.ims.master.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemReceivedDto {

	private String soNo;
	private LocalDate soDate;
	private String description;
	private Long recievedQty;
	private String rinNo;
	private LocalDate rinDate;
	private Long divisionId;
	private Long projectId;
	private String divisionCode;
	private String projectCode;
}
