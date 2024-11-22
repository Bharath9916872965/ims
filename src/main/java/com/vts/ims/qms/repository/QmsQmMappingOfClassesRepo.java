package com.vts.ims.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.qms.model.QmsQmMappingOfClasses;

import jakarta.transaction.Transactional;

public interface QmsQmMappingOfClassesRepo extends JpaRepository<QmsQmMappingOfClasses, Long> {
	
	@Transactional
	void deleteByRevisionRecordId(Long revisionRecordId);
	
	@Query(value = "SELECT t1.SectionNo, t1.ClauseNo, t1.Description " +
            "FROM ims_qms_qm_mapping_classes t1 " +
            "WHERE t1.revisionRecordId = :revisionRecordId", nativeQuery = true)
	List<Object[]> findAllByRevisionRecordId(@Param("revisionRecordId") Long revisionRecordId);

	
}
