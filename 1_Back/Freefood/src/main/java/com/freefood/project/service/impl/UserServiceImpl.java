package com.freefood.project.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freefood.project.model.User;
import com.freefood.project.repository.UserRepository;
import com.freefood.project.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

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
	public User updateUser(User user) {
		if(user.getPassword().isBlank()) {
			String password = this.userRepository.getPasswordByUserId(user.getId());
			user.setPassword(password);
		}
		return this.userRepository.save(user);
	}

	@Override
	public User save(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public String getLanguageUser(Long idUser) {
		return this.userRepository.getLanguageUser(idUser);
	}
	
}
