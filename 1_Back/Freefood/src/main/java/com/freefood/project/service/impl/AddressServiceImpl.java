package com.freefood.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.freefood.project.dto.AddressDTO;
import com.freefood.project.model.Address;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.repository.AddressRepository;
import com.freefood.project.service.AddressService;

@Service(value = "addressService")
public class AddressServiceImpl implements AddressService {
	
	private static final String SERVER_ERROR = "GLOBAL_WORD.WORD_MSG_SERVER_ERROR";
	
	private final AddressRepository addressRepository;
	private final ModelMapper modelMapper;
	
	@Autowired
	public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
		this.addressRepository = addressRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ResponseEntity<List<AddressDTO>> addressByUser(Long idUser) {
		List<AddressDTO> resultFinal = null;
		try {
			List<Address> resultBank = this.addressRepository.addressByUser(idUser);
			if(!resultBank.isEmpty()) {
				resultFinal = resultBank.stream().map(m -> modelMapper.map(m, AddressDTO.class)).collect(Collectors.toList());
				return new ResponseEntity<>(resultFinal, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(resultFinal, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(resultFinal, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<MessageResponse> saveAddress(AddressDTO address) {
		AddressDTO resultDto = null;
		try {
			this.updateIsDefault(address);
			Address result = this.addressRepository.save(modelMapper.map(address, Address.class));
			resultDto = modelMapper.map(result, AddressDTO.class);
			
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
	public ResponseEntity<MessageResponse> updateMenu(AddressDTO address) {
		AddressDTO resultDto = null;
		try {
			this.updateIsDefault(address);
			resultDto = modelMapper.map(this.addressRepository.save(modelMapper.map(address, Address.class)), AddressDTO.class);
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
	public ResponseEntity<MessageResponse> deleteAddress(Long idAddress) {
		try {
			this.addressRepository.deleteById(idAddress);
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_REMOVE"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void updateIsDefault(AddressDTO address) {
		if(Boolean.TRUE.equals(address.getIsDefault())) {
			this.addressRepository.updateIsDefault(address.getUser().getId());
		}
	}
	
}
