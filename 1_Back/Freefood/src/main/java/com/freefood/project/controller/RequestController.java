package com.freefood.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freefood.project.dto.RequestDTO;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.service.RequestService;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "*")
public class RequestController {
	
	private RequestService requestService;
	
	@Autowired
	public RequestController(RequestService requestService) {
		this.requestService = requestService;
	}
	
	@PostMapping("/createRequest")
	public ResponseEntity<MessageResponse> createRequest(@RequestBody List<RequestDTO> request) {
		return this.requestService.saveRequest(request);
	}
	
	@GetMapping("/listRequestByUser")
	public ResponseEntity<List<RequestDTO>> getRequestByUser(@RequestParam Long idUser) {
		return this.requestService.getRequestByUser(idUser);
	}

}
