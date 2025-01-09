package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.qms.model.QmsQmChaptersRev;

public interface QmsQmChaptersRevRepo extends JpaRepository<QmsQmChaptersRev, Long>{

	@Query("SELECT a FROM QmsQmChaptersRev a WHERE a.revisionRecordId=:revisionRecordId AND a.isActive = 1 ORDER BY a.chapterId ASC")
    List<QmsQmChaptersRev> findAllActiveQmChapters(@Param("revisionRecordId") Long revisionRecordId);
}
