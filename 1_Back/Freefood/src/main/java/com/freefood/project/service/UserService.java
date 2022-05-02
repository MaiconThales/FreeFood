package com.freefood.project.service;

import java.util.List;

import com.freefood.project.model.User;

public interface UserService {
	
	/**
	 * Retorna o User pelo ID passado.
	 * 
	 * @param id do User
	 * @return Retorna o menu de acordo com o ID passado no parametro.
	 * */
	User findById(Long idUser);
	
	/**
	 * Retorna todos os registros do banco.
	 * 
	 * @return Retorna todos os dados do Banco.
	 * */
	List<User> findAll();
	
	/**
	 * Função para fazer o update do objeto.
	 * 
	 * @param user é o objeto que se deseja fazer o update
	 * @return Com a ação bem sucedida o spring vai retornar o objeto que sofreu o update
	 * */
	User updateUser(User user);
	
	/**
	 * Função para deletar o objeto.
	 * 
	 * @param idUser o ID do objeto que se deseja deletar
	 * */
	void deleteUser(Long idUser);
	
	User save(User user);
	
}
