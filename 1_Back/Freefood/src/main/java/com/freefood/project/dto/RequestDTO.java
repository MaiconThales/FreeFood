package com.freefood.project.dto;

import com.freefood.project.model.Address;
import com.freefood.project.model.Menu;
import com.freefood.project.model.Restaurant;
import com.freefood.project.model.User;

public class RequestDTO {

	private Long idRequest;
	private Long amount;
	private String observation;
	private Restaurant restaurant;
	private Menu menu;
	private User user;
	private Address address;
	
	public Long getIdRequest() {
		return idRequest;
	}
	public void setIdRequest(Long idRequest) {
		this.idRequest = idRequest;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
