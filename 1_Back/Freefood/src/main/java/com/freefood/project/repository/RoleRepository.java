package com.freefood.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freefood.project.model.ERole;
import com.freefood.project.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(ERole name);

}
