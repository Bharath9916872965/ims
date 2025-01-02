package com.vts.ims.master.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CommitteeScheduleDto {

	private Long ScheduleId;
	private String LabCode;
	private Long CommitteeId;
	private Long CommitteeMainId;
	private String MeetingId;
	private Long ProjectId;
	private Long DivisionId; 
	private Long InitiationId; 
	private Long CARSInitiationId;
	private Long RODNameId;
	private LocalDate ScheduleDate;
	private String ScheduleStartTime;
	private String ScheduleFlag;
	private String ScheduleSub;
	private String KickOffOtp;
	private String MeetingVenue;
	private String Confidential;
	private String Reference;
	private String PMRCDecisions;
	private String BriefingPaperFrozen;
	private String MinutesFrozen;
	private String PresentationFrozen;
	private String BriefingStatus;
	private String ScheduleType;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
	private String CommitteeShortName;
}
