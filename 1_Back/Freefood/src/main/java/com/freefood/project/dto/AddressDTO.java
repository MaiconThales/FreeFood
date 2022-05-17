package com.freefood.project.dto;

import com.freefood.project.model.User;

public class AddressDTO {

	private Long id;
	private String street;
	private String district;
	private Long number;
	private String complement;
	private Boolean isDefault;
	private User user;
	
	public AddressDTO() {
	}

	public AddressDTO(Long id, String street, String district, Long number, String complement, Boolean isDefault, User user) {
		this.id = id;
		this.street = street;
		this.district = district;
		this.number = number;
		this.complement = complement;
		this.user = user;
		this.isDefault = isDefault; 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
}
