package com.vts.ims.qms.repository;

import com.vts.ims.qms.model.DwpChapters;
import com.vts.ims.qms.model.QmsQspChapters;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QmsQspChaptersRepo extends JpaRepository<QmsQspChapters, Long> {

    @Query("SELECT a FROM QmsQspChapters a, QmsQspSections b WHERE b.docName=:docType AND a.sectionId=b.sectionId AND b.isActive=1 AND a.isActive=1")
    List<QmsQspChapters> findAllActiveQspChapters(@Param("docType") String docType);

    List<QmsQspChapters> findByChapterParentIdAndIsActive(Long chapterId, int i);
}
