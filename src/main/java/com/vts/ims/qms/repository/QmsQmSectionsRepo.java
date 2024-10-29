package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.qms.model.QmsQmSections;

public interface QmsQmSectionsRepo extends JpaRepository<QmsQmSections, Long> {

	@Query("SELECT s FROM QmsQmSections s WHERE s.isActive=1 AND s.sectionId NOT IN (SELECT c.sectionId FROM QmsQmChapters c where c.isActive=1)")
    List<QmsQmSections> findSectionsNotInChapters();
	
}
