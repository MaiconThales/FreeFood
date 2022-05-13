package com.freefood.project.service;

import org.springframework.http.ResponseEntity;

import com.freefood.project.payload.request.LogOutRequest;
import com.freefood.project.payload.request.LoginRequest;
import com.freefood.project.payload.request.SignupRequest;
import com.freefood.project.payload.request.TokenRefreshRequest;
import com.freefood.project.payload.response.JwtResponse;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.payload.response.TokenRefreshResponse;

public interface LoginService {
	
JwtResponse authenticateUser(LoginRequest loginRequest);
	
	ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest);
	
	ResponseEntity<TokenRefreshResponse> refreshtoken(TokenRefreshRequest request);
	
	ResponseEntity<MessageResponse> logoutUser(LogOutRequest logOutRequest);

	ResponseEntity<Boolean> isUserLogger();
	
}
