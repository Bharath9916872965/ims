package com.vts.ims.master.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ActionAssignDto {

	private Long ScheduleMinutesId;
	private Long ScheduleId;
	private String ActionItem;
	private LocalDate PDCOrg;
	private int Progress;
}
