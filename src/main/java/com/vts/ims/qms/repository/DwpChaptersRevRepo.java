package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.qms.model.DwpChaptersRev;

import feign.Param;

public interface DwpChaptersRevRepo extends JpaRepository<DwpChaptersRev, Long>{

	@Query("SELECT a FROM DwpChaptersRev a, DwpSections b WHERE a.revisionRecordId=:revisionRecordId AND b.groupDivisionId=:groupDivisionId AND b.docType=:docType AND a.sectionId=b.sectionId AND b.isActive=1 AND a.isActive=1")
	List<DwpChaptersRev> findAllActiveDwpChapters(@Param("docType") String docType, @Param("groupDivisionId") Long groupDivisionId,@Param("revisionRecordId") Long revisionRecordId);
	
}
