package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.qms.model.DwpChapters;
import com.vts.ims.qms.model.QmsQmChapters;

import feign.Param;

public interface DwpChaptersRepo extends JpaRepository<DwpChapters, Long> {

	@Query("SELECT a FROM DwpChapters a, DwpSections b WHERE a.chapterParentId=0 AND b.divisionId=:divisionId AND a.sectionId=b.sectionId AND b.isActive=1 AND a.isActive=1")
    List<DwpChapters> findAllActiveDwpChapters(@Param("divisionId") Long divisionId);
	
	List<DwpChapters> findByChapterParentIdAndIsActive(Long chapterParentId, int isActive);
	
}
