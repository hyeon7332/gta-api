package com.gta.service;

import java.util.List;

import com.gta.dto.LoginRequest;
import com.gta.dto.LoginResponse;
import com.gta.dto.PendingUserResponse;
import com.gta.dto.SignupRequest;
import com.gta.dto.UserApprovalRequest;

/**
 * 인증 관련 서비스
 */
public interface AuthService {
	void signup(SignupRequest request);
	
	LoginResponse login(LoginRequest request);
	
	void approve(UserApprovalRequest request);
	
	List<PendingUserResponse> getPendingUsers();
}
