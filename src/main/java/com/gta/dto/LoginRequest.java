package com.gta.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 요청 DTO
 */
@Getter
@Setter
public class LoginRequest {
    private String loginId;
    private String password;
}
