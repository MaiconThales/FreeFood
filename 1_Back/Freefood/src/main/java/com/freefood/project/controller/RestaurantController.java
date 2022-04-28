package com.freefood.project.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freefood.project.dto.RestaurantDto;
import com.freefood.project.model.Restaurant;
import com.freefood.project.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/all")
	public ResponseEntity<List<RestaurantDto>> getAll() {
		List<RestaurantDto> result = null;
		try {
			result = this.restaurantService.findAll().stream().map(r -> modelMapper.map(r, RestaurantDto.class)).collect(Collectors.toList());
			
			if(result.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findId")
	public ResponseEntity<RestaurantDto> getFindById(@RequestParam Long idRestaurant) {
		RestaurantDto resultDto = null;
		try {
			Restaurant result = this.restaurantService.findById(idRestaurant);
			resultDto = modelMapper.map(result, RestaurantDto.class);
			
			if(resultDto != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/createRestaurant")
	public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurant) {
		RestaurantDto result = null;
		try {
			Restaurant param = this.restaurantService.saveRestaurant(modelMapper.map(restaurant, Restaurant.class));
			result = modelMapper.map(param, RestaurantDto.class);
			
			if(result != null) {
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateRestaurant")
	public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody RestaurantDto restaurant) {
		RestaurantDto resultDto = null;
		try {
			Restaurant paramRestaurant = modelMapper.map(restaurant, Restaurant.class);
			resultDto = modelMapper.map(this.restaurantService.updateRestaurant(paramRestaurant), RestaurantDto.class);
			return new ResponseEntity<>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteRestaurant/{idRestaurant}")
	public ResponseEntity<RestaurantDto> deleteRestaurant(@PathVariable("idRestaurant") long idRestaurant) {
		try {
			this.restaurantService.deleteRestaurant(idRestaurant);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
