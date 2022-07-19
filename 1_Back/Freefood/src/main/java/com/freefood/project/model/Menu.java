package com.freefood.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMenu;
	
	@ManyToOne
    @JoinColumn(name="idRestaurant", nullable=false)
	private Restaurant restaurant;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(name = "picByte", nullable = true, length = 1000)
	private byte[] picByte;
	
	public Menu() {
		
	}
	
	public Menu(Long idMenu, Restaurant restaurant, String name, String description, byte[] picByte) {
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
