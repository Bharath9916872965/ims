package com.vts.ims.admin.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vts.ims.admin.entity.FormDetail;


@Repository
public interface FormDetailRepo extends JpaRepository<FormDetail, Long> {

//	   @Query(value = """
//	            SELECT DISTINCT b.*, a.serialno
//	            FROM ims_form_module a
//	            JOIN ims_form_detail b ON a.formmoduleid = b.formmoduleid
//	            JOIN ims_form_role_access c ON b.formdetailid = c.formdetailid
//	            WHERE a.isactive = 1
//	              AND c.isactive = 1
//	              AND c.imsformroleid = :imsformroleid
//	            ORDER BY a.serialno
//	            """, nativeQuery = true)
//	    List<FormDetail> findDistinctFormModulesByRoleId(@Param("imsformroleid") Long imsformroleid);
	   @Query(value = """
	            SELECT DISTINCT b.*
	            FROM ims_form_detail b JOIN ims_form_role_access c ON b.formdetailid = c.formdetailid
	            WHERE c.isactive = 1
	              AND c.imsformroleid = :imsformroleid
	            ORDER BY b.formmoduleid, b.formserialno
	            """, nativeQuery = true)
	   List<FormDetail> findDistinctFormModulesDetailsByRoleId(@Param("imsformroleid") Long imsformroleid);
	
}
