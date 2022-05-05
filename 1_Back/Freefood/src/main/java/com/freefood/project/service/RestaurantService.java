package com.freefood.project.service;

import java.util.List;

import com.freefood.project.model.Restaurant;

public interface RestaurantService {

	/**
	 * Retorna a empresa pelo ID passado.
	 * 
	 * @param id do Restaurante
	 * @return Retorna o restaurante de acordo com o ID passado no parametro.
	 * */
	Restaurant findById(Long idRestaurant);
	
	/**
	 * Função para salvar o objeto.
	 * 
	 * @param restaurant é o objeto que se deseja salvar
	 * @return Com a ação bem sucedida o spring vai retornar o objeto cadastrado
	 * */
	Restaurant saveRestaurant(Restaurant restaurant);
	
	/**
	 * Função para fazer o update do objeto.
	 * 
	 * @param restaurant é o objeto que se deseja fazer o update
	 * @return Com a ação bem sucedida o spring vai retornar o objeto que sofreu o update
	 * */
	Restaurant updateRestaurant(Restaurant restaurant);
	
	/**
	 * Função para deletar o objeto.
	 * 
	 * @param idRestaurant o ID do objeto que se deseja deletar
	 * */
	void deleteRestaurant(Long idRestaurant);
	
	List<Restaurant> findRestaurantByUserId(Long idUser);
	
	boolean verifyAccessRestaurnt(Long idUser, Long idRestaurant);
	
}
