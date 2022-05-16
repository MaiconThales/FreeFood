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

import com.freefood.project.dto.AddressDTO;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.service.AddressService;

@RestController
@RequestMapping("/address")
@CrossOrigin(origins = "*")
public class AddressController {
	
	private final AddressService addressService;

	@Autowired
	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}
	
	@GetMapping("/byUser")
	public ResponseEntity<List<AddressDTO>> addressByUser(@RequestParam("idUser") Long idUser) {
		return this.addressService.addressByUser(idUser);
	}
	
	@PostMapping("/createAddress")
	public ResponseEntity<MessageResponse> createMenu(@RequestBody AddressDTO address) {
		return this.addressService.saveAddress(address);
	}
	
	@PutMapping("/updateAddress")
	public ResponseEntity<MessageResponse> updateMenu(@RequestBody AddressDTO address) {
		return this.addressService.updateMenu(address);
	}
	
	@DeleteMapping("/deleteAddress")
	public ResponseEntity<MessageResponse> deleteAddress(@RequestParam("idAddress") Long idAddress) {
		return this.addressService.deleteAddress(idAddress);
	}

}
