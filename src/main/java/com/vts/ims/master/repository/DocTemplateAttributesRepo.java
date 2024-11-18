package com.vts.ims.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vts.ims.master.entity.DocTemplateAttributes;

@Repository
public interface DocTemplateAttributesRepo extends JpaRepository<DocTemplateAttributes, Long> {

}
