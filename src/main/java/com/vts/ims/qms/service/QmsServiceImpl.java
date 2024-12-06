package com.vts.ims.qms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vts.ims.master.dto.DivisionGroupDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vts.ims.audit.repository.AuditeeRepository;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.DivisionEmployeeDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.qms.dto.DwpRevisionRecordDto;
import com.vts.ims.qms.dto.DwpSectionDto;
import com.vts.ims.qms.dto.QmsDocTypeDto;
import com.vts.ims.qms.dto.QmsIssueDto;
import com.vts.ims.qms.dto.QmsQmChaptersDto;
import com.vts.ims.qms.dto.QmsQmDocumentSummaryDto;
import com.vts.ims.qms.dto.QmsQmRevisionRecordDto;
import com.vts.ims.qms.dto.QmsQmSectionsDto;
import com.vts.ims.qms.model.QmsQmDocumentSummary;
import com.vts.ims.qms.model.QmsQmMappingOfClasses;
import com.vts.ims.qms.model.QmsQmRevisionRecord;
import com.vts.ims.qms.model.QmsQmSections;
import com.vts.ims.qms.repository.DwpChaptersRepo;
import com.vts.ims.qms.repository.DwpGwpDocumentSummaryRepo;
import com.vts.ims.qms.repository.DwpRevisionRecordRepo;
import com.vts.ims.qms.repository.DwpSectionsRepo;
import com.vts.ims.qms.repository.QmsAbbreviationsRepo;
import com.vts.ims.qms.repository.QmsQmChaptersRepo;
import com.vts.ims.qms.repository.QmsQmDocumentSummaryRepo;
import com.vts.ims.qms.repository.QmsQmMappingOfClassesRepo;
import com.vts.ims.qms.repository.QmsQmRevisionRecordRepo;
import com.vts.ims.qms.repository.QmsQmRevisionTransactionRepo;
import com.vts.ims.qms.repository.QmsQmSectionsRepo;
import com.vts.ims.qms.model.QmsQmRevisionTransaction;
import com.vts.ims.qms.model.DwpChapters;
import com.vts.ims.qms.model.DwpGwpDocumentSummary;
import com.vts.ims.qms.model.DwpRevisionRecord;
import com.vts.ims.qms.model.DwpSections;
import com.vts.ims.qms.model.QmsAbbreviations;
import com.vts.ims.qms.model.QmsQmChapters;

@Service
public class QmsServiceImpl implements QmsService {
	
	private static final Logger logger=LogManager.getLogger(QmsServiceImpl.class);

	@Value("${appStorage}")
	private String storageDrive;
	
	@Value("${x_api_key}")
	private String xApiKey;
	
	@Autowired
	private MasterClient masterClient;
	
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
	
	@Autowired
	private DwpGwpDocumentSummaryRepo dwpGwpDocumentSummaryRepo;
	
	@Autowired
	private AuditeeRepository auditeeRepository;
	
	
	@Override
	public List<QmsQmRevisionRecordDto> getQmVersionRecordDtoList() throws Exception {
		logger.info( " Inside getQmVersionRecordDtoList() " );
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
			logger.info( " Inside getQmVersionRecordDtoList() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQmRevisionRecordDto>();
		}
	}
	
	@Override
	public List<QmsQmChaptersDto> getAllQMChapters() throws Exception {
		logger.info( " Inside qmUnAddListToAddList() " );
		try {
			List<QmsQmChaptersDto> qmsQmChaptersDtoList = new ArrayList<QmsQmChaptersDto>();
//			long startTime = System.currentTimeMillis();
			List<QmsQmChapters> qmChapters = qmsQmChaptersRepo.findAllActiveQmChapters();
//	        long endTime = System.currentTimeMillis();
//	        System.out.println("Execution time: " + (endTime - startTime) + " ms");
//	        long startTime1 = System.currentTimeMillis();
//	        qmsQmChaptersRepo.findAllActiveQmChaptersss();
//	        long endTime1 = System.currentTimeMillis();
//	        System.out.println("Execution time: 1 " + (endTime1 - startTime1) + " ms");
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
			logger.info( " Inside getAllQMChapters() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQmChaptersDto>();
		}
	}
	
	
	@Override
	public List<QmsQmSectionsDto> getUnAddedQmSectionList() throws Exception {
		logger.info( " Inside qmUnAddListToAddList() " );
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
			logger.info( " Inside getUnAddedQmSectionList() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQmSectionsDto>();
		}
	}
	
	@Override
	public Long addNewQmSection(String sectionName, String username) throws Exception {
		logger.info( " Inside qmUnAddListToAddList() " );
		try {
			QmsQmSections qmsQmSections = new QmsQmSections();
			qmsQmSections.setSectionName(sectionName);
			qmsQmSections.setCreatedBy(username);
			qmsQmSections.setCreatedDate(LocalDateTime.now());
			qmsQmSections.setIsActive(1);
			return qmsQmSectionsRepo.save(qmsQmSections).getSectionId();
		} catch (Exception e) {
			logger.info( " Inside addNewQmSection() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long qmUnAddListToAddList(@RequestBody long[] selectedSections, @RequestHeader  String username) throws Exception {
		logger.info( " Inside qmUnAddListToAddList() " );
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
			logger.info( " Inside qmUnAddListToAddList() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public List<QmsQmChaptersDto> getQmSubChaptersById(Long chapterId) throws Exception {
		logger.info( " Inside getQmSubChaptersById() ");
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
			logger.info( " Inside getQmSubChaptersById() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQmChaptersDto>();
		}
	}
	
	@Override
	public Long addQmNewSubChapter(Long chapterId, String chapterName, String username) throws Exception {
		logger.info( " Inside addQmNewSubChapter() ");
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
			logger.info( " Inside addQmNewSubChapter() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long updateQmChapterContent(Long chapterId, String chapterContent, String username) throws Exception {
		logger.info( " Inside updateQmChapterContent() ");
		try {
			Long res = 0l;
			chapterContent = chapterContent.replace("\"", "");
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
			logger.info( " Inside updateQmChapterContent() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long updateQmChapterName(Long chapterId, String chapterName, String username) throws Exception {
		logger.info( " Inside updateQmChapterName() ");
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
			logger.error(  " Inside updateQmChapterName() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long addNewQmRevision(QmsQmRevisionRecordDto qmsQmRevisionRecordDto, String username) throws Exception {
		logger.info( " Inside addNewQmRevision() ");
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
			logger.error(  " Inside addNewQmRevision() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long addQmDocSummary(QmsQmDocumentSummaryDto qmsQmDocumentSummaryDto, String username) throws Exception {
		logger.info( " Inside addQmDocSummary() ");
		try {
			
			long res =0;
			
			QmsQmDocumentSummary qmsQmDocumentSummary = new QmsQmDocumentSummary();
			qmsQmDocumentSummary.setDocumentSummaryId(qmsQmDocumentSummaryDto.getDocumentSummaryId());
			qmsQmDocumentSummary.setAdditionalInfo(qmsQmDocumentSummaryDto.getAdditionalInfo());
			qmsQmDocumentSummary.setAbstract(qmsQmDocumentSummaryDto.getAbstract());
			qmsQmDocumentSummary.setKeywords(qmsQmDocumentSummaryDto.getKeywords());
			qmsQmDocumentSummary.setDistribution(qmsQmDocumentSummaryDto.getDistribution());
			qmsQmDocumentSummary.setRevisionRecordId(qmsQmDocumentSummaryDto.getRevisionRecordId());
			qmsQmDocumentSummary.setCreatedBy(qmsQmDocumentSummaryDto.getCreatedBy());
			qmsQmDocumentSummary.setCreatedDate(qmsQmDocumentSummaryDto.getCreatedDate());
			
			
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
			logger.error(  " Inside addQmDocSummary() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public QmsQmDocumentSummaryDto getQmDocSummarybyId(long documentSummaryId) throws Exception {
		logger.info( " Inside getQmDocSummarybyId() ");
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
			logger.error(  " Inside getQmDocSummarybyId() "+ e );
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public QmsQmDocumentSummary getQmDocSummarybyRevisionRecordId(long revisionRecordId) throws Exception {
		logger.info( " Inside getQmDocSummarybyRevisionRecordId() ");
		try {
			QmsQmDocumentSummary existingSummary = qmsQmDocumentSummaryRepo.findByRevisionRecordId(revisionRecordId);
		
			
			return existingSummary;
		} catch (Exception e) {
			logger.error(  " Inside getQmDocSummarybyRevisionRecordId() "+ e );
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
			logger.error( "Inside DAO deleteQmChapterById() " + e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public QmsQmChaptersDto getQmChapterById(long chapterId) throws Exception {
		logger.info( " Inside getQmChapterById() " );
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
			logger.error(  " Inside getQmChapterById() "+ e );
			e.printStackTrace();
			return QmsQmChaptersDto.builder().build();
		}
	}
	
	
	@Override
	public long updatechapterPagebreakAndLandscape(String[] chapterPagebreakOrLandscape, String username) throws Exception {
		logger.info( " Inside updatechapterPagebreakAndLandscape() " );
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
			logger.error( " Inside updatechapterPagebreakAndLandscape " +e);
			return 0;
		}
	}
	
	@Override
	public List<QmsAbbreviations> getAbbreviationList(String abbreviationIdNotReq) throws Exception {
		logger.info( " Inside getAbbreviationList() " );
		try {
			
			String abbreviationId = "";
			if(abbreviationIdNotReq != null) {
				abbreviationIdNotReq = abbreviationIdNotReq.trim();
				abbreviationId = abbreviationIdNotReq.replace("\"", "");
			}
			
			List<QmsAbbreviations> abbreviationList =  qmsAbbreviationsRepo.findValidAbbreviations(abbreviationId);
			
			return abbreviationList;
		} catch (Exception e) {
			logger.error( " Inside getAbbreviationList() " +e);
			return new ArrayList<QmsAbbreviations>();
		}
	}
	
	@Override
	public QmsQmRevisionRecord getQmsQmRevisionRecord(Long revisionRecordId) throws Exception {
		logger.info( " Inside getQmsQmRevisionRecord() " );
		try {
			QmsQmRevisionRecord qmRevisionRecord = qmsQmRevisionRecordRepo.findById(revisionRecordId).orElse(null);
			return qmRevisionRecord;
		} catch (Exception e) {
			logger.error( " Inside getQmsQmRevisionRecord() " +e);
			return null;
		}
	}
	
	
	@Override
	public long updateNotReqQmAbbreviationIds(Long revisionRecordId, String abbreviationIds, String username) throws Exception {
		logger.info( " Inside updateNotReqQmAbbreviationIds() " );
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
			logger.error( "Inside service updateNotReqQmAbbreviationIds() " + e);
			return 0l;
		}
	}
	
	
	@Override
	public Long addMappingOfClasses(Long revisionRecordId, List<String[]> mocList, String username) throws Exception {
		logger.info( " Inside addMappingOfClasses() " );
		try {
			long res = 0;
			if(mocList.size()>1) {
				qmsQmMappingOfClassesRepo.deleteByRevisionRecordId(revisionRecordId);
				for(int i=1; i<mocList.size(); i++) {

					QmsQmMappingOfClasses isoMappingOfClasses = new QmsQmMappingOfClasses();
					isoMappingOfClasses.setSectionNo(mocList.get(i)[0]);
					isoMappingOfClasses.setClauseNo(mocList.get(i)[1]);
					isoMappingOfClasses.setDescription(mocList.get(i).length==3 ? mocList.get(i)[2] : "");
					isoMappingOfClasses.setRevisionRecordId(revisionRecordId);
					res=qmsQmMappingOfClassesRepo.save(isoMappingOfClasses).getMocId();
				}
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( "Inside service addMappingOfClasses() " + e);
			return 0l;
		}
	}
	
	@Override
	public List<Object[]> getMocList(Long revisionRecordId) throws Exception {
		logger.info( " Inside getMocList() " );
		try {
			return qmsQmMappingOfClassesRepo.findAllByRevisionRecordId(revisionRecordId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( "Inside service getMocList() " + e);
			return new ArrayList<Object[]>();
		}
	}
	
	@Override
	public List<DwpRevisionRecordDto> getDwpVersionRecordDtoList(QmsDocTypeDto qmsDocTypeDto) throws Exception {
		logger.info( " Inside getQmVersionRecordDtoList() " );
		try {
			
			List<DivisionMasterDto> divisionDtoList = masterClient.getDivisionMaster(xApiKey);
			List<DivisionGroupDto> divisiongroupDtoList = masterClient.getDivisionGroupList(xApiKey);
			
			
			List<DwpRevisionRecordDto> revisionRecordDtoList = new ArrayList<DwpRevisionRecordDto>();
			List<DwpRevisionRecord> revisionRecord = dwpRevisionRecordRepo.findAllActiveDwpRecordsByDocType(qmsDocTypeDto.getDocType(), qmsDocTypeDto.getGroupDivisionId());
			revisionRecord.forEach(revison -> {
				
				DivisionMasterDto divisionDto = null;
				DivisionGroupDto divisiongroupDto = null;
				
				if(revison.getDocType().equalsIgnoreCase("dwp")) {
					
					divisionDto = divisionDtoList.stream()
			    	        .filter(division -> division.getDivisionId().equals(revison.getGroupDivisionId()))
			    	        .findFirst()
			    	        .orElse(null);
					
				} else if(revison.getDocType().equalsIgnoreCase("gwp")) {
					
					divisiongroupDto = divisiongroupDtoList.stream()
			    	        .filter(obj -> obj.getGroupId().equals(revison.getGroupDivisionId()))
			    	        .findFirst()
			    	        .orElse(null);
				}
				
				DwpRevisionRecordDto qmsQmRevisionRecordDto = DwpRevisionRecordDto.builder()
						.RevisionRecordId(revison.getRevisionRecordId())
						.DocType(revison.getDocType())
						.GroupDivisionId(revison.getGroupDivisionId())
						.divisionMasterDto(divisionDto)
						.divisionGroupDto(divisiongroupDto)
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
			logger.error( " Inside getDwpVersionRecordDtoList() "+ e );
			e.printStackTrace();
			return new ArrayList<DwpRevisionRecordDto>();
		}
	}
	
	@Override
	public List<DwpChapters> getAllDwpChapters(QmsDocTypeDto qmsDocTypeDto) throws Exception {
		logger.info( " Inside getAllDwpChapters() " );
		try {
			List<DwpChapters> chapters = dwpChaptersRepo.findAllActiveDwpChapters(qmsDocTypeDto.getDocType(), qmsDocTypeDto.getGroupDivisionId());
			return chapters;
		} catch (Exception e) {
			logger.error( " Inside getAllDwpChapters() "+ e );
			e.printStackTrace();
			return new ArrayList<DwpChapters>();
		}
	}
	
	
	@Override
	public Long updateDwpChapterName(Long chapterId, String chapterName, String username) throws Exception {
		logger.info( " Inside updateDwpChapterName() ");
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
			logger.error(  " Inside updateDwpChapterName() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public List<DwpChapters> getDwpSubChaptersById(Long chapterId) throws Exception {
		logger.info( " Inside getDwpSubChaptersById() ");
		try {
			List<DwpChapters> chapters = dwpChaptersRepo.findByChapterParentIdAndIsActive(chapterId, 1);
			return chapters;
		} catch (Exception e) {
			logger.error( " Inside getDwpSubChaptersById() "+ e );
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
			logger.error( "Inside DAO deleteDwpChapterById() " + e);
			e.printStackTrace();
			return 0;
		}
	}
	
	
	@Override
	public Long addDwpNewSubChapter(Long chapterId, String chapterName, String username) throws Exception {
		logger.info( " Inside addDwpNewSubChapter() ");
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
			logger.error(  " Inside addDwpNewSubChapter() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public long updateDwpPagebreakAndLandscape(String[] chapterPagebreakOrLandscape, String username) throws Exception {
		logger.info( " Inside updateDwpPagebreakAndLandscape() " );
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
			logger.error( " Inside updateDwpPagebreakAndLandscape() " +e);
			return 0;
		}
	}
	
	@Override
	public DwpChapters getDwpChapterById(long chapterId) throws Exception {
		logger.info( " Inside getDwpChapterById() " );
		try {
			Optional<DwpChapters> optionalChapters = dwpChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				DwpChapters chapters = optionalChapters.get();
				return chapters;
			}
			return new DwpChapters();
		} catch (Exception e) {
			logger.error(" Inside getDwpChapterById() "+ e );
			e.printStackTrace();
			return new DwpChapters();
		}
	}
	
	
	@Override
	public Long updateDwpChapterContent(Long chapterId, String chapterContent, String username) throws Exception {
		logger.info( " Inside updateDwpChapterContent() ");
		try {
			Long res = 0l;
			chapterContent = chapterContent.replace("\"", "");
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
			logger.error( " Inside updateDwpChapterContent() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public List<DwpSections> getDwpUnAddedQmSectionList(QmsDocTypeDto qmsDocTypeDto) throws Exception {
		logger.info( " Inside qmUnAddListToAddList() " );
		try {
			List<DwpSections> sections = dwpSectionsRepo.findSectionsNotInChapters(qmsDocTypeDto.getDocType(), qmsDocTypeDto.getGroupDivisionId());
			
			
			return sections;
		} catch (Exception e) {
			logger.error( " Inside getUnAddedQmSectionList() "+ e );
			e.printStackTrace();
			return new ArrayList<DwpSections>();
		}
	}
	
	@Override
	public Long addNewDwpSection(DwpSectionDto dwpSectionDto, String username) throws Exception {
		logger.info( " Inside addNewDwpSection() " );
		try {
			DwpSections sections = new DwpSections();
			sections.setSectionName(dwpSectionDto.getSectionName());
			sections.setGroupDivisionId(dwpSectionDto.getGroupDivisionId());
			sections.setDocType(dwpSectionDto.getDocType());
			sections.setCreatedBy(username);
			sections.setCreatedDate(LocalDateTime.now());
			sections.setIsActive(1);
			return dwpSectionsRepo.save(sections).getSectionId();
		} catch (Exception e) {
			logger.error( " Inside addNewDwpSection() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public Long dwpUnAddListToAddList(@RequestBody long[] selectedSections, @RequestHeader  String username) throws Exception {
		logger.info( " Inside dwpUnAddListToAddList() " );
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
			logger.error( " Inside dwpUnAddListToAddList() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public DwpRevisionRecord getDwpRevisionRecord(Long revisionRecordId) throws Exception {
		logger.info( " Inside getDwpRevisionRecord() " );
		try {
			DwpRevisionRecord dwpRevisionRecord = dwpRevisionRecordRepo.findById(revisionRecordId).orElse(null);
			return dwpRevisionRecord;
		} catch (Exception e) {
			logger.error( " Inside getDwpRevisionRecord() " +e);
			return null;
		}
	}
	
	@Override
	public long updateNotReqDwpAbbreviationIds(Long revisionRecordId, String abbreviationIds, String username) throws Exception {
		logger.info( " Inside updateNotReqDwpAbbreviationIds() " );
		try {
			long res =0;
			Optional<DwpRevisionRecord> optionalRevisionRecord = dwpRevisionRecordRepo.findById(revisionRecordId);
			if(optionalRevisionRecord.isPresent()) {
				DwpRevisionRecord qmRevisionRecord = optionalRevisionRecord.get();
				qmRevisionRecord.setAbbreviationIdNotReq(abbreviationIds);
				res = dwpRevisionRecordRepo.save(qmRevisionRecord).getRevisionRecordId();
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( "Inside service updateNotReqDwpAbbreviationIds() " + e);
			return 0l;
		}
	}
	
	@Override
	public Long addDwpDocSummary(DwpGwpDocumentSummary documentSummary, String username) throws Exception {
		logger.info( " Inside addDwpDocSummary() ");
		try {
			
			long res =0;
			
			DwpGwpDocumentSummary newDocumentSummary = new DwpGwpDocumentSummary();
			newDocumentSummary.setDocumentSummaryId(documentSummary.getDocumentSummaryId());
			newDocumentSummary.setAdditionalInfo(documentSummary.getAdditionalInfo());
			newDocumentSummary.setAbstract(documentSummary.getAbstract());
			newDocumentSummary.setKeywords(documentSummary.getKeywords());
			newDocumentSummary.setDistribution(documentSummary.getDistribution());
			newDocumentSummary.setRevisionRecordId(documentSummary.getRevisionRecordId());
			newDocumentSummary.setCreatedBy(documentSummary.getCreatedBy());
			newDocumentSummary.setCreatedDate(documentSummary.getCreatedDate());
			
			
			if(newDocumentSummary.getDocumentSummaryId() >0 ) {
				
				newDocumentSummary.setModifiedBy(username);
				newDocumentSummary.setModifiedDate(LocalDateTime.now());
				res = dwpGwpDocumentSummaryRepo.save(newDocumentSummary).getDocumentSummaryId();
				
			} else {
				newDocumentSummary.setCreatedBy(username);
				newDocumentSummary.setCreatedDate(LocalDateTime.now());
				res = dwpGwpDocumentSummaryRepo.save(newDocumentSummary).getDocumentSummaryId();
			}
			
			return res;
			
		} catch (Exception e) {
			logger.error(  " Inside addDwpDocSummary() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public DwpGwpDocumentSummary getDwpDocSummarybyRevisionRecordId(long revisionRecordId) throws Exception {
		logger.info( " Inside getQmDocSummarybyRevisionRecordId() ");
		try {
			DwpGwpDocumentSummary existingSummary = dwpGwpDocumentSummaryRepo.findByRevisionRecordId(revisionRecordId);
				
			return existingSummary;
		} catch (Exception e) {
			logger.error(  " Inside getQmDocSummarybyRevisionRecordId() "+ e );
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public List<DivisionMasterDto> getDwpDivisionMaster(Integer imsFormRoleId, Long empId) throws Exception {
	    logger.info("Inside getDwpDivisionMaster()");
	    try {

	        List<Integer> isAllList = Arrays.asList(2, 7);
	        List<DivisionMasterDto> divisionDto = masterClient.getDivisionMaster(xApiKey);

	        List<DivisionMasterDto> activeAllDivisionDto = divisionDto.stream()
	            .filter(dto -> dto.getIsActive() == 1)
	            .collect(Collectors.toList());

	        if (isAllList.contains(imsFormRoleId)) {
	            return activeAllDivisionDto;
	        }

			List<EmployeeDto> emp = masterClient.getEmployee(xApiKey, empId);

			EmployeeDto empDto = emp.size() > 0 ? emp.get(0) : EmployeeDto.builder().build();

	        List<DivisionEmployeeDto> divisionEmployeeDtoList = masterClient.getDivisionEmpDetailsById(xApiKey);
	        List<DivisionEmployeeDto> divisionEmployeeDtoListByEmpId = divisionEmployeeDtoList.stream().filter(dto -> dto.getEmpId().equals(empId)).collect(Collectors.toList());
	        List<Long> auditeeDivisionIds = auditeeRepository.findDivisionIdsByEmpId(empId);
	        

	        List<DivisionMasterDto> returnDivisionList = activeAllDivisionDto.stream()
	            .filter(obj -> divisionEmployeeDtoListByEmpId.stream()
	            		.anyMatch(dto -> dto.getDivisionId().equals(obj.getDivisionId()))
	                    || empDto.getDivisionId().equals(obj.getDivisionId()) || (auditeeDivisionIds.contains(obj.getDivisionId())))
	            .collect(Collectors.toList());

	        return returnDivisionList;
	    } catch (Exception e) {
	        e.printStackTrace();
	        logger.error("Error in getDwpDivisionMaster() ", e);
	        return Collections.emptyList();
	    }
	}

	@Override
	public List<DivisionGroupDto> getDwpDivisionGroupList(Integer imsFormRoleId, Long empId) throws Exception {
		logger.info("Inside getDwpDivisionGroupList()");
		try {

			List<Integer> isAllList = Arrays.asList(2, 7);

			List<DivisionGroupDto> divisiongroupdto = masterClient.getDivisionGroupList(xApiKey);
			List<DivisionGroupDto> activeDivisiongroupdto = divisiongroupdto.stream()
					.filter(dto -> dto.getIsActive()== 1)
					.collect(Collectors.toList());


			if (isAllList.contains(imsFormRoleId)) {
				return activeDivisiongroupdto;
			}

			List<DivisionMasterDto> divisionDto = masterClient.getDivisionMaster(xApiKey);
			List<EmployeeDto> emp = masterClient.getEmployee(xApiKey, empId);

			EmployeeDto empDto = emp.size() > 0 ? emp.get(0) : EmployeeDto.builder().build();

			List<DivisionMasterDto> activeAllDivisionDto = divisionDto.stream()
					.filter(dto -> dto.getIsActive() == 1)
					.collect(Collectors.toList());

			List<DivisionEmployeeDto> divisionEmployeeDtoList = masterClient.getDivisionEmpDetailsById(xApiKey);
			List<DivisionEmployeeDto> divisionEmployeeDtoListByEmpId = divisionEmployeeDtoList.stream().filter(dto -> dto.getEmpId().equals(empId)).collect(Collectors.toList());
			List<Long> auditeeDivisionIds = auditeeRepository.findDivisionIdsByEmpId(empId);
			List<Long> auditeeDivisionGroupIds = auditeeRepository.findDivisionGroupIdsByEmpId(empId);


			List<DivisionMasterDto> divisionValidList = activeAllDivisionDto.stream()
					.filter(obj -> divisionEmployeeDtoListByEmpId.stream()
							.anyMatch(dto -> dto.getDivisionId().equals(obj.getDivisionId()))
							|| empDto.getDivisionId().equals(obj.getDivisionId()) || (auditeeDivisionIds.contains(obj.getDivisionId())))
					.collect(Collectors.toList());


			List<DivisionGroupDto> rturnDivisiongroupdto =  activeDivisiongroupdto.stream().filter(obj -> divisionValidList.stream()
					.anyMatch(dto -> dto.getGroupId().equals(obj.getGroupId())) || (auditeeDivisionGroupIds.contains(obj.getGroupId()))).collect(Collectors.toList());



			return rturnDivisiongroupdto;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getDwpDivisionGroupList() ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public DwpRevisionRecord addNewDwpRevisionRecord(QmsIssueDto qmsIssueDto, String username) throws Exception {
		logger.info("Inside addNewDwpRevisionRecord() ");

		try {
			
	
			DwpRevisionRecord dwpRevisionRecord = new DwpRevisionRecord();
	
//			Login login = loginRepo.findByUsername(username);
//			EmployeeDto emp = masterClient.getEmployee(xApiKey, login.getEmpId()).get(0);
			
			String version = qmsIssueDto.getNewAmendVersion();
			String issueVersion = version.split("-")[0];
			String releaseVersion = version.split("-")[1];
			
			dwpRevisionRecord.setGroupDivisionId(qmsIssueDto.getGroupDivisionId());
			dwpRevisionRecord.setDescription(qmsIssueDto.getAmendParticulars());
			dwpRevisionRecord.setIssueNo(Integer.parseInt(issueVersion.substring(1)));
			dwpRevisionRecord.setRevisionNo(Integer.parseInt(releaseVersion.substring(1)));
			dwpRevisionRecord.setStatusCode("INI");
			dwpRevisionRecord.setDateOfRevision((LocalDate.now()));
			dwpRevisionRecord.setDocType(qmsIssueDto.getDocType());
			dwpRevisionRecord.setCreatedDate(LocalDateTime.now());
			dwpRevisionRecord.setCreatedBy(username);
			dwpRevisionRecord.setIsActive(1);

//			res = dwpRevisionRecordRepo.save(dwpRevisionRecord).getRevisionRecordId();
			
			return dwpRevisionRecordRepo.save(dwpRevisionRecord);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in addNewDwpRevisionRecord() ", e);
			return null;
		}
		
	}
	
}
