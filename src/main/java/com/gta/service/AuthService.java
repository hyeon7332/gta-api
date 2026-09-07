package com.gta.service;

import com.gta.dto.LoginRequest;
import com.gta.dto.LoginResponse;

/**
 * 인증 관련 서비스
 */
public interface AuthService {
	
	LoginResponse login(LoginRequest request);
}
