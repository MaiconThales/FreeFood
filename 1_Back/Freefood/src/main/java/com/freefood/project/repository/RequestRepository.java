package com.freefood.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freefood.project.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

}
