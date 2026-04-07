package com.gta.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 엔티티
 */
@Getter
@Setter
public class User {
	private Long userId;
    private String loginId;
    private String password;
    private String nickname;
    private String role;
    private String status;
    private String useYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
