package com.gta.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 승인 처리 요청 DTO
 */
@Getter
@Setter
public class UserApprovalRequest {
	private Long userId;
    private String status;
}
