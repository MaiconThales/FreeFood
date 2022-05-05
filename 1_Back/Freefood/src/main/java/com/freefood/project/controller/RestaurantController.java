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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freefood.project.dto.RestaurantDTO;
import com.freefood.project.model.Restaurant;
import com.freefood.project.model.User;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.service.RestaurantService;
import com.freefood.project.service.UserService;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/findId")
	public ResponseEntity<RestaurantDTO> getFindById(@RequestParam Long idRestaurant) {
		RestaurantDTO resultDto = null;
		try {
			Restaurant result = this.restaurantService.findById(idRestaurant);
			resultDto = modelMapper.map(result, RestaurantDTO.class);
			
			if(resultDto != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getRestaurant")
	public ResponseEntity<List<RestaurantDTO>> findRestaurantByUserId(@RequestParam Long idUser) {
		List<RestaurantDTO> resultDto = null;
		try {
			List<Restaurant> result = this.restaurantService.findRestaurantByUserId(idUser);
			resultDto = result.stream().map(r -> modelMapper.map(r, RestaurantDTO.class)).collect(Collectors.toList());
			
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
	public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurant) {
		RestaurantDTO result = null;
		try {
			Restaurant param = this.restaurantService.saveRestaurant(modelMapper.map(restaurant, Restaurant.class));
			result = modelMapper.map(param, RestaurantDTO.class);
			
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
	public ResponseEntity<RestaurantDTO> updateRestaurant(@RequestBody RestaurantDTO restaurant, @RequestParam("idUser") Long idUser) {
		RestaurantDTO resultDto = null;
		try {
			if(this.restaurantService.verifyAccessRestaurnt(idUser, restaurant.getId())) {
				Restaurant paramRestaurant = modelMapper.map(restaurant, Restaurant.class);
				resultDto = modelMapper.map(this.restaurantService.updateRestaurant(paramRestaurant), RestaurantDTO.class);
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteRestaurant")
	public ResponseEntity<RestaurantDTO> deleteRestaurant(@RequestParam("idRestaurant") Long idRestaurant, @RequestParam("idUser") Long idUser) {
		try {
			if(this.restaurantService.verifyAccessRestaurnt(idUser, idRestaurant)) {
				this.restaurantService.deleteRestaurant(idRestaurant);
				return new ResponseEntity<>(HttpStatus.OK); 
			}
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/liberateRestaurant")
	public ResponseEntity<MessageResponse> liberateRestaurant(@RequestBody RestaurantDTO restaurant, @RequestParam("username") String username, @RequestParam("idUser") Long idUser) {
		try {
			if(this.restaurantService.verifyAccessRestaurnt(idUser, restaurant.getId())) {
				User u = this.userService.findByUsername(username); 
				if(u != null) {
					Restaurant paramRestaurant = modelMapper.map(restaurant, Restaurant.class);
					paramRestaurant.getUsers().add(u);
					this.restaurantService.updateRestaurant(paramRestaurant);
					return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_LIBERATE_SUCCESS"), HttpStatus.OK);
				}
				return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_NO_USER"), HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_FORBIDDEN"), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
