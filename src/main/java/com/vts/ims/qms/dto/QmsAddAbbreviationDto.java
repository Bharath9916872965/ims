package com.vts.ims.qms.dto;

import lombok.Data;

import java.util.List;
@Data
public class QmsAddAbbreviationDto {
    private Long revisionRecordId;
    private String docName;
    private List<QmsAbbreviationDto> abbreviationDetails;
}
