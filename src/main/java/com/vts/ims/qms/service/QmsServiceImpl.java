package com.vts.ims.qms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vts.ims.qms.dto.*;
import com.vts.ims.qms.model.*;
import com.vts.ims.qms.repository.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vts.ims.repository.NotificationRepository;
import com.vts.ims.audit.repository.AuditeeRepository;
import com.vts.ims.master.dao.MasterClient;
import com.vts.ims.master.dto.DivisionEmployeeDto;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.LoginDetailsDto;
import com.vts.ims.master.dto.ProjectEmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;
import com.vts.ims.master.service.MasterService;
import com.vts.ims.model.ImsNotification;
import com.vts.ims.qms.model.DwpChapters;
import com.vts.ims.qms.model.DwpGwpDocumentSummary;
import com.vts.ims.qms.model.DwpRevisionRecord;
import com.vts.ims.qms.model.DwpSections;
import com.vts.ims.qms.model.DwpSectionsMaster;
import com.vts.ims.qms.model.DwpTransaction;
import com.vts.ims.qms.model.QmsAbbreviations;
import com.vts.ims.qms.model.QmsDocStatus;
import com.vts.ims.qms.model.QmsQmChapters;
import com.vts.ims.qms.model.QmsQmDocumentSummary;
import com.vts.ims.qms.model.QmsQmMappingOfClasses;
import com.vts.ims.qms.model.QmsQmRevisionRecord;
import com.vts.ims.qms.model.QmsQmRevisionTransaction;
import com.vts.ims.qms.model.QmsQmSections;
import com.vts.ims.qms.repository.DwpChaptersRepo;
import com.vts.ims.qms.repository.DwpGwpDocumentSummaryRepo;
import com.vts.ims.qms.repository.DwpRevisionRecordRepo;
import com.vts.ims.qms.repository.DwpRevisionTransactionRepo;
import com.vts.ims.qms.repository.DwpSectionsMasterRepo;
import com.vts.ims.qms.repository.DwpSectionsRepo;
import com.vts.ims.qms.repository.MrMastersRepo;
import com.vts.ims.qms.repository.QmsAbbreviationsRepo;
import com.vts.ims.qms.repository.QmsDocStatusRepo;
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

	@Autowired
	private DwpSectionsMasterRepo sectionsMasterRepo;

	@Autowired
	private QmsQspRevisionRecordRepo qspRevisionRecordRepo;

	@Autowired
	QmsQspChaptersRepo qspChaptersRepo;

	@Autowired
	QmsQspDocumentSummaryRepo qspDocumentSummaryRepo;

	@Autowired
	QmsDocStatusRepo qmsdocstatusrepo;

	@Autowired
	DwpRevisionTransactionRepo dwpTransactionrepo;

	@Autowired
	MrMastersRepo mrMastersrepo;
	
	@Autowired
	QmsQspRevisionTransactionRepo qmsqsprevisiontransactionrepo;
	
	
	@Autowired
	MasterService masterService;
	
	@Autowired
	NotificationRepository notificationRepo;
	

	@Override
	public List<QmsQmRevisionRecordDto> getQmVersionRecordDtoList() throws Exception {
		logger.info( " Inside getQmVersionRecordDtoList() " );
		try {


			List<QmsQmRevisionRecordDto> qmsQmRevisionRecordDtoList = new ArrayList<QmsQmRevisionRecordDto>();
			List<QmsQmRevisionRecord> qmRevisionRecord = qmsQmRevisionRecordRepo.findAllActiveQmRecords();
			List<QmsDocStatus> qmsdocStatus=qmsdocstatusrepo.findAll();
			List<EmployeeDto> employeeList=masterClient.getEmployeeList(xApiKey);
			Map<Long, EmployeeDto> employeeMap = employeeList.stream()
					.filter(employee -> employee.getEmpId() != null)
					.collect(Collectors.toMap(EmployeeDto::getEmpId, employee -> employee));

			Map<String, String> statusCodeToStatusMap = qmsdocStatus.stream()
					.collect(Collectors.toMap(QmsDocStatus::getStatusCode, QmsDocStatus::getStatus));

			qmRevisionRecord.forEach(revison -> {
				EmployeeDto initiatedBy =  employeeMap.get(revison.getInitiatedBy());
				EmployeeDto reviewed =  employeeMap.get(revison.getReviewedBy());
				EmployeeDto approved =  employeeMap.get(revison.getApprovedBy());
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
						.InitiatedBy(revison.getInitiatedBy())
						.ReviewedBy(revison.getReviewedBy())
						.ApprovedBy(revison.getApprovedBy())
						.StatusCodeNext(revison.getStatusCodeNext())
						.InitiatedByEmployee(initiatedBy != null ? initiatedBy.getEmpName() + ", " + initiatedBy.getEmpDesigName() : null)
						.ReviewedByEmployee(reviewed != null ? reviewed.getEmpName() + ", " + reviewed.getEmpDesigName() : null)
						.ApprovedByEmployee(approved != null ? approved.getEmpName() + ", " + approved.getEmpDesigName() : null)
						.CreatedBy(revison.getCreatedBy())
						.CreatedDate(revison.getCreatedDate())
						.ModifiedBy(revison.getModifiedBy())
						.ModifiedDate(revison.getModifiedDate())
						.IsActive(revison.getIsActive())
						.Status(statusCodeToStatusMap.getOrDefault(revison.getStatusCode(), "Unknown Status"))
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
			chapterContent = chapterContent.replace("\\", "");
			if (chapterContent.startsWith("\"") && chapterContent.endsWith("\"")) {
				chapterContent = chapterContent.substring(1, chapterContent.length() - 1);
			}
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
		long res = 0;
		try {
			QmsQmRevisionRecord qmsQmRevisionRecord = new QmsQmRevisionRecord();

			qmsQmRevisionRecord.setDescription(qmsQmRevisionRecordDto.getDescription());
			qmsQmRevisionRecord.setIssueNo(qmsQmRevisionRecordDto.getIssueNo());
			qmsQmRevisionRecord.setRevisionNo(qmsQmRevisionRecordDto.getRevisionNo());
			qmsQmRevisionRecord.setStatusCode("INI");
			qmsQmRevisionRecord.setStatusCodeNext("INI");
			qmsQmRevisionRecord.setAbbreviationIdNotReq(qmsQmRevisionRecordDto.getAbbreviationIdNotReq());
			qmsQmRevisionRecord.setDateOfRevision(LocalDate.now());
			qmsQmRevisionRecord.setCreatedDate(LocalDateTime.now());
			qmsQmRevisionRecord.setCreatedBy(username);
			qmsQmRevisionRecord.setIsActive(1);

			res = qmsQmRevisionRecordRepo.save(qmsQmRevisionRecord).getRevisionRecordId();

//			QmsQmRevisionTransaction trans = new QmsQmRevisionTransaction();
//			trans.setEmpId(qmsQmRevisionRecordDto.getEmpId());
//			trans.setRevisionRecordId(qmsQmRevisionRecord.getRevisionRecordId());
//			trans.setStatusCode(qmsQmRevisionRecord.getStatusCode());
//			trans.setTransactionDate(LocalDateTime.now());
//			trans.setRemarks(null);
//			qmsQmRevisionTransactionRepo.save(trans);

			return res;
		} catch (Exception e) {
			logger.error(  " Inside addNewQmRevision() "+ e );
			e.printStackTrace();
			return res;
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
				qmRevisionRecord.setModifiedBy(username);
				qmRevisionRecord.setModifiedDate(LocalDateTime.now());
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
					isoMappingOfClasses.setIsForCheckList("N");
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
	//public List<DwpRevisionRecordDto> getDwpVersionRecordDtoList(Long divisionId) throws Exception {
	public List<DwpRevisionRecordDto> getDwpVersionRecordDtoList(QmsDocTypeDto qmsDocTypeDto) throws Exception {
		logger.info( " Inside getQmVersionRecordDtoList() " );
		try {

			List<DivisionMasterDto> divisionDtoList = masterClient.getDivisionMaster(xApiKey);
			List<DivisionGroupDto> divisiongroupDtoList = masterClient.getDivisionGroupList(xApiKey);

			List<QmsDocStatus> qmsdocStatus=qmsdocstatusrepo.findAll();
			List<EmployeeDto> employeeList=masterClient.getEmployeeList(xApiKey);
			Map<Long, EmployeeDto> employeeMap = employeeList.stream()
					.filter(employee -> employee.getEmpId() != null)
					.collect(Collectors.toMap(EmployeeDto::getEmpId, employee -> employee));

			Map<String, String> statusCodeToStatusMap = qmsdocStatus.stream()
					.collect(Collectors.toMap(QmsDocStatus::getStatusCode, QmsDocStatus::getStatus));

			List<DwpRevisionRecordDto> revisionRecordDtoList = new ArrayList<DwpRevisionRecordDto>();
			List<DwpRevisionRecord> revisionRecord = dwpRevisionRecordRepo.findAllActiveDwpRecordsByDocType(qmsDocTypeDto.getDocType(), qmsDocTypeDto.getGroupDivisionId());
			revisionRecord.forEach(revison -> {
				EmployeeDto initiatedBy =  employeeMap.get(revison.getInitiatedBy());
				EmployeeDto reviewed =  employeeMap.get(revison.getReviewedBy());
				EmployeeDto approved =  employeeMap.get(revison.getApprovedBy());
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
						.InitiatedBy(revison.getInitiatedBy())
						.ReviewedBy(revison.getReviewedBy())
						.ApprovedBy(revison.getApprovedBy())
						.StatusCodeNext(revison.getStatusCodeNext())
						.InitiatedByEmployee(initiatedBy != null ? initiatedBy.getEmpName() + ", " + initiatedBy.getEmpDesigName() : null)
						.ReviewedByEmployee(reviewed != null ? reviewed.getEmpName() + ", " + reviewed.getEmpDesigName() : null)
						.ApprovedByEmployee(approved != null ? approved.getEmpName() + ", " + approved.getEmpDesigName() : null)
						.CreatedBy(revison.getCreatedBy())
						.CreatedDate(revison.getCreatedDate())
						.ModifiedBy(revison.getModifiedBy())
						.ModifiedDate(revison.getModifiedDate())
						.IsActive(revison.getIsActive())
						.Status(statusCodeToStatusMap.getOrDefault(revison.getStatusCode(), "Unknown Status"))
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
			chapterContent = chapterContent.replace("\\", "");
			if (chapterContent.startsWith("\"") && chapterContent.endsWith("\"")) {
				chapterContent = chapterContent.substring(1, chapterContent.length() - 1);
			}
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
	public Integer updateChapterDescById(CheckListMasterDto checkListMasterDto, String username) throws Exception {

	    logger.info(" QmsServiceImpl Inside method updateChapterDescById()");
	    Integer result = 0;
	    try {
	    	 result = qmsQmMappingOfClassesRepo.updateMoc(checkListMasterDto.getMocId(),checkListMasterDto.getDescription(),username,LocalDateTime.now());
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("QmsServiceImpl Inside method updateChapterDescById() " + e);
	    }
	    return result;
	}

	@Override
	public Integer deleteChapterDescById(String mocId, String username) throws Exception {

	    logger.info(" QmsServiceImpl Inside method deleteChapterDescById()");
	    Integer result = 0;
	    try {
	    	 result = qmsQmMappingOfClassesRepo.deleteMoc(mocId,username,LocalDateTime.now());
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("QmsServiceImpl Inside method deleteChapterDescById() " + e);
	    }
	    return result;

	}

	@Override
	public Integer deleteSubChapterDescById(String mocId, String username) throws Exception {
	    logger.info(" QmsServiceImpl Inside method deleteSubChapterDescById()");
	    Integer result = 0;
	    try {
	    	 result = qmsQmMappingOfClassesRepo.deleteSubChapter(mocId,username,LocalDateTime.now());
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("QmsServiceImpl Inside method deleteSubChapterDescById() " + e);
	    }
	    return result;
	}

	@Override
	public Long addNewChapter(CheckListMasterDto checkListMasterDto, String username) throws Exception {

	    logger.info(" QmsServiceImpl Inside method addNewChapter()");
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
	        logger.error("QmsServiceImpl Inside method addNewChapter() " + e);
	    }
	    return result;
	}

	@Override
	public int addChapterToMasters(List<String> mocIds, String username) throws Exception {
	    logger.info(" QmsServiceImpl Inside method addChapterToMasters()");
	    int result = 0;
	    try {
	    	for(String data : mocIds) {
	    		String[] chapterData = data.split("#");
	    		result = qmsQmMappingOfClassesRepo.addToCheckListMaster(chapterData[0],chapterData[1],username,LocalDateTime.now());
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("QmsServiceImpl Inside method addChapterToMasters() " + e);
	    }
	    return result;

	}

	@Override
	public Integer updateCheckListChapters(List<Long> mocIds, String username) throws Exception {
	    logger.info(" QmsServiceImpl Inside method updateCheckListChapters()");
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
	        logger.error("QmsServiceImpl Inside method updateCheckListChapters() " + e);
	    }
	    return result;
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
	public List<ProjectMasterDto> getDwpProjectMaster(Integer imsFormRoleId, Long empId) throws Exception{
		logger.info("Inside getDwpProjectMaster()");
		try {
			List<Integer> isAllList = Arrays.asList(1, 2, 3, 4);
			List<ProjectMasterDto> projectDto = masterClient.getProjectMasterList(xApiKey);
			List<ProjectMasterDto> activeAllProjectDto = projectDto.stream()
					.filter(dto -> dto.getIsActive() == 1)
					.collect(Collectors.toList());

			if (isAllList.contains(imsFormRoleId)) {
				return activeAllProjectDto;
			}
			
			//List<EmployeeDto> emp = masterClient.getEmployee(xApiKey, empId);

			//EmployeeDto empDto = emp.size() > 0 ? emp.get(0) : EmployeeDto.builder().build();
			
			List<ProjectEmployeeDto> projectEmployeeDtoList = masterClient.getProjectEmpDetailsById(xApiKey);
			List<ProjectEmployeeDto> projectEmployeeDtoListByEmpId = projectEmployeeDtoList.stream().filter(dto -> dto.getEmpId().equals(empId) && dto.getIsActive() == 1).collect(Collectors.toList());
			List<Long> auditeeProjectIds = auditeeRepository.findProjectIdsByEmpId(empId);

			List<ProjectMasterDto> returnProjectList = activeAllProjectDto.stream()
					.filter(obj -> projectEmployeeDtoListByEmpId.stream()
							.anyMatch(dto -> dto.getProjectId().equals(obj.getProjectId()))
					|| (auditeeProjectIds.contains(obj.getProjectId())))
					.collect(Collectors.toList());

			return returnProjectList;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getDwpProjectMaster() ", e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<DivisionMasterDto> getDwpDivisionMaster(Integer imsFormRoleId, Long empId) throws Exception {
		logger.info("Inside getDwpDivisionMaster()");
		try {

			List<Integer> isAllList = Arrays.asList(1, 2, 3, 4);
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
			List<DivisionEmployeeDto> divisionEmployeeDtoListByEmpId = divisionEmployeeDtoList.stream().filter(dto -> dto.getEmpId().equals(empId) && dto.getIsActive() == 1).collect(Collectors.toList());
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

			List<Integer> isAllList = Arrays.asList(1, 2, 3, 4);

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
			List<DivisionEmployeeDto> divisionEmployeeDtoListByEmpId = divisionEmployeeDtoList.stream().filter(dto -> dto.getEmpId().equals(empId) && dto.getIsActive() == 1).collect(Collectors.toList());
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

			List<DwpSectionsMaster> dwpSectionsMaster = sectionsMasterRepo.findAll();
			if(!dwpSectionsMaster.isEmpty()){
				for (DwpSectionsMaster sectionsMaster : dwpSectionsMaster) {
					DwpSections master = new DwpSections();
					if(qmsIssueDto.getDocType().equalsIgnoreCase("gwp")){
						master.setDocType("gwp");
					}else{
						master.setDocType("dwp");
					}
					master.setSectionName(sectionsMaster.getSectionName());
					master.setGroupDivisionId(qmsIssueDto.getGroupDivisionId());
					master.setCreatedDate(LocalDateTime.now());
					master.setCreatedBy(username);
					master.setIsActive(1);
					dwpSectionsRepo.save(master);
				}
			}

			List<DwpSections> dwpSections = dwpSectionsRepo.findAllByGroupDivisionIdAndDocTypeAndIsActive(qmsIssueDto.getGroupDivisionId(),qmsIssueDto.getDocType(),1);
			if(!dwpSections.isEmpty()){
				for (DwpSections sections : dwpSections) {
					DwpChapters chapters = new DwpChapters();
					chapters.setSectionId(sections.getSectionId());
					chapters.setChapterParentId(0);
					chapters.setChapterName(sections.getSectionName());
					chapters.setChapterContent("");
					chapters.setIsLandscape('N');
					chapters.setIsPagebreakAfter('N');
					chapters.setCreatedDate(LocalDateTime.now());
					chapters.setCreatedBy(username);
					chapters.setIsActive(1);
					dwpChaptersRepo.save(chapters);
				}
			}

//			res = dwpRevisionRecordRepo.save(dwpRevisionRecord).getRevisionRecordId();

			return dwpRevisionRecordRepo.save(dwpRevisionRecord);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in addNewDwpRevisionRecord() ", e);
			return null;
		}

	}

	@Override
	public List<QmsQspRevisionRecordDto> getQspVersionRecordDtoList() throws Exception {
		logger.info( " Inside getQmVersionRecordDtoList() " );
		try {
			List<QmsQspRevisionRecordDto> qmsQspRevisionRecordDtoList = new ArrayList<>();

			// Fetching data from repositories and external client
			List<QmsQspRevisionRecord> qspRevisionRecords = qspRevisionRecordRepo.findAllActiveQspRecords();
			List<QmsDocStatus> qmsDocStatuses = qmsdocstatusrepo.findAll();
			List<EmployeeDto> employeeList = masterClient.getEmployeeList(xApiKey);

			// Mapping employee data by Employee ID
			Map<Long, EmployeeDto> employeeMap = employeeList.stream()
			        .filter(employee -> employee.getEmpId() != null)
			        .collect(Collectors.toMap(EmployeeDto::getEmpId, employee -> employee));

			// Mapping document statuses by Status Code
			Map<String, String> statusCodeToStatusMap = qmsDocStatuses.stream()
			        .collect(Collectors.toMap(QmsDocStatus::getStatusCode, QmsDocStatus::getStatus));

			// Creating DTO list from QSP revision records
			qspRevisionRecords.forEach(revision -> {
			    EmployeeDto initiatedBy = employeeMap.get(revision.getInitiatedBy());
			    EmployeeDto reviewedBy = employeeMap.get(revision.getReviewedBy());
			    EmployeeDto approvedBy = employeeMap.get(revision.getApprovedBy());

			    // Constructing the DTO object
			    QmsQspRevisionRecordDto qmsQspRevisionRecordDto = QmsQspRevisionRecordDto.builder()
			            .revisionRecordId(revision.getRevisionRecordId())
			            .docName(revision.getDocName() != null ? revision.getDocName().trim() : null)
			            .docFileName(revision.getDocFileName())
			            .docFilepath(revision.getDocFilepath())
			            .description(revision.getDescription())
			            .issueNo(revision.getIssueNo())
			            .revisionNo(revision.getRevisionNo())
			            .dateOfRevision(revision.getDateOfRevision())
			            .statusCode(revision.getStatusCode())
						.abbreviationIdNotReq(revision.getAbbreviationIdNotReq())
						.InitiatedBy(revision.getInitiatedBy())
						.ReviewedBy(revision.getReviewedBy())
						.ApprovedBy(revision.getApprovedBy())
						.StatusCodeNext(revision.getStatusCodeNext())
						.InitiatedByEmployee(initiatedBy != null ? initiatedBy.getEmpName() + ", " + initiatedBy.getEmpDesigName() : null)
						.ReviewedByEmployee(reviewedBy != null ? reviewedBy.getEmpName() + ", " + reviewedBy.getEmpDesigName() : null)
						.ApprovedByEmployee(approvedBy != null ? approvedBy.getEmpName() + ", " + approvedBy.getEmpDesigName() : null)
						.createdBy(revision.getCreatedBy())
						.createdDate(revision.getCreatedDate())
						.modifiedBy(revision.getModifiedBy())
						.modifiedDate(revision.getModifiedDate())
						.isActive(revision.getIsActive())
						.Status(statusCodeToStatusMap.getOrDefault(revision.getStatusCode(), "Unknown Status"))
			            .build();

			    qmsQspRevisionRecordDtoList.add(qmsQspRevisionRecordDto);
			});

			return qmsQspRevisionRecordDtoList;
		} catch (Exception e) {
			logger.info( " Inside getQmVersionRecordDtoList() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQspRevisionRecordDto>();
		}
	}

	@Override
	public List<QmsQspChapters> getAllQspChapters(QmsDocTypeDto qmsDocTypeDto) {
		logger.info( " Inside getAllQspChapters() " );
		try {
			List<QmsQspChapters> chapters = qspChaptersRepo.findAllActiveQspChapters(qmsDocTypeDto.getDocType());
			return chapters;
		} catch (Exception e) {
			logger.error( " Inside getAllQspChapters() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQspChapters>();
		}
	}

	@Override
	public List<QmsQspChapters> getQspSubChaptersById(Long chapterId) throws Exception {
		logger.info( " Inside getQspSubChaptersById() ");
		try {
			List<QmsQspChapters> chapters = qspChaptersRepo.findByChapterParentIdAndIsActive(chapterId, 1);
			return chapters;
		} catch (Exception e) {
			logger.error( " Inside getQspSubChaptersById() "+ e );
			e.printStackTrace();
			return new ArrayList<QmsQspChapters>();
		}
	}

	@Override
	public QmsQspRevisionRecord getQspRevisionRecord(Long revisionRecordId) throws Exception {
		logger.info( " Inside getQspRevisionRecord() " );
		try {
			QmsQspRevisionRecord qspRevisionRecord = qspRevisionRecordRepo.findById(revisionRecordId).orElse(null);
			return qspRevisionRecord;
		} catch (Exception e) {
			logger.error( " Inside getQspRevisionRecord() " +e);
			return null;
		}
	}

	@Override
	public QmsQspDocumentSummary getQspDocSummarybyRevisionRecordId(long revisionRecordId) throws Exception {
		logger.info( " Inside getQspDocSummarybyRevisionRecordId() ");
		try {
			QmsQspDocumentSummary existingSummary = qspDocumentSummaryRepo.findByRevisionRecordId(revisionRecordId);
			return existingSummary;
		} catch (Exception e) {
			logger.error(  " Inside getQspDocSummarybyRevisionRecordId() "+ e );
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public QmsQspChapters getQspChapterById(long chapterId) throws Exception {
		logger.info( " Inside getQspChapterById() " );
		try {
			Optional<QmsQspChapters> optionalChapters = qspChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQspChapters chapters = optionalChapters.get();
				return chapters;
			}
			return new QmsQspChapters();
		} catch (Exception e) {
			logger.error(" Inside getQspChapterById() "+ e );
			e.printStackTrace();
			return new QmsQspChapters();
		}
	}

	@Override
	public Long updateQspChapterContent(Long chapterId, String chapterContent, String username) throws Exception {
		logger.info( " Inside updateQspChapterContent() ");
		try {
			Long res = 0l;
			chapterContent = chapterContent.replace("\\", "");
			if (chapterContent.startsWith("\"") && chapterContent.endsWith("\"")) {
				chapterContent = chapterContent.substring(1, chapterContent.length() - 1);
			}
			Optional<QmsQspChapters> optionalChapters = qspChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQspChapters chapters = optionalChapters.get();
				chapters.setChapterContent(chapterContent);
				chapters.setModifiedBy(username);
				chapters.setModifiedDate(LocalDateTime.now());

				res = qspChaptersRepo.save(chapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.error( " Inside updateQspChapterContent() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Long addQspNewSubChapter(Long chapterId, String chapterName, String username) throws Exception {
		logger.info( " Inside addQspNewSubChapter() ");
		try {
			Long res =0l;
			Optional<QmsQspChapters> optionalChapters = qspChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQspChapters qspChapters = optionalChapters.get();
				QmsQspChapters chapters = new QmsQspChapters();
				chapters.setChapterName(chapterName);
				chapters.setChapterParentId(chapterId);
				chapters.setSectionId(qspChapters.getSectionId());
				chapters.setIsPagebreakAfter('N');
				chapters.setIsLandscape('N');
				chapters.setCreatedBy(username);
				chapters.setCreatedDate(LocalDateTime.now());
				chapters.setIsActive(1);
				res = qspChaptersRepo.save(chapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.error(  " Inside addQspNewSubChapter() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Long updateQspChapterName(Long chapterId, String chapterName, String username) throws Exception {
		logger.info( " Inside updateQspChapterName() ");
		try {
			Long res = 0l;
			Optional<QmsQspChapters> optionalChapters = qspChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQspChapters chapters = optionalChapters.get();
				chapters.setChapterName(chapterName);
				chapters.setModifiedBy(username);
				chapters.setModifiedDate(LocalDateTime.now());

				res = qspChaptersRepo.save(chapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.error(  " Inside updateQspChapterName() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Long deleteQspChapterById(long chapterId, String username) throws Exception {
		try {
			Long res = 0l;
			Optional<QmsQspChapters> optionalChapters = qspChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQspChapters chapters = optionalChapters.get();
				chapters.setIsActive(0);
				chapters.setModifiedBy(username);
				chapters.setModifiedDate(LocalDateTime.now());

				res = qspChaptersRepo.save(chapters).getChapterId();
			}
			return res;
		} catch (Exception e) {
			logger.error( "Inside DAO deleteDwpChapterById() " + e);
			e.printStackTrace();
			return 0l;
		}
	}

	@Override
	public Long updateQspPagebreakAndLandscape(String[] chaperContent, String username) throws Exception {
		logger.info( " Inside updateDwpPagebreakAndLandscape() " );
		try {
			long res=0;
			long chapterId = Long.parseLong(chaperContent[0]);
			String IsPagebreakAfter = chaperContent[1];
			String IsLandscape = chaperContent[2];


			Optional<QmsQspChapters> optionalChapters = qspChaptersRepo.findById(chapterId);
			if (optionalChapters.isPresent()) {
				QmsQspChapters chapters = optionalChapters.get();
				chapters.setIsPagebreakAfter(IsPagebreakAfter.charAt(0));
				chapters.setIsLandscape(IsLandscape.charAt(0));
				chapters.setModifiedBy(username);
				chapters.setModifiedDate(LocalDateTime.now());

				res = qspChaptersRepo.save(chapters).getChapterId();
			}

			return res;
		} catch (Exception e) {
			logger.error( " Inside updateDwpPagebreakAndLandscape() " +e);
			return 0l;
		}
	}

	@Override
	public long updateNotReqQspAbbreviationIds(Long revisionRecordId, String abbreviationIds, String username) throws Exception {
		logger.info( " Inside updateNotReqQspAbbreviationIds() " );
		try {
			long res =0;
			Optional<QmsQspRevisionRecord> optionalRevisionRecord = qspRevisionRecordRepo.findById(revisionRecordId);
			if(optionalRevisionRecord.isPresent()) {
				QmsQspRevisionRecord qspRevisionRecord = optionalRevisionRecord.get();
				qspRevisionRecord.setAbbreviationIdNotReq(abbreviationIds);
				qspRevisionRecord.setModifiedBy(username);
				qspRevisionRecord.setModifiedDate(LocalDateTime.now());
				res = qspRevisionRecordRepo.save(qspRevisionRecord).getRevisionRecordId();
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( "Inside service updateNotReqQspAbbreviationIds() " + e);
			return 0l;
		}
	}

	@Override
	public long addQspDocSummary(QmsQspDocumentSummary qspDocumentSummary, String username) throws Exception {
		logger.info( " Inside addQspDocSummary() ");
		try {

			long res =0;

			QmsQspDocumentSummary newDocumentSummary = new QmsQspDocumentSummary();
			newDocumentSummary.setDocumentSummaryId(qspDocumentSummary.getDocumentSummaryId());
			newDocumentSummary.setAdditionalInfo(qspDocumentSummary.getAdditionalInfo());
			newDocumentSummary.setAbstract(qspDocumentSummary.getAbstract());
			newDocumentSummary.setKeywords(qspDocumentSummary.getKeywords());
			newDocumentSummary.setDistribution(qspDocumentSummary.getDistribution());
			newDocumentSummary.setRevisionRecordId(qspDocumentSummary.getRevisionRecordId());
			newDocumentSummary.setCreatedBy(qspDocumentSummary.getCreatedBy());
			newDocumentSummary.setCreatedDate(qspDocumentSummary.getCreatedDate());


			if(newDocumentSummary.getDocumentSummaryId() >0 ) {

				newDocumentSummary.setModifiedBy(username);
				newDocumentSummary.setModifiedDate(LocalDateTime.now());
				res = qspDocumentSummaryRepo.save(newDocumentSummary).getDocumentSummaryId();

			} else {
				newDocumentSummary.setCreatedBy(username);
				newDocumentSummary.setCreatedDate(LocalDateTime.now());
				res = qspDocumentSummaryRepo.save(newDocumentSummary).getDocumentSummaryId();
			}

			return res;

		} catch (Exception e) {
			logger.error(  " Inside addQspDocSummary() "+ e );
			e.printStackTrace();
			return 0l;
		}
	}

	public Integer forwardQm(QmsQmRevisionRecordDto qmsqmrevisionDto, String username) throws Exception {
		logger.info("Inside forwardQm() ");
		try {
			Integer result=0;
			long res=0;
			if(qmsqmrevisionDto!=null) {
				Long revisionRecordId = qmsqmrevisionDto.getRevisionRecordId();
				QmsQmRevisionRecord qmsqmrevision = qmsQmRevisionRecordRepo.findByRevisionRecordId(revisionRecordId);
				List<String> forwardstatus = Arrays.asList("INI","RTM","RTD","RVM");
				List<String> reforwardstatus = Arrays.asList("RTM","RTD");
				String RevisionStatusCode = qmsqmrevision.getStatusCode();
				String RevisionStatusCodeNext = qmsqmrevision.getStatusCodeNext();
				String Action = qmsqmrevisionDto.getAction();
				if(Action.equalsIgnoreCase("A")) {
					if(forwardstatus.contains(RevisionStatusCode)) {
						if(RevisionStatusCode.equalsIgnoreCase("INI") || RevisionStatusCode.equalsIgnoreCase("RVM") ) {
							qmsqmrevision.setInitiatedBy(qmsqmrevisionDto.getInitiatedBy());
							qmsqmrevision.setReviewedBy(qmsqmrevisionDto.getReviewedBy());
							qmsqmrevision.setApprovedBy(qmsqmrevisionDto.getApprovedBy());
							qmsqmrevision.setStatusCode("FWD");
							if(qmsqmrevision.getReviewedBy()!=null) {
								qmsqmrevision.setStatusCodeNext("RWD");
							}
						}
						if(reforwardstatus.contains(RevisionStatusCode)) {
							qmsqmrevision.setStatusCode("RFD");
							if(qmsqmrevision.getReviewedBy()!=null) {
								qmsqmrevision.setStatusCodeNext("RWD");
							}
						}
					}else {
						qmsqmrevision.setStatusCode(RevisionStatusCodeNext);
						if(RevisionStatusCodeNext.equalsIgnoreCase("RWD")) {
							if(qmsqmrevision.getApprovedBy()!=null) {
								qmsqmrevision.setStatusCodeNext("APD");
							}

						}
					}
					res=qmsQmRevisionRecordRepo.save(qmsqmrevision).getRevisionRecordId();
				}else if(Action.equalsIgnoreCase("R")){
					if(RevisionStatusCodeNext.equalsIgnoreCase("RWD"))
					{
						qmsqmrevision.setStatusCode("RTM");
					}
					else if(RevisionStatusCodeNext.equalsIgnoreCase("APD"))
					{
						qmsqmrevision.setStatusCode("RTD");
					}
					res=qmsQmRevisionRecordRepo.save(qmsqmrevision).getRevisionRecordId();
				}
				QmsQmRevisionTransaction trans = new QmsQmRevisionTransaction();
				trans.setEmpId(qmsqmrevisionDto.getEmpId());
				trans.setRevisionRecordId(res);
				trans.setStatusCode(qmsqmrevision.getStatusCode());
				trans.setTransactionDate(LocalDateTime.now());
				trans.setRemarks(qmsqmrevisionDto.getRemarks()!=null && !qmsqmrevisionDto.getRemarks().equalsIgnoreCase("") ? qmsqmrevisionDto.getRemarks():"");
				qmsQmRevisionTransactionRepo.save(trans);
				
				//if res>0 then send notificationQM
				if(res>0) {
				ImsNotification notification = new ImsNotification();
				List<LoginDetailsDto> loginDetails = masterService.loginDetailsList(username);
				if (!loginDetails.isEmpty()) {
			        LoginDetailsDto loginDetail = loginDetails.get(0);  // Assuming you want to use the first element

			    
			       
			        notification.setNotificationby(loginDetail.getEmpId());  
			        notification.setNotificationUrl("/quality-manual");
			        notification.setNotificationDate(LocalDateTime.now());
			        notification.setIsActive(1);	
			        notification.setCreatedBy(username);
			        notification.setCreatedDate(LocalDateTime.now());
			        if(qmsqmrevision.getStatusCode().equalsIgnoreCase("FWD")) {
                        //Forwarded notification should go to reviewer
				        notification.setEmpId(qmsqmrevision.getReviewedBy()); 
			            notification.setNotificationMessage("QM Forwarded by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());
                  
			        }else  if(qmsqmrevision.getStatusCode().equalsIgnoreCase("RFD")) {
			            //Re Forwarded notification should go to reviewer
			            notification.setEmpId(qmsqmrevision.getReviewedBy()); 
			        	notification.setNotificationMessage("QM Re-Forwarded by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
			      
			        }else  if(qmsqmrevision.getStatusCode().equalsIgnoreCase("RWD")) {
			        	 //Reviewed notification should go to Approver
			            notification.setEmpId(qmsqmrevision.getApprovedBy()); 
			        	notification.setNotificationMessage("QM Reviewed by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
			      
			        }else  if(qmsqmrevision.getStatusCode().equalsIgnoreCase("APD")) {
			        	 //Approved notification should go to Initiator
			            notification.setEmpId(qmsqmrevision.getInitiatedBy()); 
			        	notification.setNotificationMessage("QM Approved by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
			      
			        }else  if(qmsqmrevision.getStatusCode().equalsIgnoreCase("RTM")) {
			        	 //Return after forward message should go directly to initiator 
			        	notification.setEmpId(qmsqmrevision.getInitiatedBy()); 
				         notification.setNotificationMessage("QM Returned by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
				        
			        }else  if(qmsqmrevision.getStatusCode().equalsIgnoreCase("RTD")) {
			        	//Return after forward message should go directly to initiator 
			        	notification.setEmpId(qmsqmrevision.getInitiatedBy()); 
				         notification.setNotificationMessage("QM Returned by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
				        
			        }
			     
			
			        notificationRepo.save(notification);
			    }
				
				}
			}
			if(res>0) {
				result=1;
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in forwardQm() ", e);
			return null;
		}
	}


	@Override
	public long revokeQmRevision(QmsQmRevisionRecordDto qmsqmrevisionDto, String username) {
		logger.info("Inside revokeQmRevision() ");
		long res=0;
		try {
			if(qmsqmrevisionDto!=null) {
				Long revisionRecordId = qmsqmrevisionDto.getRevisionRecordId();
				QmsQmRevisionRecord qmsqmrevision = qmsQmRevisionRecordRepo.findByRevisionRecordId(revisionRecordId);
				qmsqmrevision.setStatusCode("RVM");
				qmsqmrevision.setStatusCodeNext("RFD");
				res=qmsQmRevisionRecordRepo.save(qmsqmrevision).getRevisionRecordId();
			}
			QmsQmRevisionTransaction trans = new QmsQmRevisionTransaction();
			trans.setEmpId(qmsqmrevisionDto.getEmpId());
			trans.setRevisionRecordId(res);
			trans.setStatusCode("RVM");
			trans.setTransactionDate(LocalDateTime.now());
			trans.setRemarks(qmsqmrevisionDto.getRemarks()!=null && !qmsqmrevisionDto.getRemarks().equalsIgnoreCase("") ? qmsqmrevisionDto.getRemarks():"");
			qmsQmRevisionTransactionRepo.save(trans);
			//if res>0 then send notificationRVM
			if(res>0) {
			ImsNotification notification = new ImsNotification();
			List<LoginDetailsDto> loginDetails = masterService.loginDetailsList(username);
			if (!loginDetails.isEmpty()) {
		        LoginDetailsDto loginDetail = loginDetails.get(0);  
		        notification.setNotificationby(loginDetail.getEmpId());  
		        notification.setNotificationUrl("/quality-manual");
		        notification.setNotificationDate(LocalDateTime.now());
                    //Revoked notification should go to initiator
			    notification.setEmpId(qmsqmrevisionDto.getInitiatedBy()); 
		        notification.setNotificationMessage("QM Revoked by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());
		        notification.setIsActive(1);	
		        notification.setCreatedBy(username);
		        notification.setCreatedDate(LocalDateTime.now());
		    }
		
		        notificationRepo.save(notification);
		    }
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in revokeQmRevision() ", e);
			return res;
		}
	}


	@Override
	public List<QmsQmRevisionTransactionDto> qmsRevisionTran(String revisionRecordId) throws Exception {
		logger.info( " QmsServiceImpl Inside method qmsRevisionTran()");
		try {
			List<Object[]> tranList = qmsQmRevisionRecordRepo.getRevisionTran(revisionRecordId);
			List<EmployeeDto> totalEmployee = masterClient.getEmployeeMasterList(xApiKey);


			Map<Long, EmployeeDto> employeeMap = totalEmployee.stream()
					.filter(employee -> employee.getEmpId() != null)
					.collect(Collectors.toMap(EmployeeDto::getEmpId, employee -> employee));

			List<QmsQmRevisionTransactionDto> revisionTranDtoList = Optional.ofNullable(tranList).orElse(Collections.emptyList()).stream()
					.map(obj -> {
						EmployeeDto employee =	obj[0] != null?employeeMap.get(Long.parseLong(obj[0].toString())):null;

						return QmsQmRevisionTransactionDto.builder()
								.empId(obj[0]!=null?Long.parseLong(obj[0].toString()):0L)
								.statusCode(obj[1]!=null?obj[1].toString():"")
								.transactionDate(obj[2]!=null?obj[2].toString():"")
								.remarks(obj[3]!=null?obj[3].toString():"")
								.status(obj[4]!=null?obj[4].toString():"")
								.empName(employee != null?employee.getEmpName()+", "+employee.getEmpDesigName():"")
								.build();
					})
					.collect(Collectors.toList());
			return revisionTranDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QmsServiceImpl Inside method qmsRevisionTran()"+ e);
			 return Collections.emptyList();
		}
	}

	@Override
	public Integer forwardDwpGwp(DwpRevisionRecordDto dwprevisionDto, String username) throws Exception {
		logger.info("Inside forwardDwpGwp() ");
		try {
			Integer result=0;
			long res=0;
			if(dwprevisionDto!=null) {
				Long revisionRecordId = dwprevisionDto.getRevisionRecordId();
				DwpRevisionRecord dwpqmrevision = dwpRevisionRecordRepo.findByRevisionRecordId(revisionRecordId);
				List<String> forwardstatus = Arrays.asList("INI","RTM","RTG","RVD");
				List<String> reforwardstatus = Arrays.asList("RTM","RTG");
				String RevisionStatusCode = dwpqmrevision.getStatusCode();
				String RevisionStatusCodeNext = dwpqmrevision.getStatusCodeNext();
				String Action = dwprevisionDto.getAction();
				if(Action.equalsIgnoreCase("A")) {
					if(forwardstatus.contains(RevisionStatusCode)) {
						if(RevisionStatusCode.equalsIgnoreCase("INI") || RevisionStatusCode.equalsIgnoreCase("RVD") ) {
							dwpqmrevision.setInitiatedBy(dwprevisionDto.getInitiatedBy());
							dwpqmrevision.setReviewedBy(dwprevisionDto.getReviewedBy());
							dwpqmrevision.setApprovedBy(dwprevisionDto.getApprovedBy());
							dwpqmrevision.setStatusCode("FWD");
							if(dwpqmrevision.getReviewedBy()!=null) {
								dwpqmrevision.setStatusCodeNext("RWD");
							}
						}
						if(reforwardstatus.contains(RevisionStatusCode)) {
							dwpqmrevision.setStatusCode("RFD");
							if(dwpqmrevision.getReviewedBy()!=null) {
								dwpqmrevision.setStatusCodeNext("RWD");
							}
						}
					}else {
						dwpqmrevision.setStatusCode(RevisionStatusCodeNext);
						if(RevisionStatusCodeNext.equalsIgnoreCase("RWD")) {
							if(dwpqmrevision.getApprovedBy()!=null) {
								dwpqmrevision.setStatusCodeNext("APG");
							}

						}
					}
					res=dwpRevisionRecordRepo.save(dwpqmrevision).getRevisionRecordId();
				}else if(Action.equalsIgnoreCase("R")){
					if(RevisionStatusCodeNext.equalsIgnoreCase("RWD"))
					{
						dwpqmrevision.setStatusCode("RTM");
					}
					else if(RevisionStatusCodeNext.equalsIgnoreCase("APG"))
					{
						dwpqmrevision.setStatusCode("RTG");
					}
					res=dwpRevisionRecordRepo.save(dwpqmrevision).getRevisionRecordId();
				}
				DwpTransaction trans = new DwpTransaction();
				trans.setEmpId(dwprevisionDto.getEmpId());
				trans.setRevisionRecordId(res);
				trans.setStatusCode(dwpqmrevision.getStatusCode());
				trans.setTransactionDate(LocalDateTime.now());
				trans.setRemarks(dwprevisionDto.getRemarks()!=null && !dwprevisionDto.getRemarks().equalsIgnoreCase("") ? dwprevisionDto.getRemarks():"");
				dwpTransactionrepo.save(trans);
				
				
				//if res>0 then send notificationDWPGWP
				if(res>0) {
					String docType = dwpqmrevision.getDocType().toUpperCase();

					
				ImsNotification notification = new ImsNotification();
				List<LoginDetailsDto> loginDetails = masterService.loginDetailsList(username);
				if (!loginDetails.isEmpty()) {
			        LoginDetailsDto loginDetail = loginDetails.get(0);  
			        notification.setNotificationby(loginDetail.getEmpId());  
			        notification.setNotificationUrl("/"+docType);
			        notification.setNotificationDate(LocalDateTime.now());
	              	notification.setIsActive(1);	
			        notification.setCreatedBy(username);
			        notification.setCreatedDate(LocalDateTime.now());
			        
			        
			        if(dwpqmrevision.getStatusCode().equalsIgnoreCase("FWD")) {
                        //Forwarded notification should go to reviewer
				        notification.setEmpId(dwpqmrevision.getReviewedBy()); 
			            notification.setNotificationMessage(docType+" Forwarded by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());
                  
			        }else  if(dwpqmrevision.getStatusCode().equalsIgnoreCase("RFD")) {
			            //Re Forwarded notification should go to reviewer
			            notification.setEmpId(dwpqmrevision.getReviewedBy()); 
			        	notification.setNotificationMessage(docType+" Re-Forwarded by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
			      
			        }else  if(dwpqmrevision.getStatusCode().equalsIgnoreCase("RWD")) {
			        	 //Reviewed notification should go to Approver
			            notification.setEmpId(dwpqmrevision.getApprovedBy()); 
			        	notification.setNotificationMessage(docType+" Reviewed by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
			      
			        }else  if(dwpqmrevision.getStatusCode().equalsIgnoreCase("APG")) {
			        	 //Approved notification should go to Initiator
			            notification.setEmpId(dwpqmrevision.getInitiatedBy()); 
			        	notification.setNotificationMessage(docType+" Approved by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
			      
			        }else  if(dwpqmrevision.getStatusCode().equalsIgnoreCase("RTM")) {
			        	 //Return after forward message should go directly to initiator 
			        	notification.setEmpId(dwpqmrevision.getInitiatedBy()); 
				         notification.setNotificationMessage(docType+" Returned by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
				        
			        }else  if(dwpqmrevision.getStatusCode().equalsIgnoreCase("RTG")) {
			        	//Return after forward message should go directly to initiator 
			        	notification.setEmpId(dwpqmrevision.getInitiatedBy()); 
				         notification.setNotificationMessage(docType+" Returned by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
				        
			        }
			        
			        
			        
			    }
			
			        notificationRepo.save(notification);
			    }
				
				
				
				
			}
			if(res>0) {
				result=1;
				
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in forwardDwpGwp() ", e);
			return null;
		}
	}

	@Override
	public List<MRMastersDto> getMrRepList() throws Exception {
		logger.info("Inside getMrRepList() ");
		try {
			List<MRMastersDto> mrMastersDtoList = new ArrayList<>();
			List<Object[]> getMRrepList=mrMastersrepo.getMRrepList("MR Rep",LocalDate.now());

			getMRrepList.forEach(mrRepList -> {
				MRMastersDto dto = new MRMastersDto();
				dto.setEmpId(Long.parseLong(mrRepList[0].toString()));
				dto.setMRType(mrRepList[1].toString());
				if (mrRepList[2] != null) {
					dto.setMRFrom(LocalDate.parse(mrRepList[2].toString()));
				}
				if (mrRepList[3] != null) {
					dto.setMRTo(LocalDate.parse(mrRepList[3].toString()));
				}
				mrMastersDtoList.add(dto);
			});
			return mrMastersDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getMrRepList() ", e);
			return null;
		}
	}

	@Override
	public List<MRMastersDto> getMrList() throws Exception {
		logger.info("Inside getMrList() ");
		try {
			List<MRMastersDto> mrMastersDtoList = new ArrayList<>();
			List<Object[]> getMRList=mrMastersrepo.getMRrepList("MR",LocalDate.now());

			getMRList.forEach(mrRepList -> {
				MRMastersDto dto = new MRMastersDto();
				dto.setEmpId(Long.parseLong(mrRepList[0].toString()));
				dto.setMRType(mrRepList[1].toString());
				if (mrRepList[2] != null) {
					dto.setMRFrom(LocalDate.parse(mrRepList[2].toString()));
				}
				if (mrRepList[3] != null) {
					dto.setMRTo(LocalDate.parse(mrRepList[3].toString()));
				}
				mrMastersDtoList.add(dto);
			});
			return mrMastersDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getMrList() ", e);
			return null;
		}
	}
	
	
	@Override
	public Long addnewdwpgwprevision(DwpRevisionRecordDto dwprevisionRecordDto, String username) throws Exception {
		logger.info( " Inside addnewdwpgwprevision() ");
		long res = 0;
		try {
			DwpRevisionRecord dwpRevisionRecord = new DwpRevisionRecord();
			
			dwpRevisionRecord.setGroupDivisionId(dwprevisionRecordDto.getGroupDivisionId());
			dwpRevisionRecord.setDescription(dwprevisionRecordDto.getDescription());
			dwpRevisionRecord.setIssueNo(dwprevisionRecordDto.getIssueNo());
			dwpRevisionRecord.setRevisionNo(dwprevisionRecordDto.getRevisionNo());
			dwpRevisionRecord.setStatusCode("INI");
			dwpRevisionRecord.setStatusCodeNext("INI");
			dwpRevisionRecord.setDocType(dwprevisionRecordDto.getDocType());
			dwpRevisionRecord.setAbbreviationIdNotReq(dwprevisionRecordDto.getAbbreviationIdNotReq());
			dwpRevisionRecord.setDateOfRevision(LocalDate.now());
			dwpRevisionRecord.setCreatedDate(LocalDateTime.now());
			dwpRevisionRecord.setCreatedBy(username);
			dwpRevisionRecord.setIsActive(1);
			res = dwpRevisionRecordRepo.save(dwpRevisionRecord).getRevisionRecordId();
			return res;
		} catch (Exception e) {
			logger.error(  " Inside addnewdwpgwprevision() "+ e );
			e.printStackTrace();
			return res;
		}
	}
	
	
	@Override
	public Long revokeDwpRevision(DwpRevisionRecordDto dwprevisionRecordDto, String username) throws Exception {
		logger.info("Inside revokeDwpRevision() ");
		long res=0;
		try {
			if(dwprevisionRecordDto!=null) {
			Long revisionRecordId = dwprevisionRecordDto.getRevisionRecordId();
			DwpRevisionRecord dwpgwprevision = dwpRevisionRecordRepo.findByRevisionRecordId(revisionRecordId);
			dwpgwprevision.setStatusCode("RVD");
			dwpgwprevision.setStatusCodeNext("RFD");
			res=dwpRevisionRecordRepo.save(dwpgwprevision).getRevisionRecordId();
			}
			DwpTransaction trans = new DwpTransaction();
			trans.setEmpId(dwprevisionRecordDto.getEmpId());
			trans.setRevisionRecordId(res);
			trans.setStatusCode("RVD");
			trans.setTransactionDate(LocalDateTime.now());
			trans.setRemarks(dwprevisionRecordDto.getRemarks()!=null && !dwprevisionRecordDto.getRemarks().equalsIgnoreCase("") ? dwprevisionRecordDto.getRemarks():"");
			dwpTransactionrepo.save(trans);
			//if res>0 then send notificationRVD
			if(res>0) {
			ImsNotification notification = new ImsNotification();
			List<LoginDetailsDto> loginDetails = masterService.loginDetailsList(username);
			if (!loginDetails.isEmpty()) {
		        LoginDetailsDto loginDetail = loginDetails.get(0);  
		        notification.setNotificationby(loginDetail.getEmpId());  
		        notification.setNotificationUrl("/"+dwprevisionRecordDto.getDocType());
		        notification.setNotificationDate(LocalDateTime.now());
                    //Revoked notification should go to initiator
			    notification.setEmpId(dwprevisionRecordDto.getInitiatedBy()); 
		        notification.setNotificationMessage( dwprevisionRecordDto.getDocType().toUpperCase()+" Revoked by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());
		        notification.setIsActive(1);	
		        notification.setCreatedBy(username);
		        notification.setCreatedDate(LocalDateTime.now());
		    }
		
		        notificationRepo.save(notification);
		    }
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in revokeDwpRevision() ", e);
			return res;
		}
	}
	
	@Override
	public List<DwpTransactionDto> dwpRevisionTran(String revisionRecordId) throws Exception {
		logger.info( " QmsServiceImpl Inside method dwpRevisionTran()");
		try {
			List<Object[]> tranList = dwpRevisionRecordRepo.getDwpRevisionTran(revisionRecordId);
			List<EmployeeDto> totalEmployee = masterClient.getEmployeeMasterList(xApiKey);
			
		    Map<Long, EmployeeDto> employeeMap = totalEmployee.stream()
		            .filter(employee -> employee.getEmpId() != null)
		            .collect(Collectors.toMap(EmployeeDto::getEmpId, employee -> employee));
		    
			 List<DwpTransactionDto> revisionTranDtoList = Optional.ofNullable(tranList).orElse(Collections.emptyList()).stream()
				    .map(obj -> {
					    EmployeeDto employee =	obj[0] != null?employeeMap.get(Long.parseLong(obj[0].toString())):null;

					    	return DwpTransactionDto.builder()
				    			.empId(obj[0]!=null?Long.parseLong(obj[0].toString()):0L)
				    			.statusCode(obj[1]!=null?obj[1].toString():"")
				    			.transactionDate(obj[2]!=null?obj[2].toString():"")
				    			.remarks(obj[3]!=null?obj[3].toString():"")
				    			.status(obj[4]!=null?obj[4].toString():"")
				    			.empName(employee != null?employee.getEmpName()+", "+employee.getEmpDesigName():"")
				    			.build();
				    })
				    .collect(Collectors.toList());
			return revisionTranDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QmsServiceImpl Inside method dwpRevisionTran()"+ e);
			 return Collections.emptyList();
		}
	}
	
	
	@Override
	public List<DivisionEmployeeDto> getDivisionEmployee() throws Exception {
		logger.info( "QmsServiceImpl Inside method getDivisionEmployee()");
		try {
			return masterClient.getDivisionEmpDetailsById(xApiKey);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QmsServiceImpl Inside method getDivisionEmployee()"+ e);
			 return Collections.emptyList();
		}
		
	}
	
	@Override
	public Long updateQmDescription(QmsQmRevisionRecordDto qmsqmrevisionDto, String username) throws Exception {
		logger.info("Inside updateQmDescription() ");
		long res=0;
		try {
			if(qmsqmrevisionDto!=null) {
				Long revisionRecordId = qmsqmrevisionDto.getRevisionRecordId();
				QmsQmRevisionRecord qmrevision = qmsQmRevisionRecordRepo.findByRevisionRecordId(revisionRecordId);
				qmrevision.setDescription(qmsqmrevisionDto.getDescription());
				qmrevision.setModifiedDate(LocalDateTime.now());
				qmrevision.setModifiedBy(username);
				res=qmsQmRevisionRecordRepo.save(qmrevision).getRevisionRecordId();
				}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in updateQmDescription() ", e);
			return res;
		}
	}
	
	
	@Override
	public Long updateDwpGwpDescription(DwpRevisionRecordDto dwpRevisionRecordDto, String username) throws Exception {
		logger.info("Inside updateDwpGwpDescription() ");
		long res=0;
		try {
			if(dwpRevisionRecordDto!=null) {
				Long revisionRecordId = dwpRevisionRecordDto.getRevisionRecordId();
				DwpRevisionRecord dwpgwprevision = dwpRevisionRecordRepo.findByRevisionRecordId(revisionRecordId);
				dwpgwprevision.setDescription(dwpRevisionRecordDto.getDescription());
				dwpgwprevision.setModifiedDate(LocalDateTime.now());
				dwpgwprevision.setModifiedBy(username);
				res=dwpRevisionRecordRepo.save(dwpgwprevision).getRevisionRecordId();
				}
			return res;
		} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in updateDwpGwpDescription() ", e);
				return res;
		}
 	}
	
	@Override
	public Integer forwardQsp(QmsQspRevisionRecordDto qsprevisionDto, String username) throws Exception {
		logger.info("Inside forwardQsp() ");
		try {
			Integer result=0;
			long res=0;
			if(qsprevisionDto!=null) {
				Long revisionRecordId = qsprevisionDto.getRevisionRecordId();
				QmsQspRevisionRecord qmsqsprevision = qspRevisionRecordRepo.findByRevisionRecordId(revisionRecordId);
				List<String> forwardstatus = Arrays.asList("INI","RTM","RTD","RVM");
				List<String> reforwardstatus = Arrays.asList("RTM","RTD");
				String RevisionStatusCode = qmsqsprevision.getStatusCode();
				String RevisionStatusCodeNext = qmsqsprevision.getStatusCodeNext();
				String Action = qsprevisionDto.getAction();
				if(Action.equalsIgnoreCase("A")) {
					if(forwardstatus.contains(RevisionStatusCode)) {
						if(RevisionStatusCode.equalsIgnoreCase("INI") || RevisionStatusCode.equalsIgnoreCase("RVM") ) {
							qmsqsprevision.setInitiatedBy(qsprevisionDto.getInitiatedBy());
							qmsqsprevision.setReviewedBy(qsprevisionDto.getReviewedBy());
							qmsqsprevision.setApprovedBy(qsprevisionDto.getApprovedBy());
							qmsqsprevision.setStatusCode("FWD");
							if(qmsqsprevision.getReviewedBy()!=null) {
								qmsqsprevision.setStatusCodeNext("RWD");
							}
						}
						if(reforwardstatus.contains(RevisionStatusCode)) {
							qmsqsprevision.setStatusCode("RFD");
							if(qmsqsprevision.getReviewedBy()!=null) {
								qmsqsprevision.setStatusCodeNext("RWD");
							}
						}
					}else {
						qmsqsprevision.setStatusCode(RevisionStatusCodeNext);
						if(RevisionStatusCodeNext.equalsIgnoreCase("RWD")) {
							if(qmsqsprevision.getApprovedBy()!=null) {
								qmsqsprevision.setStatusCodeNext("APD");
							}

						}
					}
					res=qspRevisionRecordRepo.save(qmsqsprevision).getRevisionRecordId();
				}else if(Action.equalsIgnoreCase("R")){
					if(RevisionStatusCodeNext.equalsIgnoreCase("RWD"))
					{
						qmsqsprevision.setStatusCode("RTM");
					}
					else if(RevisionStatusCodeNext.equalsIgnoreCase("APD"))
					{
						qmsqsprevision.setStatusCode("RTD");
					}
					res=qspRevisionRecordRepo.save(qmsqsprevision).getRevisionRecordId();
				}
				QmsQspRevisionTransaction trans = new QmsQspRevisionTransaction();
				trans.setEmpId(qsprevisionDto.getEmpId());
				trans.setRevisionRecordId(res);
				trans.setStatusCode(qmsqsprevision.getStatusCode());
				trans.setTransactionDate(LocalDateTime.now());
				trans.setRemarks(qsprevisionDto.getRemarks()!=null && !qsprevisionDto.getRemarks().equalsIgnoreCase("") ? qsprevisionDto.getRemarks():"");
				qmsqsprevisiontransactionrepo.save(trans);
				
				
				//if res>0 then send notificationQSP
				if(res>0) {
				ImsNotification notification = new ImsNotification();
				List<LoginDetailsDto> loginDetails = masterService.loginDetailsList(username);
				if (!loginDetails.isEmpty()) {
			        LoginDetailsDto loginDetail = loginDetails.get(0);  // Assuming you want to use the first element

			        String docName = qmsqsprevision.getDocName();
			        String captalizeDocName = docName.toUpperCase();
			       
			        notification.setNotificationby(loginDetail.getEmpId());  
			        notification.setNotificationUrl("/"+docName);
			        notification.setNotificationDate(LocalDateTime.now());
			        notification.setIsActive(1);	
			        notification.setCreatedBy(username);
			        notification.setCreatedDate(LocalDateTime.now());
			        
			        if(qmsqsprevision.getStatusCode().equalsIgnoreCase("FWD")) {
                        //Forwarded notification should go to reviewer
				        notification.setEmpId(qmsqsprevision.getReviewedBy()); 
			            notification.setNotificationMessage(captalizeDocName+" Forwarded by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());
                  
			        }else  if(qmsqsprevision.getStatusCode().equalsIgnoreCase("RFD")) {
			            //Re Forwarded notification should go to reviewer
			            notification.setEmpId(qmsqsprevision.getReviewedBy()); 
			        	notification.setNotificationMessage(captalizeDocName+" Re-Forwarded by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
			      
			        }else  if(qmsqsprevision.getStatusCode().equalsIgnoreCase("RWD")) {
			        	 //Reviewed notification should go to Approver
			            notification.setEmpId(qmsqsprevision.getApprovedBy()); 
			        	notification.setNotificationMessage(captalizeDocName+" Reviewed by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
			      
			        }else  if(qmsqsprevision.getStatusCode().equalsIgnoreCase("APD")) {
			        	 //Approved notification should go to Initiator
			            notification.setEmpId(qmsqsprevision.getInitiatedBy()); 
			        	notification.setNotificationMessage(captalizeDocName+" Approved by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
			      
			        }else  if(qmsqsprevision.getStatusCode().equalsIgnoreCase("RTM")) {
			        	 //Return after forward message should go directly to initiator 
			        	notification.setEmpId(qmsqsprevision.getInitiatedBy()); 
				         notification.setNotificationMessage(captalizeDocName+" Returned by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	
				        
			        }else  if(qmsqsprevision.getStatusCode().equalsIgnoreCase("RTD")) {
			        	//Return after forward message should go directly to initiator 
			        	notification.setEmpId(qmsqsprevision.getInitiatedBy()); 
				         notification.setNotificationMessage(captalizeDocName+" Returned by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());	 
			        }
			     
			
			        notificationRepo.save(notification);
			    }
				
				}
				
				
				
				
				
				
			}
			if(res>0) {
				result=1;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in forwardQsp() ", e);
			return null;
		}
	}
	
	
	@Override
	public Long revokeQspRevision(QmsQspRevisionRecordDto qsprevisionRecordDto, String username) throws Exception {
		logger.info("Inside revokeQspRevision() ");
		long res=0;
		try {
			if(qsprevisionRecordDto!=null) {
			Long revisionRecordId = qsprevisionRecordDto.getRevisionRecordId();
			QmsQspRevisionRecord qmsqsprevision = qspRevisionRecordRepo.findByRevisionRecordId(revisionRecordId);
			qmsqsprevision.setStatusCode("RVM");
			qmsqsprevision.setStatusCodeNext("RFD");
			res=qspRevisionRecordRepo.save(qmsqsprevision).getRevisionRecordId();
			//if res>0 then send notificationRVM
			if(res>0) {
			ImsNotification notification = new ImsNotification();
			List<LoginDetailsDto> loginDetails = masterService.loginDetailsList(username);
			if (!loginDetails.isEmpty()) {
		        LoginDetailsDto loginDetail = loginDetails.get(0);  
		        notification.setNotificationby(loginDetail.getEmpId());  
		        notification.setNotificationUrl("/"+qmsqsprevision.getDocName());
		        notification.setNotificationDate(LocalDateTime.now());
                    //Revoked notification should go to initiator
			    notification.setEmpId(qmsqsprevision.getInitiatedBy()); 
		        notification.setNotificationMessage(qmsqsprevision.getDocName().toUpperCase()+" Revoked by " + loginDetail.getEmpName()+", "+loginDetail.getEmpDesigCode());
		        notification.setIsActive(1);	
		        notification.setCreatedBy(username);
		        notification.setCreatedDate(LocalDateTime.now());
		    }
		
		        notificationRepo.save(notification);
		    }
			}
			QmsQspRevisionTransaction trans = new QmsQspRevisionTransaction();
			trans.setEmpId(qsprevisionRecordDto.getEmpId());
			trans.setRevisionRecordId(res);
			trans.setStatusCode("RVM");
			trans.setTransactionDate(LocalDateTime.now());
			trans.setRemarks(qsprevisionRecordDto.getRemarks()!=null && !qsprevisionRecordDto.getRemarks().equalsIgnoreCase("") ? qsprevisionRecordDto.getRemarks():"");
			qmsqsprevisiontransactionrepo.save(trans);
			

			
			
			return res;
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in revokeQspRevision() ", e);
			return res;
		}
	}
	
	@Override
	public List<QmsQspRevisionTransactionDto> qspRevisionTran(String revisionRecordId) throws Exception {
		logger.info( " QmsServiceImpl Inside method qspRevisionTran()");
		try {
			List<Object[]> tranList = qspRevisionRecordRepo.getQspRevisionTran(revisionRecordId);
			List<EmployeeDto> totalEmployee = masterClient.getEmployeeMasterList(xApiKey);

			Map<Long, EmployeeDto> employeeMap = totalEmployee.stream()
					.filter(employee -> employee.getEmpId() != null)
					.collect(Collectors.toMap(EmployeeDto::getEmpId, employee -> employee));

			List<QmsQspRevisionTransactionDto> revisionTranDtoList = Optional.ofNullable(tranList).orElse(Collections.emptyList()).stream()
					.map(obj -> {
						EmployeeDto employee =	obj[0] != null?employeeMap.get(Long.parseLong(obj[0].toString())):null;

						return QmsQspRevisionTransactionDto.builder()
								.empId(obj[0]!=null?Long.parseLong(obj[0].toString()):0L)
								.statusCode(obj[1]!=null?obj[1].toString():"")
								.transactionDate(obj[2]!=null?obj[2].toString():"")
								.remarks(obj[3]!=null?obj[3].toString():"")
								.status(obj[4]!=null?obj[4].toString():"")
								.empName(employee != null?employee.getEmpName()+", "+employee.getEmpDesigName():"")
								.build();
					})
					.collect(Collectors.toList());
			return revisionTranDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QmsServiceImpl Inside method qspRevisionTran()"+ e);
			 return Collections.emptyList();
		}
	}
	
	
	@Override
	public Long addNewQspRevision(QmsQspRevisionRecordDto qmsQspRevisionRecordDto, String username) throws Exception {
		logger.info( " Inside addNewQspRevision() ");
		long res = 0;
		try {
			QmsQspRevisionRecord qmsQspRevisionRecord = new QmsQspRevisionRecord();
			qmsQspRevisionRecord.setDescription(qmsQspRevisionRecordDto.getDescription());
			qmsQspRevisionRecord.setIssueNo(qmsQspRevisionRecordDto.getIssueNo());
			qmsQspRevisionRecord.setRevisionNo(qmsQspRevisionRecordDto.getRevisionNo());
			qmsQspRevisionRecord.setDocName(qmsQspRevisionRecordDto.getDocName());
			qmsQspRevisionRecord.setStatusCode("INI");
			qmsQspRevisionRecord.setStatusCodeNext("INI");
			qmsQspRevisionRecord.setAbbreviationIdNotReq(qmsQspRevisionRecordDto.getAbbreviationIdNotReq());
			qmsQspRevisionRecord.setDateOfRevision(LocalDate.now());
			qmsQspRevisionRecord.setCreatedDate(LocalDateTime.now());
			qmsQspRevisionRecord.setCreatedBy(username);
			qmsQspRevisionRecord.setIsActive(1);
			res = qspRevisionRecordRepo.save(qmsQspRevisionRecord).getRevisionRecordId();
			return res;
		} catch (Exception e) {
			logger.error(  " Inside addNewQspRevision() "+ e );
			e.printStackTrace();
			return res;
		}
	}
	
	
	@Override
	public Long updateQspDescription(QmsQspRevisionRecordDto qsprevisionDto, String username) throws Exception {
		logger.info("Inside updateQspDescription() ");
		long res=0;
		try {
			if(qsprevisionDto!=null) {
				Long revisionRecordId = qsprevisionDto.getRevisionRecordId();
				QmsQspRevisionRecord qsprevision = qspRevisionRecordRepo.findByRevisionRecordId(revisionRecordId);
				qsprevision.setDescription(qsprevisionDto.getDescription());
				qsprevision.setModifiedDate(LocalDateTime.now());
				qsprevision.setModifiedBy(username);
				res=qspRevisionRecordRepo.save(qsprevision).getRevisionRecordId();
				}
			return res;
		} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in updateQspDescription() ", e);
				return res;
		}
	}
//	@Override
//	public List<DwpRevisionRecordDto> getDwpVersionRecordDtoList(Long divisionId) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}


}
