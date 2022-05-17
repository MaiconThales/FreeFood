package com.freefood.project.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.freefood.project.dto.RestaurantDTO;
import com.freefood.project.payload.response.MessageResponse;

public interface RestaurantService {

	/**
	 * Retorna a empresa pelo ID passado.
	 * 
	 * @param id do Restaurante
	 * @return Retorna o restaurante de acordo com o ID passado no parametro.
	 * */
	ResponseEntity<RestaurantDTO> findById(Long idRestaurant);
	
	/**
	 * Função para salvar o objeto.
	 * 
	 * @param restaurant é o objeto que se deseja salvar
	 * @return Com a ação bem sucedida o spring vai retornar o objeto cadastrado
	 * */
	ResponseEntity<MessageResponse> saveRestaurant(RestaurantDTO restaurant);
	
	/**
	 * Função para fazer o update do objeto.
	 * 
	 * @param restaurant é o objeto que se deseja fazer o update
	 * @return Com a ação bem sucedida o spring vai retornar o objeto que sofreu o update
	 * */
	ResponseEntity<MessageResponse> updateRestaurant(RestaurantDTO restaurant, Long idUser);
	
	/**
	 * Função para deletar o objeto.
	 * 
	 * @param idRestaurant o ID do objeto que se deseja deletar
	 * */
	ResponseEntity<MessageResponse> deleteRestaurant(Long idRestaurant, Long idUser);
	
	ResponseEntity<List<RestaurantDTO>> findRestaurantByUserId(Long idUser);
	
	ResponseEntity<MessageResponse> liberateRestaurant(RestaurantDTO restaurant, String username, Long idUser);
	
	boolean verifyAccessRestaurant(Long idUser, Long idRestaurant);
	
	ResponseEntity<List<RestaurantDTO>> getAllRestaurant();
	
}
