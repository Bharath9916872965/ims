package com.vts.ims.qms.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vts.ims.qms.dto.QmsQmChaptersDto;
import com.vts.ims.qms.dto.QmsQmDocumentSummaryDto;
import com.vts.ims.qms.dto.QmsQmRevisionRecordDto;
import com.vts.ims.qms.dto.QmsQmSectionsDto;
import com.vts.ims.qms.service.QmsService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin("*")
@RestController
public class QmsController {

	private static final Logger logger = LogManager.getLogger(QmsController.class);
	
	
	
	@Value("${appStorage}")
	private String storageDrive ;
	
	@Autowired
	QmsService service;
	
	
	@PostMapping(value = "/get-qm-version-record-list", produces = "application/json")
	public List<QmsQmRevisionRecordDto> getQmVersionRecordDtoList(@RequestHeader  String username) throws Exception {
		logger.info(new Date() + " Inside get-qm-version-record-list " + username);
		return service.getQmVersionRecordDtoList();
	}
	
	@PostMapping(value = "/get-all-qm-chapters", produces = "application/json")
	public List<QmsQmChaptersDto> getAllQMChapters(@RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside get-all-qm-chapters " + username);
		return service.getAllQMChapters();
	}
	
	@PostMapping(value = "/un-added-qm-section-list", produces = "application/json")
	public List<QmsQmSectionsDto> unAddedQmSectionList(@RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside un-added-qm-section-list " + username);
		return service.getUnAddedQmSectionList();
	}
	
	
	@PostMapping(value = "/add-new-qm-section", produces = "application/json")
	public Long addNewQmSection(@RequestBody String sectionName, @RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside add-new-qm-section " + username);
		return service.addNewQmSection(sectionName, username);
	}
	
	@PostMapping(value = "/qm-unaddlist-to-addlist", produces = "application/json")
	public Long qmUnAddListToAddList(@RequestBody long[] selectedSections, @RequestHeader  String username) throws Exception {
		logger.info(new Date() + " Inside qm-unaddlist-to-addlist " + username);
		return service.qmUnAddListToAddList(selectedSections, username);
	}
	
	@PostMapping(value = "/get-qm-subchapters", produces = "application/json")
	public List<QmsQmChaptersDto> getQmSubChaptersById(@RequestBody Long chapterId, @RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside get-qm-subchapters-by-id " + username);
		return service.getQmSubChaptersById(chapterId);
	}
	
	@PostMapping(value = "/add-qm-new-subchapter/{chapterId}", produces = "application/json")
	public Long addQmNewSubChapter(@PathVariable("chapterId") Long chapterId, @RequestBody String chapterName, @RequestHeader  String username) throws Exception {
		logger.info(new Date() + " Inside add-qm-new-subchapter " + username);
		return service.addQmNewSubChapter(chapterId, chapterName, username);
	}
	
	@PostMapping(value = "/update-qm-chaptercontent/{chapterId}", produces = "application/json")
	public Long updateQmChapterContent(@PathVariable("chapterId") Long chapterId, @RequestBody String chapterContent, @RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside update-qm-chaptercontent " + username);
		return service.updateQmChapterContent(chapterId, chapterContent, username);
	}
	
	@PostMapping(value = "/update-qm-chaptername/{chapterId}", produces = "application/json")
	public Long updateQmChapterNameById(@PathVariable("chapterId") Long chapterId, @RequestBody String chapterName, @RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside update-qm-chaptername " + username);
		return service.updateQmChapterName(chapterId, chapterName, username);
	}
	
	@PostMapping(value = "/add-new-qm-revision", produces = "application/json")
	public Long addNewQmRevision(@RequestBody QmsQmRevisionRecordDto qmsQmRevisionRecordDto, @RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside add-new-qm-revision " + username);
		return service.addNewQmRevision(qmsQmRevisionRecordDto, username);
	}
	
	@GetMapping("/download-qm-document")
	public ResponseEntity<Resource> downloadQmDocument(@RequestParam String fileName, HttpServletResponse res, @RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside download-qm-document " + username);
	    File file = new File(storageDrive + fileName);

	    if (!file.exists() || !file.isFile()) {
	        throw new FileNotFoundException("File not found: " + fileName);
	    }

	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    headers.add("Pragma", "no-cache");
	    headers.add("Expires", "0");

	    Path path = file.toPath();
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(file.length())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(resource);
	    
	}
	
	@GetMapping("/get-qm-moc-excel")
    public ResponseEntity<FileSystemResource> downloadExcel(@RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside get-qm-moc-excel " + username);
		String fileName = "QMS"+File.separator+"QM_Defaults"+File.separator+"Mapping_of_Clauses.xlsx";
        String filePath = storageDrive+fileName;
        
        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("File not found: " + filePath);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("filename", file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(file));
    }
	
	
	@PostMapping(value = "/add-docSummary", produces = "application/json")
	public long addQmDocSummary(@RequestBody QmsQmDocumentSummaryDto qmsQmDocumentSummaryDto, @RequestHeader String username) throws Exception {
		logger.info(new Date() + " Inside add-docSummary " + username);
		
		return service.addQmDocSummary(qmsQmDocumentSummaryDto, username);
	}

	@PostMapping(value = "/get-docsummary", produces = "application/json")
	public QmsQmDocumentSummaryDto getQmDocSummarybyId(@RequestBody long DocumentSummaryId, @RequestHeader  String username) throws Exception {
		logger.info(new Date() + " Inside get-docsummary " + username);
		return service.getQmDocSummarybyId(DocumentSummaryId);
	}
	
	
}
