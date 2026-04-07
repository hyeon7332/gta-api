package com.gta.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gta.dto.LoginRequest;
import com.gta.dto.LoginResponse;
import com.gta.dto.PendingUserResponse;
import com.gta.dto.SignupRequest;
import com.gta.dto.UserApprovalRequest;
import com.gta.exception.ForbiddenException;
import com.gta.exception.UnauthorizedException;
import com.gta.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
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
	 * 회원가입
	 */
	@PostMapping("/signup")
	public String signup(@RequestBody SignupRequest request) {
		authService.signup(request);
		return "회원가입 성공 (승인 대기 상태입니다)";
	}
	
	/**
	 * 로그인
	 */
	@PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
		return authService.login(request);
    }
	
	/**
	 * 관리자 승인 처리
	 */
	@PostMapping("/approve")
	public String approve(
			HttpServletRequest request,
			@RequestBody UserApprovalRequest req) {
		
		String role = (String) request.getAttribute("role");
		
		if (role == null) {
	        throw new UnauthorizedException("로그인이 필요합니다.");
	    }

	    if (!"ADMIN".equals(role)) {
	        throw new ForbiddenException("관리자만 접근 가능합니다.");
	    }
		
		authService.approve(req);
		return "승인 처리 요청 완료";
	}
	
	/**
     * 승인 대기 회원 목록 조회
     */
    @GetMapping("/pending-users")
    public List<PendingUserResponse> getPendingUsers() {
        return authService.getPendingUsers();
    }
}
