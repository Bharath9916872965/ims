package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vts.ims.qms.model.DwpSections;

import feign.Param;

public interface DwpSectionsRepo extends JpaRepository<DwpSections, Long> {

	@Query("SELECT s FROM DwpSections s WHERE s.isActive=1 AND s.docType=:docType AND s.groupDivisionId=:groupDivisionId AND s.sectionId NOT IN (SELECT c.sectionId FROM DwpSections b, DwpChapters c where c.isActive=1 AND b.isActive=1 AND c.sectionId=b.sectionId AND b.docType=:docType AND b.groupDivisionId=:groupDivisionId)")
    List<DwpSections> findSectionsNotInChapters(@Param("docType") String docType, @Param("groupDivisionId") Long groupDivisionId);

    List<DwpSections> findAllByGroupDivisionIdAndDocTypeAndIsActive(long groupDivisionId,String docType,int isActive);
}
