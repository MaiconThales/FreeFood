package com.freefood.project.service.impl;

import java.util.List;
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
			return result.get();
		}
		return null;
	}

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public User updateUser(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public void deleteUser(Long idUser) {
		this.userRepository.deleteById(idUser);
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
