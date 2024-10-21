package com.vts.ims.login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role  findByRoleId(Long roleId);
}
