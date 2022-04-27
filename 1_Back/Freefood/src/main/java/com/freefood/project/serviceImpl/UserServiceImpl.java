package com.freefood.project.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.freefood.project.dao.UserDao;
import com.freefood.project.model.Role;
import com.freefood.project.model.User;
import com.freefood.project.model.UserDto;
import com.freefood.project.repository.UserRepository;
import com.freefood.project.service.RoleService;
import com.freefood.project.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	public Optional<User> findById(Long idUser) {
		return this.userRepository.findById(idUser);
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
	public User save(UserDto user) {
		User nUser = user.getUserFromDto();
		nUser.setPassword(bcryptEncoder.encode(user.getPassword()));

		Role role = roleService.findByName("USER");
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(role);

		/*if (nUser.getEmail().split("@")[1].equals("admin.edu")) {
			role = roleService.findByName("ADMIN");
			roleSet.add(role);
		}*/

		nUser.setRoles(roleSet);
		return userDao.save(nUser);
	}

	@Override
	public User findOne(String username) {
		return userDao.findByUsername(username);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

}
