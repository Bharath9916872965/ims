package com.vts.ims.login;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LoginRepository extends JpaRepository<Login, Long> {
	Login findByUsername(String Username);
	
	@Query(value = "SELECT a.LoginId, a.Username, a.EmpId, a.DivisionId, a.ImsFormRoleId,b.FormRoleName FROM login a, ims_form_role b WHERE a.ImsFormRoleId = b.ImsFormRoleId AND a.Username = :Username",nativeQuery = true)
	public List<Object[]> getLoginDetails(@Param("Username")String username);
}
