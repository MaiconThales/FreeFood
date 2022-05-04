package com.freefood.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freefood.project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	@Query(value = "SELECT language FROM user WHERE id = :idUser", nativeQuery=true)
	String getLanguageUser(@Param("idUser") Long idUser);
	
	@Query(value = "SELECT password FROM user WHERE id = :idUser", nativeQuery=true)
	String getPasswordByUserId(@Param("idUser") Long idUser);

}
