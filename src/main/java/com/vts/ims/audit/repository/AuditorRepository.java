package com.vts.ims.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.audit.model.Auditor;

public interface AuditorRepository extends JpaRepository<Auditor, Long> {

}
