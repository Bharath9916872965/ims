package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.qms.model.QmsAbbreviations;

public interface QmsAbbreviationsRepo extends JpaRepository<QmsAbbreviations, Long> {

//	@Query(value = """
//		    SELECT abr.*
//		    FROM ims_qms_abbreviation abr
//		    WHERE NOT EXISTS (
//		        SELECT 1 FROM ims_qms_qm_revision_record vr
//		        WHERE vr.RevisionRecordId = :revisionRecordId
//		        AND FIND_IN_SET(abr.AbbreviationId, vr.AbbreviationIdNotReq) > 0
//		    )
//		    ORDER BY abr.Abbreviation
//		""", nativeQuery = true)
//		List<QmsAbbreviations> findValidAbbreviations(@Param("revisionRecordId") Long revisionRecordId);
	
	@Query(value = """
		    SELECT abr.*
		    FROM ims_qms_abbreviation abr
		    WHERE NOT FIND_IN_SET(abr.AbbreviationId, :abbreviationIdNotReq)
		    ORDER BY abr.Abbreviation
		""", nativeQuery = true)
		List<QmsAbbreviations> findValidAbbreviations(@Param("abbreviationIdNotReq") String abbreviationIdNotReq);



}
