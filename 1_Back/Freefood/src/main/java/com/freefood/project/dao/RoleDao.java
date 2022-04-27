package com.freefood.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freefood.project.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

	Role findRoleByName(String name);
	
}
