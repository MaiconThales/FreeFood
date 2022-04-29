package com.freefood.project.model;

import com.freefood.project.dto.UserAuthDto;

public class AuthToken {
	
	private String token;
	private UserAuthDto user;
	
	public AuthToken() {
		
	}

	public AuthToken(String token) {
		super();
		this.token = token;
	}
	
	public AuthToken(String token, UserAuthDto user) {
		super();
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserAuthDto getUser() {
		return user;
	}

	public void setUser(UserAuthDto user) {
		this.user = user;
	}

}
