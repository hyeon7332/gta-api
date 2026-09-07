package com.gta.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.LoginRequest;
import com.gta.dto.LoginResponse;
import com.gta.service.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * 인증 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	/**
	 * 로그인
	 */
	@PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
		return authService.login(request);
    }
}
