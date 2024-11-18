package com.vts.ims.qms.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vts.ims.qms.dto.QmsQmChaptersDto;
import com.vts.ims.qms.dto.QmsQmDocumentSummaryDto;
import com.vts.ims.qms.dto.QmsQmRevisionRecordDto;
import com.vts.ims.qms.dto.QmsQmSectionsDto;
import com.vts.ims.qms.model.QmsAbbreviations;
import com.vts.ims.qms.model.QmsQmDocumentSummary;
import com.vts.ims.qms.model.QmsQmMappingOfClasses;
import com.vts.ims.qms.model.QmsQmRevisionRecord;
import com.vts.ims.qms.model.QmsQmSections;
import com.vts.ims.qms.model.QmsQmRevisionTransaction;
import com.vts.ims.qms.model.QmsQmChapters;

public interface QmsService {

	
	
	public List<QmsQmRevisionRecordDto> getQmVersionRecordDtoList() throws Exception;
	public List<QmsQmChaptersDto> getAllQMChapters() throws Exception;
	public List<QmsQmSectionsDto> getUnAddedQmSectionList() throws Exception;
	public Long addNewQmSection(String sectionName, String username) throws Exception;
	public Long qmUnAddListToAddList(long[] selectedSections, String username) throws Exception;
	public List<QmsQmChaptersDto> getQmSubChaptersById(Long chapterId) throws Exception;
	public Long addQmNewSubChapter(Long chapterId, String chapterName, String username) throws Exception;
	public Long updateQmChapterContent(Long chapterId, String chapterContent, String username) throws Exception;
	public Long updateQmChapterName(Long chapterId, String chapterName, String username) throws Exception;
	public Long addNewQmRevision(QmsQmRevisionRecordDto qmsQmRevisionRecordDto, String username) throws Exception;
//	public Long addQmMappingOfClasses(List<String[]> mocList, String username) throws Exception;
	public Long addQmDocSummary(QmsQmDocumentSummaryDto qmsQmDocumentSummaryDto, String username) throws Exception;
	public QmsQmDocumentSummaryDto getQmDocSummarybyId(long documentSummaryId) throws Exception;
	public long deleteQmChapterById(long chapterId , String username) throws Exception;
	public QmsQmChaptersDto getQmChapterById(long chapterId) throws Exception;
	
	
}
