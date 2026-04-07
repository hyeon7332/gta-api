package com.gta.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 요청 DTO
 */
@Getter
@Setter
public class SignupRequest {
	private String loginId;
    private String password;
    private String nickname;
}
