package com.vts.ims.login;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "role_security")
public class Role {
	
	 private Long RoleId;
	 private String RoleName;
 private Set<Login> Login;
	
	public String getRoleName() {
		return RoleName;
	}
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
	@ManyToMany(mappedBy = "roles")
	public Set<Login> getLogin() {
		return Login;
	}
	public void setLogin(Set<Login> login) {
		Login = login;
	}
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getRoleId() {
		return RoleId;
	}
	public void setRoleId(Long roleId) {
		RoleId = roleId;
	}

   
    
}
