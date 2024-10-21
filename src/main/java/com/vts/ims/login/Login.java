package com.vts.ims.login;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "login")
public class Login {
	
    private Long LoginId;
    private String Username;
    private String Password;
    private Long EmpId;
    private Long DivisionId;
    private Long QmsFormRoleId;
    private String LoginType;
    private int IsActive;
    private String CreatedBy;
    private String CreatedDate;
    private String ModifiedBy;
    private String ModifiedDate;
    private Set<Role> roles;
 
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    
    public Long getLoginId() {
		return LoginId;
	}
	public void setLoginId(Long loginId) {
		LoginId = loginId;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		this.Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	public Long getEmpId() {
		return EmpId;
	}
	public void setEmpId(Long empId) {
		EmpId = empId;
	}
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "login_role_security", joinColumns = @JoinColumn(name = "LoginId"), inverseJoinColumns = @JoinColumn(name = "RoleId"))
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Long getDivisionId() {
		return DivisionId;
	}
	public void setDivisionId(Long divisionId) {
		DivisionId = divisionId;
	}
	
	public String getLoginType() {
		return LoginType;
	}
	public Long getQmsFormRoleId() {
		return QmsFormRoleId;
	}
	public void setQmsFormRoleId(Long qmsFormRoleId) {
		QmsFormRoleId = qmsFormRoleId;
	}
	public void setLoginType(String loginType) {
		LoginType = loginType;
	}
	public int getIsActive() {
		return IsActive;
	}
	public void setIsActive(int isActive) {
		IsActive = isActive;
	}
	
	public String getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}
	
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	public String getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public String getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}
    
	
    
   
}
