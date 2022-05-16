package com.freefood.project.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.freefood.project.dto.AddressDTO;
import com.freefood.project.payload.response.MessageResponse;

public interface AddressService {
	
	ResponseEntity<List<AddressDTO>> addressByUser(Long idUser);
	
	ResponseEntity<MessageResponse> saveAddress(AddressDTO address);
	
	ResponseEntity<MessageResponse> updateMenu(AddressDTO address);

	ResponseEntity<MessageResponse> deleteAddress(Long idAddress);
	
}
