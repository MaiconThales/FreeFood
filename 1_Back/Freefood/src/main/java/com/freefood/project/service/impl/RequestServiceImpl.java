package com.freefood.project.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freefood.project.model.Request;
import com.freefood.project.repository.RequestRepository;
import com.freefood.project.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	private RequestRepository requestRepository;
	
	@Override
	public Request findById(Long idRequest) {
		Optional<Request> result = this.requestRepository.findById(idRequest);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public List<Request> findAll() {
		return this.requestRepository.findAll();
	}

	@Override
	public Request saveRequest(Request request) {
		return this.requestRepository.save(request);
	}

	@Override
	public Request updateRequest(Request request) {
		return this.requestRepository.save(request);
	}

	@Override
	public void deleteRequest(Long idRequest) {
		this.requestRepository.deleteById(idRequest);
	}

}
