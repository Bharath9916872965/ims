package com.vts.ims.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FormroleAccessDto {

    Long formRoleAccessId;
    String formDispName;
    Long formDetailId;
    Long moduleId;
    boolean isActive;
    Long roleId;
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}