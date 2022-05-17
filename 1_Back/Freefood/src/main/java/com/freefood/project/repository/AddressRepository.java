package com.freefood.project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freefood.project.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	@Query(value = "SELECT * FROM address WHERE id_user = :idUser", nativeQuery = true)
	List<Address> addressByUser(@Param("idUser") Long idUser);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE address SET is_default = false WHERE id_user = :idUser", nativeQuery = true)
	void updateIsDefault(@Param("idUser") Long idUser);

}
