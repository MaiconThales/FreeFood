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
	
	@Column(nullable = true)
	private String linkImage;
	
	@Column(nullable = false)
	private String description;
	
	public Menu() {
		
	}
	
	public Menu(Long idMenu, Restaurant restaurant, String name, String linkImage, String description) {
		super();
		this.idMenu = idMenu;
		this.restaurant = restaurant;
		this.name = name;
		this.linkImage = linkImage;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
