package com.vts.ims.qms.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vts.ims.qms.model.MRMasters;

public interface MrMastersRepo extends JpaRepository<MRMasters, Long>{

	@Query(value="SELECT a.EmpId,a.MRType,a.MRFrom,a.MRTo FROM ims_mrs a WHERE (:locadate BETWEEN a.MRFrom AND a.MRTo) AND a.MRType=:role AND a.IsActive='1'",nativeQuery = true)
	List<Object[]> getMRrepList(@Param("role") String role,@Param("locadate") LocalDate locadate) throws Exception;


}
