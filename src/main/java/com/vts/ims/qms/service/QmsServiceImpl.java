package com.vts.ims.qms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vts.ims.qms.dto.QmsQmChaptersDto;
import com.vts.ims.qms.dto.QmsQmDocumentSummaryDto;
import com.vts.ims.qms.dto.QmsQmRevisionRecordDto;
import com.vts.ims.qms.dto.QmsQmSectionsDto;
import com.vts.ims.qms.model.QmsQmDocumentSummary;
import com.vts.ims.qms.model.QmsQmRevisionRecord;
import com.vts.ims.qms.model.QmsQmSections;
import com.vts.ims.qms.repository.QmsQmChaptersRepo;
import com.vts.ims.qms.repository.QmsQmDocumentSummaryRepo;
import com.vts.ims.qms.repository.QmsQmRevisionRecordRepo;
import com.vts.ims.qms.repository.QmsQmRevisionTransactionRepo;
import com.vts.ims.qms.repository.QmsQmSectionsRepo;
import com.vts.ims.qms.model.QmsQmRevisionTransaction;
import com.vts.ims.qms.model.QmsQmChapters;

@Service
public class QmsServiceImpl implements QmsService {
	
	private static final Logger logger=LogManager.getLogger(QmsServiceImpl.class);

	
	
	@Autowired
	QmsQmRevisionRecordRepo qmsQmRevisionRecordRepo;
	
	@Autowired
	QmsQmChaptersRepo qmsQmChaptersRepo;
	
	@Autowired
	QmsQmSectionsRepo qmsQmSectionsRepo;
	
	@Autowired
	QmsQmRevisionTransactionRepo qmsQmRevisionTransactionRepo;
	
	@Autowired
	QmsQmDocumentSummaryRepo qmsQmDocumentSummaryRepo;
	
	@Value("${appStorage}")
	private String storageDrive;
	 
	
	@Override
	public List<QmsQmRevisionRecordDto> getQmVersionRecordDtoList() throws Exception {
		logger.info(new Date() + " Inside getQmVersionRecordDtoList() " );
		try {
			List<QmsQmRevisionRecordDto> qmsQmRevisionRecordDtoList = new ArrayList<QmsQmRevisionRecordDto>();
			List<QmsQmRevisionRecord> qmRevisionRecord = qmsQmRevisionRecordRepo.findAllActiveQmRecords();
			qmRevisionRecord.forEach(revison -> {
				QmsQmRevisionRecordDto qmsQmRevisionRecordDto = QmsQmRevisionRecordDto.builder()
						.RevisionRecordId(revison.getRevisionRecordId())
						.DocFileName(revison.getDocFileName())
						.DocFilepath(revison.getDocFilepath())
						.Description(revison.getDescription())
						.IssueNo(revison.getIssueNo())
						.RevisionNo(revison.getRevisionNo())
						.DateOfRevision(revison.getDateOfRevision())
						.StatusCode(revison.getStatusCode())
						.CreatedBy(revison.getCreatedBy())
						.CreatedDate(revison.getCreatedDate())
						.ModifiedBy(revison.getModifiedBy())
						.ModifiedDate(revison.getModifiedDate())
						.IsActive(revison.getIsActive())
						.build();
				
				qmsQmRevisionRecordDtoList.add(qmsQmRevisionRecordDto);
			});
			return qmsQmRevisionRecordDtoList;
		} catch (Exception e) {
			logger.info(new Date() + " Inside getQmVersionRecordDtoList() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQmRevisionRecordDto>();
		}
	}
	
	@Override
	public List<QmsQmChaptersDto> getAllQMChapters() throws Exception {
		logger.info(new Date() + " Inside qmUnAddListToAddList() " );
		try {
			List<QmsQmChaptersDto> qmsQmChaptersDtoList = new ArrayList<QmsQmChaptersDto>();
			List<QmsQmChapters> qmChapters = qmsQmChaptersRepo.findAllActiveQmChapters();
			qmChapters.forEach(chapter -> {
				QmsQmChaptersDto qmsQmChaptersDto = QmsQmChaptersDto.builder()
						.ChapterId(chapter.getChapterId())
						.ChapterParentId(chapter.getChapterParentId())
						.SectionId(chapter.getSectionId())
						.ChapterName(chapter.getChapterName())
						.ChapterContent(chapter.getChapterContent())
						.CreatedBy(chapter.getCreatedBy())
						.CreatedDate(chapter.getCreatedDate())
						.ModifiedBy(chapter.getModifiedBy())
						.ModifiedDate(chapter.getModifiedDate())
						.IsActive(chapter.getIsActive())
						.build();
				
				qmsQmChaptersDtoList.add(qmsQmChaptersDto);
			});
			return qmsQmChaptersDtoList;
		} catch (Exception e) {
			logger.info(new Date() + " Inside getAllQMChapters() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQmChaptersDto>();
		}
	}
	
	
	@Override
	public List<QmsQmSectionsDto> getUnAddedQmSectionList() throws Exception {
		logger.info(new Date() + " Inside qmUnAddListToAddList() " );
		try {
			List<QmsQmSectionsDto> qmsQmSectionsDtoList = new ArrayList<QmsQmSectionsDto>();
			List<QmsQmSections> qmSections = qmsQmSectionsRepo.findSectionsNotInChapters();
			
			qmSections.forEach(section -> {
				QmsQmSectionsDto qmsQmChaptersDto = QmsQmSectionsDto.builder()
						.SectionId(section.getSectionId())
						.SectionName(section.getSectionName())
						.CreatedBy(section.getCreatedBy())
						.CreatedDate(section.getCreatedDate())
						.ModifiedBy(section.getModifiedBy())
						.ModifiedDate(section.getModifiedDate())
						.IsActive(section.getIsActive())
						.build();
				
				qmsQmSectionsDtoList.add(qmsQmChaptersDto);
			});
			
			return qmsQmSectionsDtoList;
		} catch (Exception e) {
			logger.info(new Date() + " Inside getUnAddedQmSectionList() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQmSectionsDto>();
		}
	}
	
	@Override
	public Long addNewQmSection(String sectionName, String username) throws Exception {
		logger.info(new Date() + " Inside qmUnAddListToAddList() " );
		try {
			QmsQmSections qmsQmSections = new QmsQmSections();
			qmsQmSections.setSectionName(sectionName);
			qmsQmSections.setCreatedBy(username);
			qmsQmSections.setCreatedDate(LocalDateTime.now());
			qmsQmSections.setIsActive(1);
			return qmsQmSectionsRepo.save(qmsQmSections).getSectionId();
		} catch (Exception e) {
			logger.info(new Date() + " Inside addNewQmSection() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long qmUnAddListToAddList(@RequestBody long[] selectedSections, @RequestHeader  String username) throws Exception {
		logger.info(new Date() + " Inside qmUnAddListToAddList() " );
		try {
			long res=0;
			for(long id : selectedSections) {
			
				Optional<QmsQmSections> optionalSection = qmsQmSectionsRepo.findById(id);
				if (optionalSection.isPresent()) {
					QmsQmSections qmsQmSections = optionalSection.get();
					QmsQmChapters qmsQmChapters = new QmsQmChapters();
					qmsQmChapters.setSectionId(qmsQmSections.getSectionId());
					qmsQmChapters.setChapterParentId(0);
					qmsQmChapters.setChapterName(qmsQmSections.getSectionName());
					qmsQmChapters.setCreatedBy(username);
					qmsQmChapters.setCreatedDate(LocalDateTime.now());
					qmsQmChapters.setIsActive(1);
					res = res+ qmsQmChaptersRepo.save(qmsQmChapters).getChapterId();
				}
			
			}
			
			return res;
			
		} catch (Exception e) {
			logger.info(new Date() + " Inside qmUnAddListToAddList() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public List<QmsQmChaptersDto> getQmSubChaptersById(Long chapterId) throws Exception {
		logger.info(new Date() + " Inside getQmSubChaptersById() ");
		try {
			List<QmsQmChaptersDto> qmsQmChaptersDtoList = new ArrayList<QmsQmChaptersDto>();
			List<QmsQmChapters> qmChapters = qmsQmChaptersRepo.findByChapterParentIdAndIsActive(chapterId, 1);
			qmChapters.forEach(chapter -> {
				QmsQmChaptersDto qmsQmChaptersDto = QmsQmChaptersDto.builder()
						.ChapterId(chapter.getChapterId())
						.ChapterParentId(chapter.getChapterParentId())
						.SectionId(chapter.getSectionId())
						.ChapterName(chapter.getChapterName())
						.ChapterContent(chapter.getChapterContent())
						.CreatedBy(chapter.getCreatedBy())
						.CreatedDate(chapter.getCreatedDate())
						.ModifiedBy(chapter.getModifiedBy())
						.ModifiedDate(chapter.getModifiedDate())
						.IsActive(chapter.getIsActive())
						.build();
				
				qmsQmChaptersDtoList.add(qmsQmChaptersDto);
			});
			return qmsQmChaptersDtoList;
		} catch (Exception e) {
			logger.info(new Date() + " Inside getQmSubChaptersById() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQmChaptersDto>();
		}
	}
	
	@Override
	public Long addQmNewSubChapter(Long chapterId, String chapterName, String username) throws Exception {
		logger.info(new Date() + " Inside addQmNewSubChapter() ");
		try {
			Long res =0l;
			Optional<QmsQmChapters> optionalChapters = qmsQmChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQmChapters oldQmsQmChapters = optionalChapters.get();
				QmsQmChapters qmsQmChapters = new QmsQmChapters();
				qmsQmChapters.setChapterName(chapterName);
				qmsQmChapters.setChapterParentId(chapterId);
				qmsQmChapters.setSectionId(oldQmsQmChapters.getSectionId());
				qmsQmChapters.setCreatedBy(username);
				qmsQmChapters.setCreatedDate(LocalDateTime.now());
				qmsQmChapters.setIsActive(1);
				res = qmsQmChaptersRepo.save(qmsQmChapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.info(new Date() + " Inside addQmNewSubChapter() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long updateQmChapterContent(Long chapterId, String chapterContent, String username) throws Exception {
		logger.info(new Date() + " Inside updateQmChapterContent() ");
		try {
			Long res = 0l;
			Optional<QmsQmChapters> optionalChapters = qmsQmChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQmChapters qmsQmChapters = optionalChapters.get();
				qmsQmChapters.setChapterContent(chapterContent);
				qmsQmChapters.setModifiedBy(username);
				qmsQmChapters.setModifiedDate(LocalDateTime.now());

				res = qmsQmChaptersRepo.save(qmsQmChapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.info(new Date() + " Inside updateQmChapterContent() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long updateQmChapterName(Long chapterId, String chapterName, String username) throws Exception {
		logger.info(new Date() + " Inside updateQmChapterName() ");
		try {
			Long res = 0l;
			Optional<QmsQmChapters> optionalChapters = qmsQmChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQmChapters qmsQmChapters = optionalChapters.get();
				qmsQmChapters.setChapterName(chapterName);
				qmsQmChapters.setModifiedBy(username);
				qmsQmChapters.setModifiedDate(LocalDateTime.now());
				
				res = qmsQmChaptersRepo.save(qmsQmChapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.error(new Date() + " Inside updateQmChapterName() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long addNewQmRevision(QmsQmRevisionRecordDto qmsQmRevisionRecordDto, String username) throws Exception {
		logger.info(new Date() + " Inside addNewQmRevision() ");
		try {
			long res = 0;
			
			QmsQmRevisionRecord qmsQmRevisionRecord = new QmsQmRevisionRecord();
			
			qmsQmRevisionRecord.setDescription(qmsQmRevisionRecordDto.getDescription());
			qmsQmRevisionRecord.setIssueNo(qmsQmRevisionRecordDto.getIssueNo());
			qmsQmRevisionRecord.setRevisionNo(qmsQmRevisionRecordDto.getRevisionNo());
			qmsQmRevisionRecord.setStatusCode("INI");
			qmsQmRevisionRecord.setDateOfRevision(LocalDate.now());
			qmsQmRevisionRecord.setCreatedDate(LocalDateTime.now());
			qmsQmRevisionRecord.setCreatedBy(username);
			qmsQmRevisionRecord.setIsActive(1);
			
			res = qmsQmRevisionRecordRepo.save(qmsQmRevisionRecord).getRevisionRecordId();
			
			QmsQmRevisionTransaction trans = new QmsQmRevisionTransaction();
//			trans.setEmpId(login.getEmpId());
			trans.setRevisionRecordId(qmsQmRevisionRecord.getRevisionRecordId());
			trans.setStatusCode(qmsQmRevisionRecord.getStatusCode());
			trans.setTransactionDate(LocalDateTime.now());
			trans.setRemarks(null);
			qmsQmRevisionTransactionRepo.save(trans);
			
			return res;
		} catch (Exception e) {
			logger.error(new Date() + " Inside addNewQmRevision() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long addQmDocSummary(QmsQmDocumentSummaryDto qmsQmDocumentSummaryDto, String username) throws Exception {
		logger.info(new Date() + " Inside addQmDocSummary() ");
		try {
			
			long res =0;
			
			QmsQmDocumentSummary qmsQmDocumentSummary = new QmsQmDocumentSummary();
			qmsQmDocumentSummary.setDocumentSummaryId(qmsQmDocumentSummaryDto.getDocumentSummaryId());
			qmsQmDocumentSummary.setAdditionalInfo(qmsQmDocumentSummaryDto.getAdditionalInfo());
			qmsQmDocumentSummary.setAbstract(qmsQmDocumentSummaryDto.getAbstract());
			qmsQmDocumentSummary.setKeywords(qmsQmDocumentSummaryDto.getKeywords());
			qmsQmDocumentSummary.setDistribution(qmsQmDocumentSummaryDto.getDistribution());
			qmsQmDocumentSummary.setRevisionRecordId(qmsQmDocumentSummaryDto.getRevisionRecordId());
			
			
			if(qmsQmDocumentSummary.getDocumentSummaryId() >0 ) {
				
				qmsQmDocumentSummary.setModifiedBy(username);
				qmsQmDocumentSummary.setModifiedDate(LocalDateTime.now());
				res=qmsQmDocumentSummaryRepo.save(qmsQmDocumentSummary).getDocumentSummaryId();
				
			} else {
				qmsQmDocumentSummary.setCreatedBy(username);
				qmsQmDocumentSummary.setCreatedDate(LocalDateTime.now());
				res=qmsQmDocumentSummaryRepo.save(qmsQmDocumentSummary).getDocumentSummaryId();
			}
			
			return res;
			
		} catch (Exception e) {
			logger.error(new Date() + " Inside addQmDocSummary() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public QmsQmDocumentSummaryDto getQmDocSummarybyId(long documentSummaryId) throws Exception {
		logger.info(new Date() + " Inside getQmDocSummarybyId() ");
		try {
			Optional<QmsQmDocumentSummary> optionalQmsQmDocumentSummary= qmsQmDocumentSummaryRepo.findById(documentSummaryId);
			
			QmsQmDocumentSummaryDto.QmsQmDocumentSummaryDtoBuilder qmsQmDocumentSummaryDtobuilder = QmsQmDocumentSummaryDto.builder();

			if (optionalQmsQmDocumentSummary.isPresent()) {
				QmsQmDocumentSummary existingSummary = optionalQmsQmDocumentSummary.get();
				qmsQmDocumentSummaryDtobuilder
				.DocumentSummaryId(existingSummary.getDocumentSummaryId())
				.AdditionalInfo(existingSummary.getAdditionalInfo())
				.Abstract(existingSummary.getAbstract())
				.Keywords(existingSummary.getKeywords())
				.Distribution(existingSummary.getDistribution())
				.RevisionRecordId(existingSummary.getRevisionRecordId())
				.CreatedBy(existingSummary.getCreatedBy())
				.CreatedDate(existingSummary.getCreatedDate())
				.ModifiedBy(existingSummary.getModifiedBy())
				.ModifiedDate(existingSummary.getModifiedDate());
			}

			QmsQmDocumentSummaryDto qmsQmDocumentSummary = qmsQmDocumentSummaryDtobuilder.build();
			
			return qmsQmDocumentSummary;
		} catch (Exception e) {
			logger.error(new Date() + " Inside getQmDocSummarybyId() "+ e );
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long deleteQmChapterById(long chapterId , String username) throws Exception {
		try {
			Long res = 0l;
			Optional<QmsQmChapters> optionalChapters = qmsQmChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQmChapters qmsQmChapters = optionalChapters.get();
				qmsQmChapters.setIsActive(0);
				qmsQmChapters.setModifiedBy(username);
				qmsQmChapters.setModifiedDate(LocalDateTime.now());
				
				res = qmsQmChaptersRepo.save(qmsQmChapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO deleteQmChapterById() " + e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public QmsQmChaptersDto getQmChapterById(long chapterId) throws Exception {
		logger.info(new Date() + " Inside getQmChapterById() " );
		try {
			QmsQmChaptersDto qmsQmChaptersDto = QmsQmChaptersDto.builder().build();
			Optional<QmsQmChapters> optionalChapters = qmsQmChaptersRepo.findById(chapterId);
			if(optionalChapters.isPresent()) {
						QmsQmChapters chapter = optionalChapters.get();
						qmsQmChaptersDto = QmsQmChaptersDto.builder()
						.ChapterId(chapter.getChapterId())
						.ChapterParentId(chapter.getChapterParentId())
						.SectionId(chapter.getSectionId())
						.ChapterName(chapter.getChapterName())
						.ChapterContent(chapter.getChapterContent())
						.CreatedBy(chapter.getCreatedBy())
						.CreatedDate(chapter.getCreatedDate())
						.ModifiedBy(chapter.getModifiedBy())
						.ModifiedDate(chapter.getModifiedDate())
						.IsActive(chapter.getIsActive())
						.build();
				
			}
			return qmsQmChaptersDto;
		} catch (Exception e) {
			logger.error(new Date() + " Inside getQmChapterById() "+ e );
			e.printStackTrace();
			return QmsQmChaptersDto.builder().build();
		}
	}
	
	
}
