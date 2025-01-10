package com.vts.ims.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ims.admin.entity.AuditPatch;

public interface AuditPatchRepository extends JpaRepository<AuditPatch, Long>{

}
