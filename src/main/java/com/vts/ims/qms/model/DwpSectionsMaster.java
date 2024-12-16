package com.vts.ims.qms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ims_qms_dwp_sections_master")
public class DwpSectionsMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MasterSectionId")
    private Long masterSectionId;
    @Column(name = "SectionName")
    private String sectionName;
    @Column(name = "DocType")
    private String docType;
    @Column(name = "CreatedBy")
    private String createdBy;
    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;
    @Column(name = "ModifiedBy")
    private String modifiedBy;
    @Column(name = "ModifiedDate")
    private LocalDateTime modifiedDate;
    @Column(name = "IsActive")
    private int isActive;

}

