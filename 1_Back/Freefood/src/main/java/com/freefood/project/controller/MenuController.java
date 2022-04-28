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

import com.freefood.project.dto.MenuDto;
import com.freefood.project.model.Menu;
import com.freefood.project.service.MenuService;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/all")
	public ResponseEntity<List<MenuDto>> getAll() {
		List<MenuDto> result = null;
		try {
			result = this.menuService.findAll().stream().map(m -> modelMapper.map(m, MenuDto.class)).collect(Collectors.toList());
			
			if(result.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findId")
	public ResponseEntity<MenuDto> getFindById(@RequestParam Long idMenu) {
		MenuDto resultDto = null;
		try {
			Menu result = this.menuService.findById(idMenu);
			resultDto = modelMapper.map(result, MenuDto.class);
			
			if(resultDto != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/createMenu")
	public ResponseEntity<MenuDto> createMenu(@RequestBody MenuDto menu) {
		MenuDto resultDto = null;
		try {
			Menu result = this.menuService.saveMenu(modelMapper.map(menu, Menu.class));
			resultDto = modelMapper.map(result, MenuDto.class);
			
			if(result != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateMenu")
	public ResponseEntity<MenuDto> updateMenu(@RequestBody MenuDto menu) {
		MenuDto resultDto = null;
		try {
			resultDto = modelMapper.map(this.menuService.updateMenu(modelMapper.map(menu, Menu.class)), MenuDto.class);
			return new ResponseEntity<>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteMenu/{idMenu}")
	public ResponseEntity<MenuDto> deleteMenu(@PathVariable("idMenu") long idMenu) {
		try {
			this.menuService.deleteMenu(idMenu);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
