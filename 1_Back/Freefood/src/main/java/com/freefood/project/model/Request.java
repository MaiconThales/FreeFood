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
@Table(name = "request")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRequest;
	
	@ManyToOne
    @JoinColumn(name="idRestaurant", nullable=false)
	private Restaurant restaurant;
	
	@ManyToOne
    @JoinColumn(name="idMenu", nullable=false)
	private Menu menu;

	@ManyToOne
    @JoinColumn(name="idUser", nullable=false)
	private User user;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Long amount;
	
	@Column(nullable = false)
	private String deliveryAddress;
	
	@Column(nullable = true)
	private String observation;
	
	public Request() {
		
	}
	
	public Request(Long idRequest, Restaurant restaurant, Menu menu, User user, String name, Long amount,
			String deliveryAddress, String observation) {
		super();
		this.idRequest = idRequest;
		this.restaurant = restaurant;
		this.menu = menu;
		this.user = user;
		this.name = name;
		this.amount = amount;
		this.deliveryAddress = deliveryAddress;
		this.observation = observation;
	}

	public Long getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(Long idRequest) {
		this.idRequest = idRequest;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	
}
