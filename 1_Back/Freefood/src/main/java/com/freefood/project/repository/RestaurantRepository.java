package com.freefood.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.freefood.project.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	@Transactional(readOnly = true)
	Restaurant findById(String idRestaurant);

	@Query(value = "SELECT r.* FROM restaurant r LEFT JOIN restaurant_user ru on ru.restaurant_id = r.id"
			+ " WHERE ru.user_id = :idUser", nativeQuery = true)
	List<Restaurant> findRestaurantByUserId(@Param("idUser") Long idUser);

	@Query(value = "SELECT r.id FROM restaurant r LEFT JOIN restaurant_user ru on ru.restaurant_id = r.id"
			+ " WHERE ru.user_id = :idUser", nativeQuery = true)
	List<Long> returnIdRestaurantByUserId(@Param("idUser") Long idUser);

}
