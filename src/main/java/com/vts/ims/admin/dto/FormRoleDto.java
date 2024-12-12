package com.vts.ims.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FormRoleDto {
    Long roleId;
    String  roleName;
}
