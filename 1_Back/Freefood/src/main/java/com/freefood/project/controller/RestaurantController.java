package com.freefood.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freefood.project.dto.RestaurantDTO;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantController {
	
	private final RestaurantService restaurantService;
	
	@Autowired
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}
	
	@GetMapping("/findId")
	public ResponseEntity<RestaurantDTO> getFindById(@RequestParam Long idRestaurant) {
		return this.restaurantService.findById(idRestaurant);
	}
	
	@GetMapping("/getRestaurant")
	public ResponseEntity<List<RestaurantDTO>> findRestaurantByUserId(@RequestParam Long idUser) {
		return this.restaurantService.findRestaurantByUserId(idUser);
	}
	
	@PostMapping("/createRestaurant")
	public ResponseEntity<MessageResponse> createRestaurant(@RequestBody RestaurantDTO restaurant) {
		return this.restaurantService.saveRestaurant(restaurant);
	}
	
	@PutMapping("/updateRestaurant")
	public ResponseEntity<MessageResponse> updateRestaurant(@RequestBody RestaurantDTO restaurant, @RequestParam("idUser") Long idUser) {
		return this.restaurantService.updateRestaurant(restaurant, idUser);
	}
	
	@DeleteMapping("/deleteRestaurant")
	public ResponseEntity<MessageResponse> deleteRestaurant(@RequestParam("idRestaurant") Long idRestaurant, @RequestParam("idUser") Long idUser) {
		return this.restaurantService.deleteRestaurant(idRestaurant, idUser);
	}
	
	@PutMapping("/liberateRestaurant")
	public ResponseEntity<MessageResponse> liberateRestaurant(@RequestBody RestaurantDTO restaurant, @RequestParam("username") String username, @RequestParam("idUser") Long idUser) {
		return this.restaurantService.liberateRestaurant(restaurant, username, idUser);
	}
	
	@GetMapping("/getAllRestaurant")
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurant() {
		return this.restaurantService.getAllRestaurant();
	}
	
}
