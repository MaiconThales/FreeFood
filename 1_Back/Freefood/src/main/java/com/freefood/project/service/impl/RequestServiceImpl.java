package com.freefood.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.freefood.project.dto.RequestDTO;
import com.freefood.project.model.Request;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.repository.RequestRepository;
import com.freefood.project.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {
	
	private static final String SERVER_ERROR = "GLOBAL_WORD.WORD_MSG_SERVER_ERROR";

	private RequestRepository requestRepository;
	private ModelMapper modelMapper;

	@Autowired
	public RequestServiceImpl(RequestRepository requestRepository, ModelMapper modelMapper) {
		this.requestRepository = requestRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ResponseEntity<MessageResponse> saveRequest(List<RequestDTO> request) {
		try {
			List<Request> requestParam = request.stream().map(r -> modelMapper.map(r, Request.class)).collect(Collectors.toList());
			for (int i = 0; i<requestParam.size(); i++) {
				this.requestRepository.save(requestParam.get(i));
			}
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_CREATE"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<List<RequestDTO>> getRequestByUser(Long idUser) {
		List<RequestDTO> resultUser = null;
		try {
			List<Request> resultSearch = this.requestRepository.getRequestByUser(idUser);
			if(!resultSearch.isEmpty()) {
				resultUser = resultSearch.stream().map(r -> modelMapper.map(r, RequestDTO.class)).collect(Collectors.toList());
				return new ResponseEntity<>(resultUser, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(resultUser, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(resultUser, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
