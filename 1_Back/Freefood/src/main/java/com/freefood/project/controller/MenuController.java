package com.freefood.project.controller;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.freefood.project.dto.MenuDTO;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.service.MenuService;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
public class MenuController {
	
	private final MenuService menuService;
	
	@Autowired
	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	@GetMapping("/getMenu")
	public ResponseEntity<List<MenuDTO>> getMenu(@RequestParam("idRestaurant") Long idRestaurant, @RequestParam("idUser") Long idUser) {
		return this.menuService.getMenu(idRestaurant, idUser);
	}
	
	@PostMapping("/createMenu")
	public ResponseEntity<MessageResponse> createMenu(@RequestBody MenuDTO menu) {
		return this.menuService.saveMenu(menu);
	}
	
	@PutMapping("/updateMenu")
	public ResponseEntity<MessageResponse> updateMenu(@RequestBody MenuDTO menu) {
		return this.menuService.updateMenu(menu);
	}
	
	@DeleteMapping("/deleteMenu")
	public ResponseEntity<MessageResponse> deleteMenu(@RequestParam("idMenu") Long idMenu, @RequestParam("idUser") Long idUser,  @RequestParam("idRestaurant") Long idRestaurant) {
		return this.menuService.deleteMenu(idMenu, idUser, idRestaurant);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<MenuDTO>> getAllMenu() {
		return this.menuService.getAllMenu();
	}

	@GetMapping("/getMenuByRestaurant")
	public ResponseEntity<List<MenuDTO>> getMenuByRestaurant(@RequestParam("idRestaurant") Long idRestaurant) {
		return this.menuService.getMenuByRestaurant(idRestaurant);
	}
	
	@PostMapping(value = "/saveImageMenu", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<MessageResponse> saveImageMenu(@RequestParam(name = "imageFile") @Size(min = 1) MultipartFile file, @RequestPart(name = "menu") MenuDTO menu) {
		return this.menuService.saveImageMenu(file, menu);
	}
	
}