package com.gta.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gta.dto.LoginRequest;
import com.gta.dto.LoginResponse;
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
	public LoginResponse login(LoginRequest request) {
		User user = authMapper.findByLoginId(request.getLoginId());
		
		if (user == null) {
		    throw new UnauthorizedException("아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
		    throw new UnauthorizedException("아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		
		if (!"Y".equals(user.getUseYn())) {
		    throw new ForbiddenException("비활성화된 계정입니다.");
		}
		
		String token = jwtUtil.generateToken(user.getUserId(), user.getRole());
		
		LoginResponse response = new LoginResponse();
	    response.setToken(token);
	    response.setUserId(user.getUserId());
	    response.setRole(user.getRole());
	    response.setNickname(user.getNickname());
	    
	    return response;
	}
}
