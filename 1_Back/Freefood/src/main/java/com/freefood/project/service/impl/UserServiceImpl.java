package com.freefood.project.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.freefood.project.model.User;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.repository.UserRepository;
import com.freefood.project.service.UserService;
import com.freefood.project.service.UtilsService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final UtilsService utils;
	private final PasswordEncoder encoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, UtilsService utils, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.utils = utils;
		this.encoder = encoder;
	}

	@Override
	public User findById(Long idUser) {
		Optional<User> result = this.userRepository.findById(idUser);
		if(result.isPresent()) {
			result.get().setPassword("");
			return result.get();
		}
		return null;
	}

	@Override
	public ResponseEntity<MessageResponse> updateUser(User user) {
		try {
			if (this.utils.verifyUserLogged(user.getUsername())) {
				if (!user.getPassword().isBlank()) {
					user.setPassword(encoder.encode(user.getPassword()));
				} else {
					String password = this.userRepository.getPasswordByUserId(user.getId());
					user.setPassword(password);
				}
				this.userRepository.save(user);
				return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_SUCCESS"), HttpStatus.OK);
			}
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_FORBIDDEN"), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.WORD_MSG_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public User save(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public String getLanguageUser(Long idUser) {
		return this.userRepository.getLanguageUser(idUser);
	}

	@Override
	public User findByUsername(String username) {
		Optional<User> result = this.userRepository.findByUsername(username);
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	
}
