package com.vts.ims.master.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsDto {
	public Long loginId;
	public String username;
	public Long empId;
	public Long divisionId;
	public Long imsFormRoleId;

}
