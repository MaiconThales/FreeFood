package com.freefood.project.dto;

import java.util.Set;

import com.freefood.project.model.User;

public class RestaurantDTO {
	
	private Long id;
	private String name;
	private String address;
	private Set<User> users;
	
	public RestaurantDTO() {
		
	}
	
	public RestaurantDTO(Long id, String name, String address, Set<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.users = users;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
