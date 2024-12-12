package com.vts.ims.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vts.ims.admin.entity.ImsFormRole;

import java.util.List;


@Repository
public interface ImsFormRoleRepo extends JpaRepository<ImsFormRole, Long> {
	
	
	ImsFormRole findByImsFormRoleId(Long imsFormRoleId);

	@Query(value = "SELECT imsFormRoleId, formRoleName, isActive  FROM ims_form_role WHERE isactive='1'",
			nativeQuery = true)
	List<Object[]> getRoleDetails();
}