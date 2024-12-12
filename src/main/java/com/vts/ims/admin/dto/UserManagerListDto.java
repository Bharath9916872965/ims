package com.vts.ims.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserManagerListDto {

    private Long loginId;
    private String username;
    private String password;
    private Long empId;
    private Long divisionId;
    private Long imsFormRoleId;
    private String loginType;
    private int isActive;
    private String formRoleName;
    private String empDesig;
    private String empName;
    private String empDivCode;
    private String empLabCode;



}