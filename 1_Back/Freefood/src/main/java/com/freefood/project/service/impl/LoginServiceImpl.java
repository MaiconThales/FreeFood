package com.freefood.project.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.freefood.project.service.LoginService;
import com.freefood.project.service.RefreshTokenService;

@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {
	
	static final String ROLENOTFOUND = "Error: Role is not found.";
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final RefreshTokenService refreshTokenService;
	private final PasswordEncoder encoder;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public LoginServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
			RefreshTokenService refreshTokenService, PasswordEncoder encoder, RoleRepository roleRepository,
			UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.refreshTokenService = refreshTokenService;
		this.encoder = encoder;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	@Override
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String languageUser = this.userRepository.getLanguageUser(userDetails.getId());

		String jwt = jwtUtils.generateJwtToken(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		
		return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, languageUser);
	}
	
	@Override
	public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_USERNAME_ALREADY"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_EMAIL_ALREADY"), HttpStatus.BAD_REQUEST);
		}
		
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getLanguage());

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
		this.userRepository.save(user);
		
		return new ResponseEntity<>(new MessageResponse("GLOBAL_WORD.MSG_USER_CREATE_SUCCESS"), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<TokenRefreshResponse> refreshtoken(TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		Optional<User> resultUser = refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration).map(RefreshToken::getUser);
		if (resultUser.isPresent()) {
			String token = jwtUtils.generateTokenFromUsername(resultUser.get().getUsername());
			return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
		}

		throw new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!");
	}

	@Override
	public ResponseEntity<MessageResponse> logoutUser(LogOutRequest logOutRequest) {
		refreshTokenService.deleteByUserId(logOutRequest.getUserId());
		return ResponseEntity.ok(new MessageResponse("Log out successful!"));
	}

	@Override
	public ResponseEntity<Boolean> isUserLogger() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    return new ResponseEntity<>(authentication.isAuthenticated(), HttpStatus.OK);
		}
		return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
	}

	
}
