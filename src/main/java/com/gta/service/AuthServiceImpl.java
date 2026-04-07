package com.gta.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gta.dto.LoginRequest;
import com.gta.dto.LoginResponse;
import com.gta.dto.PendingUserResponse;
import com.gta.dto.SignupRequest;
import com.gta.dto.UserApprovalRequest;
import com.gta.entity.User;
import com.gta.exception.ForbiddenException;
import com.gta.exception.UnauthorizedException;
import com.gta.mapper.AuthMapper;
import com.gta.util.JwtUtil;

import lombok.RequiredArgsConstructor;

/**
 * 인증 관련 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final AuthMapper authMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Override
	public void signup(SignupRequest request) {
		int count = authMapper.countByLoginId(request.getLoginId());
		
		if (count > 0) {
			throw new RuntimeException("이미 사용 중인 로그인 ID입니다.");
		}
		
		request.setPassword(passwordEncoder.encode(request.getPassword()));
		authMapper.insertUser(request);
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		User user = authMapper.findByLoginId(request.getLoginId());
		
		if (user == null) {
		    throw new UnauthorizedException("아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
		    throw new UnauthorizedException("아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		
		if (!"APPROVED".equals(user.getStatus())) {
		    throw new ForbiddenException("승인 대기 중인 계정입니다.");
		}
		
		if (!"Y".equals(user.getUseYn())) {
		    throw new ForbiddenException("비활성화된 계정입니다.");
		}
		
		String token = jwtUtil.generateToken(user.getUserId(), user.getRole());
		
		LoginResponse response = new LoginResponse();
	    response.setToken(token);
	    response.setUserId(user.getUserId());
	    response.setRole(user.getRole());
	    
	    return response;
	}

	@Override
	public void approve(UserApprovalRequest request) {
		authMapper.updateUserStatus(request);
	}

	@Override
	public List<PendingUserResponse> getPendingUsers() {
		return authMapper.selectPendingUsers();
	}

}
