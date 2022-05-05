package com.freefood.project.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.freefood.project.model.RefreshToken;

public interface RefreshTokenService {
	
	public Optional<RefreshToken> findByToken(String token);

	public RefreshToken createRefreshToken(Long userId);

	public RefreshToken verifyExpiration(RefreshToken token);

	@Transactional
	public int deleteByUserId(Long userId);

}
