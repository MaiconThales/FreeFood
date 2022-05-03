package com.freefood.project.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freefood.project.dto.UserDto;
import com.freefood.project.exception.TokenRefreshException;
import com.freefood.project.model.ERole;
import com.freefood.project.model.RefreshToken;
import com.freefood.project.model.Role;
import com.freefood.project.model.User;
import com.freefood.project.payload.request.LogOutRequest;
import com.freefood.project.payload.request.LoginRequest;
import com.freefood.project.payload.request.SignupRequest;
import com.freefood.project.payload.request.TokenRefreshRequest;
import com.freefood.project.payload.response.JwtResponse;
import com.freefood.project.payload.response.MessageResponse;
import com.freefood.project.payload.response.TokenRefreshResponse;
import com.freefood.project.repository.RoleRepository;
import com.freefood.project.repository.UserRepository;
import com.freefood.project.security.jwt.JwtUtils;
import com.freefood.project.security.services.RefreshTokenService;
import com.freefood.project.security.services.UserDetailsImpl;
import com.freefood.project.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

	static final String ROLENOTFOUND = "Error: Role is not found.";

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	@GetMapping("/all")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDto>> getAll() {
		List<UserDto> result = null;
		try {
			result = this.userService.findAll().stream().map(u -> modelMapper.map(u, UserDto.class))
					.collect(Collectors.toList());

			if (result.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findId")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDto> getFindById(@RequestParam Long idUser) {
		UserDto resultDto = null;
		try {
			resultDto = modelMapper.map(this.userService.findById(idUser), UserDto.class);

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
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
		// TODO
		/*
		 * Implementar algo que faça o usuário atualizar apenas ele próprio
		 */
		UserDto resultDto = null;
		try {
			User userParam = modelMapper.map(user, User.class);
			resultDto = modelMapper.map(this.userService.updateUser(userParam), UserDto.class);
			return new ResponseEntity<>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteUser/{idUser}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDto> deleteUser(@PathVariable("idUser") long idUser) {
		try {
			this.userService.deleteUser(idUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/auth/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String languageUser = this.userService.getLanguageUser(userDetails.getId());

		String jwt = jwtUtils.generateJwtToken(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
				userDetails.getUsername(), userDetails.getEmail(), roles, languageUser));
	}

	@PostMapping("/auth/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getLanguage());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException(ROLENOTFOUND));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException(ROLENOTFOUND));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException(ROLENOTFOUND));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException(ROLENOTFOUND));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userService.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/auth/refreshtoken")
	public ResponseEntity<TokenRefreshResponse> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		/**
		 * TODO Futuramente tem que colocar uma maneira de atualizar o horário, pois
		 * atualmente só faz um reflesh
		 */
		String requestRefreshToken = request.getRefreshToken();

		Optional<User> resultUser = refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration).map(RefreshToken::getUser);
		if (resultUser.isPresent()) {
			String token = jwtUtils.generateTokenFromUsername(resultUser.get().getUsername());
			return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
		}

		throw new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!");
	}

	@PostMapping("/logout")
	public ResponseEntity<MessageResponse> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
		refreshTokenService.deleteByUserId(logOutRequest.getUserId());
		return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	}

}
