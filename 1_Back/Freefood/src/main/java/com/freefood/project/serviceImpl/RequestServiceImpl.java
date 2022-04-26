package com.freefood.project.serviceImpl;

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
	public Optional<Request> findById(Long idRequest) {
		return this.requestRepository.findById(idRequest);
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
