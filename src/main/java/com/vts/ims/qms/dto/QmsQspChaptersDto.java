package com.vts.ims.qms.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class QmsQspChaptersDto {

    private long chapterId;
    private long chapterParentId;
    private long sectionId;
    private String chapterName;
    private String chapterContent;
    private char isPagebreakAfter;
    private char isLandscape;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
    private int isActive;
}
