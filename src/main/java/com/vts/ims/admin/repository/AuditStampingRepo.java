package com.vts.ims.admin.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vts.ims.model.LoginStamping;



@Repository
public interface AuditStampingRepo  extends JpaRepository<LoginStamping, Long> {

	
	LoginStamping findByAuditStampingId(Long auditStampingId);
	
	
    @Query(value = "SELECT a.auditstampingid FROM ims_auditstamping a WHERE a.auditstampingid = (SELECT MAX(b.auditstampingid) FROM ims_auditstamping b WHERE b.loginid = :loginid)"
    		, nativeQuery = true)
    Optional<Long> findLastLoginStampingId(@Param("loginid") long loginId);
	
	
	
	@Query(value = "SELECT a.auditStampingId,a.loginId,a.username,a.ipAddress,a.macAddress, a.logindate,a.LoginDateTime,a.LogOutDateTime,a.LogOutType,CAST(TIME(a.logindatetime) AS CHAR) AS logintime, a.ipaddress , (CASE WHEN a.logouttype = 'L' THEN 'Logout' WHEN a.logouttype = 'S' THEN 'Session Expired' ELSE '--'  END) AS logOutTypeDisp FROM ims_auditstamping a ,login b WHERE a.loginid =  b.loginid AND a.logindate BETWEEN :fromdate AND :todate AND a.loginId =:loginid  ORDER BY a.LoginDateTime DESC "
			,nativeQuery = true)
	public List<Object[]> getAuditStampingList(long loginid,Date fromdate, Date todate );
	
	
}
