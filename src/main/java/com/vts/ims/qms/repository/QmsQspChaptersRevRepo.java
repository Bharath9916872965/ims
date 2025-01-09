package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.qms.model.QmsQspChaptersRev;

public interface QmsQspChaptersRevRepo extends JpaRepository<QmsQspChaptersRev, Long>{

	
	 @Query("SELECT a FROM QmsQspChaptersRev a, QmsQspSections b WHERE a.revisionRecordId=:revisionRecordId AND b.docName=:docType AND a.sectionId=b.sectionId AND b.isActive=1 AND a.isActive=1")
	 List<QmsQspChaptersRev> findAllActiveQspChapters(@Param("docType") String docType,@Param("revisionRecordId") Long revisionRecordId);

	
}
