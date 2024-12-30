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

	
}
