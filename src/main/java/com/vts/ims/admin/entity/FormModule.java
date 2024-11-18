package com.vts.ims.admin.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name="ims_form_module")
@Data
public class FormModule implements Serializable {
	
	
	@Id
	@Column(name = "FormModuleId" )
	private Long formModuleId ;
	
	@Column(name = "FormModuleName" )
	private String formModuleName ;
	
	@Column(name = "ModuleUrl" )
	private String moduleUrl ;
	
	@Column(name = "ModuleIcon" )
	private String moduleIcon ;
	
	@Column(name = "SerialNo" )
	private Long serialNo ;
	
	@Column(name = "IsActive" )
	private int isActive ;
	
	
}
