package com.freefood.project.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.freefood.project.dto.RestaurantDTO;
import com.freefood.project.model.Restaurant;
import com.freefood.project.model.User;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.repository.RestaurantRepository;
import com.freefood.project.service.RestaurantService;
import com.freefood.project.service.UserService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private static final String SERVER_ERROR = "GLOBAL_WORD.WORD_MSG_SERVER_ERROR";

	private RestaurantRepository restaurantRepository;
	private ModelMapper modelMapper;
	private UserService userService;

	@Autowired
	public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ModelMapper modelMapper,
			UserService userService) {
		this.restaurantRepository = restaurantRepository;
		this.modelMapper = modelMapper;
		this.userService = userService;
	}

	@Override
	public ResponseEntity<RestaurantDTO> findById(Long idRestaurant) {
		RestaurantDTO resultDto = null;
		try {
			Restaurant result = this.optionalToObject(this.restaurantRepository.findById(idRestaurant));

			if (result != null) {
				resultDto = modelMapper.map(result, RestaurantDTO.class);
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<MessageResponse> saveRestaurant(RestaurantDTO restaurant) {
		RestaurantDTO result = null;
		try {
			Restaurant param = this.restaurantRepository.save(modelMapper.map(restaurant, Restaurant.class));
			result = modelMapper.map(param, RestaurantDTO.class);

			if (result != null) {
				return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_CREATE"), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<MessageResponse> updateRestaurant(RestaurantDTO restaurant, Long idUser) {
		try {
			if (this.verifyAccessRestaurant(idUser, restaurant.getId())) {
				Restaurant paramRestaurant = modelMapper.map(restaurant, Restaurant.class);
				this.restaurantRepository.save(paramRestaurant);
				return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_EDIT"), HttpStatus.OK);
			}
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<MessageResponse> deleteRestaurant(Long idRestaurant, Long idUser) {
		try {
			if (this.verifyAccessRestaurant(idUser, idRestaurant)) {
				this.restaurantRepository.deleteById(idRestaurant);
				return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_REMOVE"), HttpStatus.OK);
			}
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_FORBIDDEN"), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<List<RestaurantDTO>> findRestaurantByUserId(Long idUser) {
		List<RestaurantDTO> resultDto = null;
		try {
			List<Restaurant> result = this.restaurantRepository.findRestaurantByUserId(idUser);
			resultDto = result.stream().map(r -> modelMapper.map(r, RestaurantDTO.class)).collect(Collectors.toList());

			if (resultDto != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public boolean verifyAccessRestaurant(Long idUser, Long idRestaurant) {
		List<Long> accessRestaurant = this.restaurantRepository.returnIdRestaurantByUserId(idUser);
		Long result = accessRestaurant.stream().filter(a -> a.equals(idRestaurant)).findAny().orElse(null);
		return result != null;
	}

	private Restaurant optionalToObject(Optional<Restaurant> result) {
		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public ResponseEntity<MessageResponse> liberateRestaurant(RestaurantDTO restaurant, String username, Long idUser) {
		try {
			if (this.verifyAccessRestaurant(idUser, restaurant.getId())) {
				User u = this.userService.findByUsername(username);
				if (u != null) {
					Restaurant paramRestaurant = modelMapper.map(restaurant, Restaurant.class);
					paramRestaurant.getUsers().add(u);
					this.restaurantRepository.save(paramRestaurant);
					return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_LIBERATE_SUCCESS"),
							HttpStatus.OK);
				}
				return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_NO_USER"), HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_FORBIDDEN"), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Override
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurant() {
		List<RestaurantDTO> resultDto = null;
		try {
			List<Restaurant> result = this.restaurantRepository.findAll();
			resultDto = result.stream().map(r -> modelMapper.map(r, RestaurantDTO.class)).collect(Collectors.toList());

			if (resultDto != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
