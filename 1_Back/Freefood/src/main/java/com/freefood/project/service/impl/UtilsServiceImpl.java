package com.freefood.project.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.freefood.project.service.UtilsService;

@Service(value = "utilsService")
public class UtilsServiceImpl implements UtilsService {
	
	@Override
	public String getUserLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
		    return ((UserDetails)principal).getUsername();
		}
		
		return principal.toString();
	}
	
	@Override
	public boolean verifyUserLogged(String username) {
		return this.getUserLogged().equals(username);
	}

}
