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
@Entity
@Table(name="ims_form_role_access")
@Data
public class FormRoleAccess implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FormRoleAccessId")
	private Long formRoleAccessId ;

	@Column(name = "ImsFormRoleId")
	private Long imsFormRoleId ;

	@Column(name = "FormDetailId")
	private Long formDetailId ;

	@Column(name = "IsActive")
	private int isActive ;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedDate")
	private LocalDateTime createdDate;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "ModifiedDate")
	private LocalDateTime modifiedDate;

}
