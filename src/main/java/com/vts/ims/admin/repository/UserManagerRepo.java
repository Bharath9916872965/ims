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

    @Query(value = "SELECT l.LoginId, l.Username, l.Password, l.EmpId, l.DivisionId, l.FormRoleId, l.ImsFormRoleId, l.LoginType, l.IsActive,fr.formrolename " +
            "FROM login l " +
            "JOIN ims_form_role fr ON l.imsformroleId = fr.imsformroleId " +
            "WHERE l.isactive = 1 " +
            "ORDER BY l.LoginId DESC",
            nativeQuery = true)
    public List<Object[]> getUserManagerMasterList();

    public Login findByLoginId(long loginId);

    @Query(value = "SELECT COUNT(*) FROM login WHERE username = :username AND isactive = '1'", nativeQuery = true)
    public Long countByUserNameAndActive(@Param("username") String username);

}
