package com.freefood.project.dto;

import com.freefood.project.model.Address;
import com.freefood.project.model.Menu;

public class RequestDTO {

	private Long idRequest;
	private Long amount;
	private String observation;
	private Menu menu;
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
