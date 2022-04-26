package com.freefood.project.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freefood.project.model.Restaurant;
import com.freefood.project.repository.RestaurantRepository;
import com.freefood.project.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public Optional<Restaurant> findById(Long idRestaurant) {
		return this.restaurantRepository.findById(idRestaurant);
	}

	@Override
	public List<Restaurant> findAll() {
		return this.restaurantRepository.findAll();
	}

	@Override
	public Restaurant saveRestaurant(Restaurant restaurant) {
		return this.restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Restaurant restaurant) {
		return this.restaurantRepository.save(restaurant);
	}

	@Override
	public void deleteRestaurant(Long idRestaurant) {
		this.restaurantRepository.deleteById(idRestaurant);
	}

}
