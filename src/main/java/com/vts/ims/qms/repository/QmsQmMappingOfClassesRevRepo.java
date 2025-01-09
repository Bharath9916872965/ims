package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.qms.model.QmsQmMappingOfClassesRev;

public interface QmsQmMappingOfClassesRevRepo extends JpaRepository<QmsQmMappingOfClassesRev, Long>{

	@Query(value = "SELECT t1.SectionNo, t1.ClauseNo, t1.MocDescription FROM ims_qms_qm_mapping_classes_rev t1 WHERE t1.revisionRecordId = :revisionRecordId AND MocParentId = 0", nativeQuery = true)
	List<Object[]> findAllByRevisionRecordId(@Param("revisionRecordId") Long revisionRecordId);
}
