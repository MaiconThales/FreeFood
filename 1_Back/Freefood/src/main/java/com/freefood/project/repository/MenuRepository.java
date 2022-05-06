package com.freefood.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.freefood.project.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	@Query(value = "SELECT * FROM menu WHERE id_restaurant = :idRestaurant", nativeQuery = true)
	List<Menu> getMenuByRestaurant(@Param("idRestaurant") Long idRestaurant);

	@Query(value = "SELECT m.* FROM menu m LEFT JOIN restaurant r ON r.id=m.id_restaurant LEFT JOIN restaurant_user ru ON ru.restaurant_id=r.id WHERE ru.user_id = :idUser", nativeQuery = true)
	List<Menu> getMenuByUser(@Param("idUser") Long idUser);

}
