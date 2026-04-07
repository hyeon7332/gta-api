package com.gta.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 승인 대기 회원 목록 응답 DTO
 */
@Getter
@Setter
public class PendingUserResponse {
	private Long userId;
    private String loginId;
    private String nickname;
    private String role;
    private String status;
    private String useYn;
    private LocalDateTime createdAt;
}
