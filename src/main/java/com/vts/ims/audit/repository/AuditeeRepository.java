package com.vts.ims.audit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.audit.model.Auditee;

public interface AuditeeRepository extends JpaRepository<Auditee, Long>{

	List<Auditee> findAllByIsActive(int isActive) throws Exception;

}
