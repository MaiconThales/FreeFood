package com.freefood.project.dto;

import com.freefood.project.model.Restaurant;

public class MenuDTO {
	
	private Long idMenu;
	private Restaurant restaurant;
	private String name;
	private String description;
	private byte[] picByte;
	
	public MenuDTO() {
		
	}
	
	public MenuDTO(Long idMenu, Restaurant restaurant, String name, String description, byte[] picByte) {
		super();
		this.idMenu = idMenu;
		this.restaurant = restaurant;
		this.name = name;
		this.description = description;
		this.picByte = picByte;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

}
