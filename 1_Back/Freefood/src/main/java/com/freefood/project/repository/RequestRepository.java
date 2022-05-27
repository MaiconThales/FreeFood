package com.freefood.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freefood.project.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
	
	@Query(value = "SELECT r.* FROM request r"
			+ " LEFT JOIN address a ON a.id = r.id_address"
			+ " WHERE a.id_user = :idUser", nativeQuery = true)
	List<Request> getRequestByUser(@Param("idUser") Long idUser);

}
