package com.freefood.project.service;

import java.util.List;
import java.util.Optional;

import com.freefood.project.model.Request;

public interface RequestService {
	
	/**
	 * Retorna o request pelo ID passado.
	 * 
	 * @param id do Request
	 * @return Retorna o menu de acordo com o ID passado no parametro.
	 * */
	Optional<Request> findById(Long idRequest);
	
	/**
	 * Retorna todos os registros do banco.
	 * 
	 * @return Retorna todos os dados do Banco.
	 * */
	List<Request> findAll();
	
	/**
	 * Função para salvar o objeto.
	 * 
	 * @param request é o objeto que se deseja salvar
	 * @return Com a ação bem sucedida o spring vai retornar o objeto cadastrado
	 * */
	Request saveRequest(Request request);
	
	/**
	 * Função para fazer o update do objeto.
	 * 
	 * @param request é o objeto que se deseja fazer o update
	 * @return Com a ação bem sucedida o spring vai retornar o objeto que sofreu o update
	 * */
	Request updateRequest(Request request);
	
	/**
	 * Função para deletar o objeto.
	 * 
	 * @param idRequest o ID do objeto que se deseja deletar
	 * */
	void deleteRequest(Long idRequest);

}
