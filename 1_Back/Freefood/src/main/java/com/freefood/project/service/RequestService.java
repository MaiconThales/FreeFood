package com.freefood.project.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.freefood.project.dto.RequestDTO;
import com.freefood.project.payload.response.MessageResponse;

public interface RequestService {
	
	/**
	 * Função para salvar o objeto.
	 * 
	 * @param request é o objeto que se deseja salvar
	 * @return Com a ação bem sucedida o spring vai retornar o objeto cadastrado
	 * */
	ResponseEntity<MessageResponse> saveRequest(List<RequestDTO> request);
	
	ResponseEntity<List<RequestDTO>> getRequestByUser(Long idUser);

}
