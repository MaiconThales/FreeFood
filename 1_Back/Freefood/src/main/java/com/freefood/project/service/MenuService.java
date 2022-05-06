package com.freefood.project.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.freefood.project.dto.MenuDTO;
import com.freefood.project.payload.response.MessageResponse;

public interface MenuService {
	
	/**
	 * Função para salvar o objeto.
	 * 
	 * @param menu é o objeto que se deseja salvar
	 * @return Com a ação bem sucedida o spring vai retornar o objeto cadastrado
	 * */
	ResponseEntity<MessageResponse> saveMenu(MenuDTO menu);
	
	/**
	 * Função para fazer o update do objeto.
	 * 
	 * @param menu é o objeto que se deseja fazer o update
	 * @return Com a ação bem sucedida o spring vai retornar o objeto que sofreu o update
	 * */
	ResponseEntity<MessageResponse> updateMenu(MenuDTO menu);
	
	/**
	 * Função para deletar o objeto.
	 * 
	 * @param idMenu o ID do objeto que se deseja deletar
	 * */
	ResponseEntity<MessageResponse> deleteMenu(Long idMenu, Long idUser, Long idRestaurant);
	
	ResponseEntity<List<MenuDTO>> getMenu(Long idRestaurant, Long idUser);
	
}
