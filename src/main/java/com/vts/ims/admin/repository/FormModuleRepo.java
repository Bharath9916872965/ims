package com.vts.ims.admin.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vts.ims.admin.entity.FormDetail;
import com.vts.ims.admin.entity.FormModule;



@Repository
public interface FormModuleRepo extends JpaRepository<FormModule, Long> {

	@Query(value = """
            SELECT DISTINCT a.*
            FROM ims_form_module a
            JOIN ims_form_detail b ON a.formmoduleid = b.formmoduleid
            JOIN ims_form_role_access c ON b.formdetailid = c.formdetailid
            WHERE a.isactive = 1
              AND c.isactive = 1
              AND c.imsformroleid = :imsformroleid
            ORDER BY a.serialno
            """, nativeQuery = true)
    List<FormModule> findDistinctFormModulesByRoleId(@Param("imsformroleid") Long imsformroleid);

    @Query(value = "SELECT formModuleId , formModuleName FROM ims_form_module  WHERE isActive=1",
            nativeQuery = true)
    public List<Object[]> getformModulelist();
}