package com.vts.ims.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.admin.entity.FormRoleAccess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FormRoleAccessRepo extends JpaRepository<FormRoleAccess, Long> {

    @Query(value = "SELECT b.formRoleAccessId, a.formDetailId, a.formModuleId, a.formDispName, b.isActive " +
            "FROM (SELECT fd.formDetailId, fd.formModuleId, fd.formDispName " +
            "FROM ims_form_detail fd " +
            "WHERE fd.isActive = 1 " +
            "AND CASE WHEN :formModuleId <> '0' THEN fd.formModuleId = :formModuleId ELSE 1 = 1 END) AS a " +
            "LEFT JOIN (SELECT b.formRoleAccessId, b.formDetailId AS 'detailid', b.imsFormRoleId, b.isActive " +
            "FROM ims_form_detail a, ims_form_role_access b " +
            "WHERE a.formDetailId = b.formDetailId " +
            "AND b.imsFormRoleId = :imsFormRoleId " +
            "AND CASE WHEN :formModuleId <> '0' THEN a.formModuleId = :formModuleId ELSE 1 = 1 END) AS b " +
            "ON a.formdetailid = b.detailid",
            nativeQuery = true)
    List<Object[]> getformroleAccessList(@Param("imsFormRoleId") String roleId,
                                         @Param("formModuleId") String formModuleId);

    @Query(value = "SELECT COUNT(formRoleAccessId) " +
            "FROM ims_form_role_access " +
            "WHERE imsFormRoleId = :imsFormRoleId " +
            "AND formDetailId = :formDetailId",
            nativeQuery = true)
    long countByFormRoleIdAndDetailId(@Param("imsFormRoleId") String imsformroleId,
                                      @Param("formDetailId") String detailsid);

}
