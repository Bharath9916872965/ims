package com.vts.ims.qms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vts.ims.qms.dto.CheckListMasterDto;
import com.vts.ims.qms.dto.DwpRevisionRecordDto;
import com.vts.ims.qms.dto.QmsQmChaptersDto;
import com.vts.ims.qms.dto.QmsQmDocumentSummaryDto;
import com.vts.ims.qms.dto.QmsQmMappingDto;
import com.vts.ims.qms.dto.QmsQmRevisionRecordDto;
import com.vts.ims.qms.dto.QmsQmSectionsDto;
import com.vts.ims.qms.model.DwpChapters;
import com.vts.ims.qms.model.DwpRevisionRecord;
import com.vts.ims.qms.model.DwpSections;
import com.vts.ims.qms.model.QmsAbbreviations;
import com.vts.ims.qms.model.QmsQmChapters;
import com.vts.ims.qms.model.QmsQmDocumentSummary;
import com.vts.ims.qms.model.QmsQmMappingOfClasses;
import com.vts.ims.qms.model.QmsQmRevisionRecord;
import com.vts.ims.qms.model.QmsQmRevisionTransaction;
import com.vts.ims.qms.model.QmsQmSections;
import com.vts.ims.qms.repository.DwpChaptersRepo;
import com.vts.ims.qms.repository.DwpRevisionRecordRepo;
import com.vts.ims.qms.repository.DwpSectionsRepo;
import com.vts.ims.qms.repository.QmsAbbreviationsRepo;
import com.vts.ims.qms.repository.QmsQmChaptersRepo;
import com.vts.ims.qms.repository.QmsQmDocumentSummaryRepo;
import com.vts.ims.qms.repository.QmsQmMappingOfClassesRepo;
import com.vts.ims.qms.repository.QmsQmRevisionRecordRepo;
import com.vts.ims.qms.repository.QmsQmRevisionTransactionRepo;
import com.vts.ims.qms.repository.QmsQmSectionsRepo;

@Service
public class QmsServiceImpl implements QmsService {
	
	private static final Logger logger=LogManager.getLogger(QmsServiceImpl.class);

	@Value("${appStorage}")
	private String storageDrive;
	
	@Autowired
	private QmsQmRevisionRecordRepo qmsQmRevisionRecordRepo;
	
	@Autowired
	private QmsQmChaptersRepo qmsQmChaptersRepo;
	
	@Autowired
	private QmsQmSectionsRepo qmsQmSectionsRepo;
	
	@Autowired
	private QmsQmRevisionTransactionRepo qmsQmRevisionTransactionRepo;
	
	@Autowired
	private QmsQmDocumentSummaryRepo qmsQmDocumentSummaryRepo;
	
	@Autowired
	private QmsAbbreviationsRepo qmsAbbreviationsRepo;
	
	@Autowired
	private QmsQmMappingOfClassesRepo qmsQmMappingOfClassesRepo;
	
	@Autowired
	private DwpRevisionRecordRepo dwpRevisionRecordRepo;
	
	@Autowired
	private DwpSectionsRepo dwpSectionsRepo;
	
	@Autowired
	private DwpChaptersRepo dwpChaptersRepo;
	
	 
	
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
						.AbbreviationIdNotReq(revison.getAbbreviationIdNotReq())
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
						.IsPagebreakAfter(chapter.getIsPagebreakAfter())
						.IsLandscape(chapter.getIsLandscape())
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
						.IsPagebreakAfter(chapter.getIsPagebreakAfter())
						.IsLandscape(chapter.getIsLandscape())
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
	public QmsQmDocumentSummaryDto getQmDocSummarybyRevisionRecordId(long revisionRecordId) throws Exception {
		logger.info(new Date() + " Inside getQmDocSummarybyRevisionRecordId() ");
		try {
			QmsQmDocumentSummary existingSummary = qmsQmDocumentSummaryRepo.findByRevisionRecordId(revisionRecordId);
			
			QmsQmDocumentSummaryDto.QmsQmDocumentSummaryDtoBuilder qmsQmDocumentSummaryDtobuilder = QmsQmDocumentSummaryDto.builder();
			
			if (existingSummary != null) {
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
			logger.error(new Date() + " Inside getQmDocSummarybyRevisionRecordId() "+ e );
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
						.IsPagebreakAfter(chapter.getIsPagebreakAfter())
						.IsLandscape(chapter.getIsLandscape())
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
	
	
	@Override
	public long updatechapterPagebreakAndLandscape(String[] chapterPagebreakOrLandscape, String username) throws Exception {
		logger.info(new Date() + " Inside updatechapterPagebreakAndLandscape() " );
		try {
			long res=0;
			long chapterId = Long.parseLong(chapterPagebreakOrLandscape[0]);
			String IsPagebreakAfter = chapterPagebreakOrLandscape[1];
			String IsLandscape = chapterPagebreakOrLandscape[2];

			
			Optional<QmsQmChapters> optionalChapters = qmsQmChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQmChapters qmsQmChapters = optionalChapters.get();
				qmsQmChapters.setIsPagebreakAfter(IsPagebreakAfter.charAt(0));
				qmsQmChapters.setIsLandscape(IsLandscape.charAt(0));
				qmsQmChapters.setModifiedBy(username);
				qmsQmChapters.setModifiedDate(LocalDateTime.now());
				
				res = qmsQmChaptersRepo.save(qmsQmChapters).getChapterId();
			}
			
			return res;
		} catch (Exception e) {
			logger.error(new Date() +" Inside updatechapterPagebreakAndLandscape " +e);
			return 0;
		}
	}
	
	@Override
	public List<QmsAbbreviations> getAbbreviationList(String abbreviationIdNotReq) throws Exception {
		logger.info(new Date() + " Inside getAbbreviationList() " );
		try {
			
			String abbreviationId = "";
			if(abbreviationIdNotReq != null) {
				abbreviationIdNotReq = abbreviationIdNotReq.trim();
				abbreviationId = abbreviationIdNotReq.replace("\"", "");
			}
			
			List<QmsAbbreviations> abbreviationList =  qmsAbbreviationsRepo.findValidAbbreviations(abbreviationId);
			
			return abbreviationList;
		} catch (Exception e) {
			logger.error(new Date() +" Inside getAbbreviationList() " +e);
			return new ArrayList<QmsAbbreviations>();
		}
	}
	
	@Override
	public QmsQmRevisionRecord getQmsQmRevisionRecord(Long revisionRecordId) throws Exception {
		logger.info(new Date() + " Inside getQmsQmRevisionRecord() " );
		try {
			QmsQmRevisionRecord qmRevisionRecord = qmsQmRevisionRecordRepo.findById(revisionRecordId).orElse(null);
			return qmRevisionRecord;
		} catch (Exception e) {
			logger.error(new Date() +" Inside getQmsQmRevisionRecord() " +e);
			return null;
		}
	}
	
	
	@Override
	public long updateNotReqQmAbbreviationIds(Long revisionRecordId, String abbreviationIds, String username) throws Exception {
		logger.info(new Date() + " Inside updateNotReqQmAbbreviationIds() " );
		try {
			long res =0;
			Optional<QmsQmRevisionRecord> optionalQmRevisionRecord = qmsQmRevisionRecordRepo.findById(revisionRecordId);
			if(optionalQmRevisionRecord.isPresent()) {
				QmsQmRevisionRecord qmRevisionRecord = optionalQmRevisionRecord.get();
				qmRevisionRecord.setAbbreviationIdNotReq(abbreviationIds);
				res = qmsQmRevisionRecordRepo.save(qmRevisionRecord).getRevisionRecordId();
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside service updateNotReqQmAbbreviationIds() " + e);
			return 0l;
		}
	}
	
	
	@Override
	public Long addMappingOfClasses(Long revisionRecordId, List<String[]> mocList, String username) throws Exception {
		logger.info(new Date() + " Inside addMappingOfClasses() " );
		try {
			long res = 0;
			if(mocList.size()>1) {
				qmsQmMappingOfClassesRepo.deleteByRevisionRecordId(revisionRecordId);
				for(int i=1; i<mocList.size(); i++) {

					QmsQmMappingOfClasses isoMappingOfClasses = new QmsQmMappingOfClasses();
					isoMappingOfClasses.setSectionNo(mocList.get(i)[0]);
					isoMappingOfClasses.setClauseNo(mocList.get(i)[1]);
					isoMappingOfClasses.setIsForCheckList("N");
					isoMappingOfClasses.setDescription(mocList.get(i).length==3 ? mocList.get(i)[2] : "");
					isoMappingOfClasses.setRevisionRecordId(revisionRecordId);
					res=qmsQmMappingOfClassesRepo.save(isoMappingOfClasses).getMocId();
				}
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside service addMappingOfClasses() " + e);
			return 0l;
		}
	}
	
	@Override
	public List<Object[]> getMocList(Long revisionRecordId) throws Exception {
		logger.info(new Date() + " Inside getMocList() " );
		try {
			return qmsQmMappingOfClassesRepo.findAllByRevisionRecordId(revisionRecordId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside service getMocList() " + e);
			return new ArrayList<Object[]>();
		}
	}
	
	@Override
	public List<QmsQmMappingDto> getMoctotalList() throws Exception {
		logger.info(new Date() + " Inside getMoctotalList() " );
		try {
			 List<QmsQmMappingOfClasses> result = qmsQmMappingOfClassesRepo.findAll();
			 return Optional.ofNullable(result).orElse(Collections.emptyList()).stream().map(entity -> QmsQmMappingDto.builder()
					 									   .mocId(entity.getMocId())
					 									   .clauseNo(entity.getClauseNo())
					 									   .sectionNo(entity.getSectionNo())
					 									   .mocParentId(entity.getMocParentId())
					 									   .description(entity.getDescription())
					 									   .revisionRecordId(entity.getRevisionRecordId())
					 									   .isForCheckList(entity.getIsForCheckList())
					 									   .isActive(entity.getIsActive())
					 									   .build()).collect(Collectors.toList());
			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside service getMoctotalList() " + e);
			return List.of();
		}
	}
	
	@Override
	public List<DwpRevisionRecordDto> getDwpVersionRecordDtoList(Long divisionId) throws Exception {
		logger.info(new Date() + " Inside getQmVersionRecordDtoList() " );
		try {
			
			
			List<DwpRevisionRecordDto> revisionRecordDtoList = new ArrayList<DwpRevisionRecordDto>();
			List<DwpRevisionRecord> revisionRecord = dwpRevisionRecordRepo.findAllActiveDwpRecords();
			revisionRecord.forEach(revison -> {
				DwpRevisionRecordDto qmsQmRevisionRecordDto = DwpRevisionRecordDto.builder()
						.RevisionRecordId(revison.getRevisionRecordId())
						.DivisionId(revison.getDivisionId())
						.DocFileName(revison.getDocFileName())
						.DocFilepath(revison.getDocFilepath())
						.Description(revison.getDescription())
						.IssueNo(revison.getIssueNo())
						.RevisionNo(revison.getRevisionNo())
						.DateOfRevision(revison.getDateOfRevision())
						.StatusCode(revison.getStatusCode())
						.AbbreviationIdNotReq(revison.getAbbreviationIdNotReq())
						.CreatedBy(revison.getCreatedBy())
						.CreatedDate(revison.getCreatedDate())
						.ModifiedBy(revison.getModifiedBy())
						.ModifiedDate(revison.getModifiedDate())
						.IsActive(revison.getIsActive())
						.build();
				
				revisionRecordDtoList.add(qmsQmRevisionRecordDto);
			});
			
			return revisionRecordDtoList;
		} catch (Exception e) {
			logger.info(new Date() + " Inside getDwpVersionRecordDtoList() "+ e );
			e.printStackTrace();
			return new ArrayList<DwpRevisionRecordDto>();
		}
	}
	
	@Override
	public List<DwpChapters> getAllDwpChapters(Long divisionId) throws Exception {
		logger.info(new Date() + " Inside getAllDwpChapters() " );
		try {
			List<DwpChapters> chapters = dwpChaptersRepo.findAllActiveDwpChapters(divisionId);
			return chapters;
		} catch (Exception e) {
			logger.info(new Date() + " Inside getAllDwpChapters() "+ e );
			e.printStackTrace();
			return new ArrayList<DwpChapters>();
		}
	}
	
	
	@Override
	public Long updateDwpChapterName(Long chapterId, String chapterName, String username) throws Exception {
		logger.info(new Date() + " Inside updateDwpChapterName() ");
		try {
			Long res = 0l;
			Optional<DwpChapters> optionalChapters = dwpChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				DwpChapters chapters = optionalChapters.get();
				chapters.setChapterName(chapterName);
				chapters.setModifiedBy(username);
				chapters.setModifiedDate(LocalDateTime.now());
				
				res = dwpChaptersRepo.save(chapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.error(new Date() + " Inside updateDwpChapterName() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public List<DwpChapters> getDwpSubChaptersById(Long chapterId) throws Exception {
		logger.info(new Date() + " Inside getDwpSubChaptersById() ");
		try {
			List<DwpChapters> chapters = dwpChaptersRepo.findByChapterParentIdAndIsActive(chapterId, 1);
			return chapters;
		} catch (Exception e) {
			logger.error(new Date() + " Inside getDwpSubChaptersById() "+ e );
			e.printStackTrace();
			return new ArrayList<DwpChapters>();
		}
	}
	
	
	@Override
	public long deleteDwpChapterById(Long chapterId , String username) throws Exception {
		try {
			Long res = 0l;
			Optional<DwpChapters> optionalChapters = dwpChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				DwpChapters chapters = optionalChapters.get();
				chapters.setIsActive(0);
				chapters.setModifiedBy(username);
				chapters.setModifiedDate(LocalDateTime.now());
				
				res = dwpChaptersRepo.save(chapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO deleteDwpChapterById() " + e);
			e.printStackTrace();
			return 0;
		}
	}
	
	
	@Override
	public Long addDwpNewSubChapter(Long chapterId, String chapterName, String username) throws Exception {
		logger.info(new Date() + " Inside addDwpNewSubChapter() ");
		try {
			Long res =0l;
			Optional<DwpChapters> optionalChapters = dwpChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				DwpChapters oldQmsQmChapters = optionalChapters.get();
				DwpChapters chapters = new DwpChapters();
				chapters.setChapterName(chapterName);
				chapters.setChapterParentId(chapterId);
				chapters.setSectionId(oldQmsQmChapters.getSectionId());
				chapters.setIsPagebreakAfter('N');
				chapters.setIsLandscape('N');
				chapters.setCreatedBy(username);
				chapters.setCreatedDate(LocalDateTime.now());
				chapters.setIsActive(1);
				res = dwpChaptersRepo.save(chapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.error(new Date() + " Inside addDwpNewSubChapter() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public long updateDwpPagebreakAndLandscape(String[] chapterPagebreakOrLandscape, String username) throws Exception {
		logger.info(new Date() + " Inside updateDwpPagebreakAndLandscape() " );
		try {
			long res=0;
			long chapterId = Long.parseLong(chapterPagebreakOrLandscape[0]);
			String IsPagebreakAfter = chapterPagebreakOrLandscape[1];
			String IsLandscape = chapterPagebreakOrLandscape[2];

			
			Optional<DwpChapters> optionalChapters = dwpChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				DwpChapters chapters = optionalChapters.get();
				chapters.setIsPagebreakAfter(IsPagebreakAfter.charAt(0));
				chapters.setIsLandscape(IsLandscape.charAt(0));
				chapters.setModifiedBy(username);
				chapters.setModifiedDate(LocalDateTime.now());
				
				res = dwpChaptersRepo.save(chapters).getChapterId();
			}
			
			return res;
		} catch (Exception e) {
			logger.error(new Date() +" Inside updateDwpPagebreakAndLandscape() " +e);
			return 0;
		}
	}
	
	@Override
	public DwpChapters getDwpChapterById(long chapterId) throws Exception {
		logger.info(new Date() + " Inside getDwpChapterById() " );
		try {
			Optional<DwpChapters> optionalChapters = dwpChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				DwpChapters chapters = optionalChapters.get();
				return chapters;
			}
			return new DwpChapters();
		} catch (Exception e) {
			logger.error(new Date() + " Inside getDwpChapterById() "+ e );
			e.printStackTrace();
			return new DwpChapters();
		}
	}
	
	
	@Override
	public Long updateDwpChapterContent(Long chapterId, String chapterContent, String username) throws Exception {
		logger.info(new Date() + " Inside updateDwpChapterContent() ");
		try {
			Long res = 0l;
			Optional<DwpChapters> optionalChapters = dwpChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				DwpChapters chapters = optionalChapters.get();
				chapters.setChapterContent(chapterContent);
				chapters.setModifiedBy(username);
				chapters.setModifiedDate(LocalDateTime.now());

				res = dwpChaptersRepo.save(chapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.info(new Date() + " Inside updateDwpChapterContent() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public List<DwpSections> getDwpUnAddedQmSectionList(Long divisionId) throws Exception {
		logger.info(new Date() + " Inside qmUnAddListToAddList() " );
		try {
			List<DwpSections> sections = dwpSectionsRepo.findSectionsNotInChapters(divisionId);
			
			
			return sections;
		} catch (Exception e) {
			logger.info(new Date() + " Inside getUnAddedQmSectionList() "+ e );
			e.printStackTrace();
			return new ArrayList<DwpSections>();
		}
	}
	
	@Override
	public Long addNewDwpSection(long divisionId, String sectionName, String username) throws Exception {
		logger.info(new Date() + " Inside addNewDwpSection() " );
		try {
			DwpSections sections = new DwpSections();
			sections.setSectionName(sectionName);
			sections.setDivisionId(divisionId);
			sections.setCreatedBy(username);
			sections.setCreatedDate(LocalDateTime.now());
			sections.setIsActive(1);
			return dwpSectionsRepo.save(sections).getSectionId();
		} catch (Exception e) {
			logger.info(new Date() + " Inside addNewDwpSection() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long dwpUnAddListToAddList(@RequestBody long[] selectedSections, @RequestHeader  String username) throws Exception {
		logger.info(new Date() + " Inside dwpUnAddListToAddList() " );
		try {
			long res=0;
			for(long id : selectedSections) {
			
				Optional<DwpSections> optionalSection = dwpSectionsRepo.findById(id);
				if (optionalSection.isPresent()) {
					DwpSections sections = optionalSection.get();
					DwpChapters chapters = new DwpChapters();
					chapters.setSectionId(sections.getSectionId());
					chapters.setChapterParentId(0);
					chapters.setChapterName(sections.getSectionName());
					chapters.setCreatedBy(username);
					chapters.setCreatedDate(LocalDateTime.now());
					chapters.setIsActive(1);
					res = res+ dwpChaptersRepo.save(chapters).getChapterId();
				}
			}
			
			return res;
			
		} catch (Exception e) {
			logger.info(new Date() + " Inside dwpUnAddListToAddList() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Integer updateChapterDescById(CheckListMasterDto checkListMasterDto, String username) throws Exception {
	    logger.info(" AuditServiceImpl Inside method updateChapterDescById()");
	    Integer result = 0;
	    try {
	    	 result = qmsQmMappingOfClassesRepo.updateMoc(checkListMasterDto.getMocId(),checkListMasterDto.getDescription(),username,LocalDateTime.now());
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method updateChapterDescById() " + e);
	    }
	    return result;
	}
	
	@Override
	public Integer deleteChapterDescById(String mocId, String username) throws Exception {
	    logger.info(" AuditServiceImpl Inside method deleteChapterDescById()");
	    Integer result = 0;
	    try {
	    	 result = qmsQmMappingOfClassesRepo.deleteMoc(mocId,username,LocalDateTime.now());
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method deleteChapterDescById() " + e);
	    }
	    return result;
	}
	
	@Override
	public Integer deleteSubChapterDescById(String mocId, String username) throws Exception {
	    logger.info(" AuditServiceImpl Inside method deleteSubChapterDescById()");
	    Integer result = 0;
	    try {
	    	 result = qmsQmMappingOfClassesRepo.deleteSubChapter(mocId,username,LocalDateTime.now());
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method deleteSubChapterDescById() " + e);
	    }
	    return result;
	}

	@Override
	public Long addNewChapter(CheckListMasterDto checkListMasterDto, String username) throws Exception {
	    logger.info(" AuditServiceImpl Inside method addNewChapter()");
	    long result = 0;
	    try {
	    	QmsQmMappingOfClasses mapClasses = new QmsQmMappingOfClasses();
	    	mapClasses.setSectionNo(checkListMasterDto.getSectionNo());	
	    	if(checkListMasterDto.getLevel() == 0) {
		    	mapClasses.setClauseNo(checkListMasterDto.getSectionNo());	
		    	mapClasses.setMocParentId(0L);
	    	}else if(checkListMasterDto.getLevel() == 1 || checkListMasterDto.getLevel() == 2 || checkListMasterDto.getLevel() == 3 || checkListMasterDto.getLevel() == 4) {
		    	mapClasses.setClauseNo(checkListMasterDto.getClauseNo());	
		    	mapClasses.setMocParentId(checkListMasterDto.getMocId());
	    	}
	    	
	    	mapClasses.setDescription(checkListMasterDto.getDescription());
	    	mapClasses.setRevisionRecordId(1L);
	    	mapClasses.setIsActive(1);
	    	mapClasses.setIsForCheckList("N");
	    	mapClasses.setCreatedBy(username);
	    	mapClasses.setCreatedDate(LocalDateTime.now());
	    	
	    	result = qmsQmMappingOfClassesRepo.save(mapClasses).getMocId();	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method addNewChapter() " + e);
	    }
	    return result;
	}

	@Override
	public int addChapterToMasters(List<String> mocIds, String username) throws Exception {
	    logger.info(" AuditServiceImpl Inside method addChapterToMasters()");
	    int result = 0;
	    try {
	    	for(String data : mocIds) {
	    		String[] chapterData = data.split("#");
	    		result = qmsQmMappingOfClassesRepo.addToCheckListMaster(chapterData[0],chapterData[1],username,LocalDateTime.now());
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method addChapterToMasters() " + e);
	    }
	    return result;
	}

	@Override
	public Integer updateCheckListChapters(List<Long> mocIds, String username) throws Exception {
	    logger.info(" AuditServiceImpl Inside method updateCheckListChapters()");
	    int result = 0;
	    try {
	    	result = qmsQmMappingOfClassesRepo.deleteCheckListChapters();
	    	if(result >0) {
		    	for(Long id : mocIds) {
		    		result = qmsQmMappingOfClassesRepo.updateCheckListChapters(id,username,LocalDateTime.now());
		    	}
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("AuditServiceImpl Inside method updateCheckListChapters() " + e);
	    }
	    return result;
	}
	
}
