package com.freefood.project.service;

import java.util.List;

import com.freefood.project.model.Menu;

public interface MenuService {

	/**
	 * Retorna o menu pelo ID passado.
	 * 
	 * @param id do Menu
	 * @return Retorna o menu de acordo com o ID passado no parametro.
	 * */
	Menu findById(Long idMenu);
	
	/**
	 * Retorna todos os registros do banco.
	 * 
	 * @return Retorna todos os dados do Banco.
	 * */
	List<Menu> findAll();
	
	/**
	 * Função para salvar o objeto.
	 * 
	 * @param menu é o objeto que se deseja salvar
	 * @return Com a ação bem sucedida o spring vai retornar o objeto cadastrado
	 * */
	Menu saveMenu(Menu menu);
	
	/**
	 * Função para fazer o update do objeto.
	 * 
	 * @param menu é o objeto que se deseja fazer o update
	 * @return Com a ação bem sucedida o spring vai retornar o objeto que sofreu o update
	 * */
	Menu updateMenu(Menu menu);
	
	/**
	 * Função para deletar o objeto.
	 * 
	 * @param idMenu o ID do objeto que se deseja deletar
	 * */
	void deleteMenu(Long idMenu);
	
}
