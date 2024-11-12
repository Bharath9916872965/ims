package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.qms.model.QmsQmChapters;

public interface QmsQmChaptersRepo extends JpaRepository<QmsQmChapters, Long> {

	@Query("SELECT a FROM QmsQmChapters a WHERE a.isActive = 1 ORDER BY a.chapterId ASC")
    List<QmsQmChapters> findAllActiveQmChapters();
	
	List<QmsQmChapters> findByChapterParentIdAndIsActive(Long chapterParentId, int isActive);
	
}
