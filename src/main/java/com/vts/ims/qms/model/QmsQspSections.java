package com.vts.ims.qms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="ims_qms_qsp_sections")
public class QmsQspSections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SectionId")
    private long sectionId;
    @Column(name = "SectionName")
    private String sectionName;
    @Column(name = "DocName")
    private String docName;
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
