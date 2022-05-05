package com.freefood.project.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freefood.project.dto.UserDTO;
import com.freefood.project.model.User;
import com.freefood.project.payload.request.LogOutRequest;
import com.freefood.project.payload.request.LoginRequest;
import com.freefood.project.payload.request.SignupRequest;
import com.freefood.project.payload.request.TokenRefreshRequest;
import com.freefood.project.payload.response.JwtResponse;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.payload.response.TokenRefreshResponse;
import com.freefood.project.service.LoginService;
import com.freefood.project.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

	private final UserService userService;
	private final ModelMapper modelMapper;
	private final LoginService loginService;

	@Autowired
	public UserController(UserService userService, ModelMapper modelMapper, LoginService loginService) {
		this.userService = userService;
		this.modelMapper = modelMapper;
		this.loginService = loginService;
	}

	@GetMapping("/findId")
	public ResponseEntity<UserDTO> getFindById(@RequestParam Long idUser) {
		UserDTO resultDto = null;
		try {
			resultDto = modelMapper.map(this.userService.findById(idUser), UserDTO.class);

			if (resultDto != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateUser")
	public ResponseEntity<MessageResponse> updateUser(@RequestBody UserDTO user) {
		return this.userService.updateUser(modelMapper.map(user, User.class));
	}

	@PostMapping("/auth/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(this.loginService.authenticateUser(loginRequest));
	}

	@PostMapping("/auth/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return this.loginService.registerUser(signUpRequest);
	}

	@PostMapping("/auth/refreshtoken")
	public ResponseEntity<TokenRefreshResponse> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		return this.loginService.refreshtoken(request);
	}

	@PostMapping("/auth/logout")
	public ResponseEntity<MessageResponse> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
		return this.loginService.logoutUser(logOutRequest);
	}

}
