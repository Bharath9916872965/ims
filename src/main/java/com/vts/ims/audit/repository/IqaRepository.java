package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.audit.model.Iqa;

public interface IqaRepository extends JpaRepository<Iqa, Long>{
	

    @Query("SELECT a FROM Iqa a WHERE a.isActive = 1 AND EXISTS (" +
           "SELECT 1 FROM AuditSchedule b WHERE b.iqaId = a.iqaId AND b.isActive = 1) " +
           "ORDER BY a.iqaId DESC")
    List<Iqa> getIqaListForDashboard();
    
    @Query(value = "SELECT a.IqaId,a.IqaNo,a.FromDate,a.ToDate,(SELECT COUNT(*) FROM ims_audit_iqa_auditee c WHERE a.IqaId = c.IqaId) AS 'auditees',\r\n"
    		+ "SUM(CASE WHEN d.ScheduleStatus = 'AES' THEN 1 ELSE 0 END) AS 'auditeeSub',SUM(CASE WHEN d.ScheduleStatus = 'ARS' THEN 1 ELSE 0 END) AS 'auditorSub',SUM(CASE WHEN d.ScheduleStatus = 'ABA' THEN 1 ELSE 0 END) AS 'auditeeAcp'\r\n"
    		+ "FROM ims_audit_iqa a LEFT JOIN ims_audit_schedule d ON a.IqaId = d.IqaId WHERE a.IsActive = 1 GROUP BY a.IqaId,a.IqaNo,a.FromDate,a.ToDate ORDER BY a.IqaId DESC",nativeQuery = true)
    public List<Object[]> getIqaScdList();

	
}
