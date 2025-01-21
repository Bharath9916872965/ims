package com.vts.ims.qms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class QmsIssueDto {

	private String NewAmendVersion;
	private String AmendParticulars;
	private String DocType;
	private long GroupDivisionId;
	private List<DynamicField> dynamicFields; 
    private String isExisting;  
    
	
    
    public static class DynamicField {
        private String currentVersion;   
        private String amendParticulars;
        private String selExistingVal;   
        private LocalDate revisionDate;
        // Getters and Setters
        public String getCurrentVersion() {
            return currentVersion;
        }

        public void setCurrentVersion(String currentVersion) {
            this.currentVersion = currentVersion;
        }

        public String getAmendParticulars() {
            return amendParticulars;
        }

        public void setAmendParticulars(String amendParticulars) {
            this.amendParticulars = amendParticulars;
        }

        public String getSelExistingVal() {
            return selExistingVal;
        }

        public void setSelExistingVal(String selOriginalVal) {
            this.selExistingVal = selOriginalVal;
        }

		public LocalDate getRevisionDate() {
			return revisionDate;
		}

		public void setRevisionDate(LocalDate revisionDate) {
			this.revisionDate = revisionDate;
		}
    }
}
