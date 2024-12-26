package com.vts.ims.admin.dto;

import lombok.Data;

@Data
public class UserManageAddEditDto {
    private Long loginId;
    private String userName;
    private Long divisionId;
    private Long roleId;
    private String logintypeId;
    private Long empId;
}
