package com.gta.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 응답 DTO
 */
@Getter
@Setter
public class LoginResponse {
	private String token;
    private Long userId;
    private String role;
    private String nickname;
}
