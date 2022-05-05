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

import com.freefood.project.dto.MenuDTO;
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
	public ResponseEntity<List<MenuDTO>> getAll() {
		List<MenuDTO> result = null;
		try {
			result = this.menuService.findAll().stream().map(m -> modelMapper.map(m, MenuDTO.class)).collect(Collectors.toList());
			
			if(result.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findId")
	public ResponseEntity<MenuDTO> getFindById(@RequestParam Long idMenu) {
		MenuDTO resultDto = null;
		try {
			Menu result = this.menuService.findById(idMenu);
			resultDto = modelMapper.map(result, MenuDTO.class);
			
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
	public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menu) {
		MenuDTO resultDto = null;
		try {
			Menu result = this.menuService.saveMenu(modelMapper.map(menu, Menu.class));
			resultDto = modelMapper.map(result, MenuDTO.class);
			
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
	public ResponseEntity<MenuDTO> updateMenu(@RequestBody MenuDTO menu) {
		MenuDTO resultDto = null;
		try {
			resultDto = modelMapper.map(this.menuService.updateMenu(modelMapper.map(menu, Menu.class)), MenuDTO.class);
			return new ResponseEntity<>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteMenu/{idMenu}")
	public ResponseEntity<MenuDTO> deleteMenu(@PathVariable("idMenu") long idMenu) {
		try {
			this.menuService.deleteMenu(idMenu);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
