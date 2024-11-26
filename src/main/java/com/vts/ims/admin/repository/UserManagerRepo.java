package com.vts.ims.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vts.ims.login.Login;

@Repository
public interface UserManagerRepo extends JpaRepository<Login, Long> {
	
	
    @Query(value = "SELECT l.loginId FROM login l WHERE l.EmpId = :empid LIMIT 1", nativeQuery = true)
    public Long findLoginIdByEmpId(@Param("empid") long empId);
	
	
}
