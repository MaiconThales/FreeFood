package com.freefood.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.freefood.project.dto.MenuDTO;
import com.freefood.project.model.Menu;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.repository.MenuRepository;
import com.freefood.project.service.MenuService;
import com.freefood.project.service.RestaurantService;

@Service
public class MenuServiceImpl implements MenuService {
	
	private static final String SERVER_ERROR = "GLOBAL_WORD.WORD_MSG_SERVER_ERROR";

	private final MenuRepository menuRepository;
	private final ModelMapper modelMapper;
	private final RestaurantService restaurantService;

	@Autowired
	public MenuServiceImpl(MenuRepository menuRepository, ModelMapper modelMapper, RestaurantService restaurantService) {
		this.menuRepository = menuRepository;
		this.modelMapper = modelMapper;
		this.restaurantService = restaurantService;
	}

	@Override
	public ResponseEntity<MessageResponse> saveMenu(MenuDTO menu) {
		MenuDTO resultDto = null;
		try {
			Menu result = this.menuRepository.save(modelMapper.map(menu, Menu.class));
			resultDto = modelMapper.map(result, MenuDTO.class);
			
			if(resultDto != null) {
				return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_CREATE"), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<MessageResponse> updateMenu(MenuDTO menu) {
		MenuDTO resultDto = null;
		try {
			resultDto = modelMapper.map(this.menuRepository.save(modelMapper.map(menu, Menu.class)), MenuDTO.class);
			if(resultDto != null) {
				return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_EDIT"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<MessageResponse> deleteMenu(Long idMenu, Long idUser, Long idRestaurant) {
		try {
			if(this.restaurantService.verifyAccessRestaurant(idUser, idRestaurant)) {
				this.menuRepository.deleteById(idMenu);
				return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_REMOVE"), HttpStatus.OK);
			}
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_FORBIDDEN"), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<List<MenuDTO>> getMenu(Long idRestaurant, Long idUser) {
		List<MenuDTO> resultDto = null;
		try {
			List<Menu> menu;
			if(idRestaurant.equals(0L)) {
				//Not filter restaurant
				menu = this.menuRepository.getMenuByUser(idUser);
			} else {
				//With filter restaurant
				menu = this.menuRepository.getMenuByRestaurant(idRestaurant);
			}
			
			if(!menu.isEmpty()) {
				resultDto = menu.stream().map(m -> modelMapper.map(m, MenuDTO.class)).collect(Collectors.toList());
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(resultDto, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<List<MenuDTO>> getAllMenu() {
		List<MenuDTO> resultFinal = null;
		try {
			List<Menu> resultBank = this.menuRepository.findAll();
			if(!resultBank.isEmpty()) {
				resultFinal = resultBank.stream().map(m -> modelMapper.map(m, MenuDTO.class)).collect(Collectors.toList());
				return new ResponseEntity<>(resultFinal, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(resultFinal, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(resultFinal, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Override
	public ResponseEntity<List<MenuDTO>> getMenuByRestaurant(Long idRestaurant) {
		List<MenuDTO> resultDto = null;
		try {
			List<Menu> menu = this.menuRepository.getMenuByRestaurant(idRestaurant);
			
			if(!menu.isEmpty()) {
				resultDto = menu.stream().map(m -> modelMapper.map(m, MenuDTO.class)).collect(Collectors.toList());
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(resultDto, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
