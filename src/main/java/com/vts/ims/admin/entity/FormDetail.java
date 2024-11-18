package com.vts.ims.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name="ims_form_detail")
public class FormDetail implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)


	@Column(name = "FormDetailId")
	private Long formDetailId ;
	
	@Column(name = "FormModuleId")
	private Long formModuleId ;
	
	@Column(name = "FormName")
	private String formName;

    @Column(name = "FormUrl")
    private String formUrl;
    
	@Column(name = "FormDispName")
	private String formDispName;
	
	@Column(name = "FormSerialNo")
	private Long formSerialNo ;
	
	@Column(name = "FormColor", length = 50)
	private String formColor;
	
	@Column(name = "IsActive", length = 1)
	private int isActive ;

    @Column(name = "ModifiedBy")
    private String modifiedBy;

    @Column(name = "ModifiedDate")
    private LocalDateTime modifiedDate;
	
	
	
}
