package com.freefood.project.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.freefood.project.exception.TokenRefreshException;
import com.freefood.project.model.RefreshToken;
import com.freefood.project.model.User;
import com.freefood.project.repository.RefreshTokenRepository;
import com.freefood.project.repository.UserRepository;
import com.freefood.project.service.RefreshTokenService;

@Service(value = "refreshTokenServiceImpl")
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Value("${freefood.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	private final RefreshTokenRepository refreshTokenRepository;

	private final UserRepository userRepository;

	@Autowired
	public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public RefreshToken createRefreshToken(Long userId) {
		RefreshToken refreshToken = new RefreshToken();
		User u = userRepository.findById(userId).orElseGet(User::new);

		refreshToken.setUser(u);
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	@Override
	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	@Override
	public int deleteByUserId(Long userId) {
		User u = userRepository.findById(userId).orElseGet(User::new);
		return refreshTokenRepository.deleteByUser(u);
	}

}
