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

import com.freefood.project.dto.RequestDto;
import com.freefood.project.model.Request;
import com.freefood.project.service.RequestService;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "*")
public class RequestController {
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/all")
	public ResponseEntity<List<RequestDto>> getAll() {
		List<RequestDto> result = null;
		try {
			result = this.requestService.findAll().stream().map(r -> modelMapper.map(r, RequestDto.class)).collect(Collectors.toList());
			
			if(result.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findId")
	public ResponseEntity<RequestDto> getFindById(@RequestParam Long idRequest) {
		RequestDto resultDto = null;
		try {
			Request result = this.requestService.findById(idRequest);
			resultDto = modelMapper.map(result, RequestDto.class);
			
			if(resultDto != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/createRequest")
	public ResponseEntity<RequestDto> createRequest(@RequestBody RequestDto request) {
		RequestDto resultDto = null;
		try {
			Request requestParam = modelMapper.map(request, Request.class);
			resultDto = modelMapper.map(this.requestService.saveRequest(requestParam), RequestDto.class);
			
			if(resultDto != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateRequest")
	public ResponseEntity<RequestDto> updateRequest(@RequestBody RequestDto request) {
		RequestDto resultDto = null;
		try {
			Request requestParam = modelMapper.map(request, Request.class);
			resultDto = modelMapper.map(this.requestService.updateRequest(requestParam), RequestDto.class);
			return new ResponseEntity<>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteRequest/{idRequest}")
	public ResponseEntity<RequestDto> deleteRequest(@PathVariable("idRequest") long idRequest) {
		try {
			this.requestService.deleteRequest(idRequest);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
