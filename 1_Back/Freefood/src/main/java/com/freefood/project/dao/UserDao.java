package com.freefood.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freefood.project.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
}
