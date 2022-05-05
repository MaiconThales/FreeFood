package com.freefood.project.dto;

import com.freefood.project.model.Restaurant;

public class MenuDTO {
	
	private Long idMenu;
	private Restaurant restaurant;
	private String name;
	private String linkImage;
	
	public MenuDTO() {
		
	}
	
	public MenuDTO(Long idMenu, Restaurant restaurant, String name, String linkImage) {
		super();
		this.idMenu = idMenu;
		this.restaurant = restaurant;
		this.name = name;
		this.linkImage = linkImage;
	}
	
	public Long getIdMenu() {
		return idMenu;
	}
	public void setIdMenu(Long idMenu) {
		this.idMenu = idMenu;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkImage() {
		return linkImage;
	}
	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}

}