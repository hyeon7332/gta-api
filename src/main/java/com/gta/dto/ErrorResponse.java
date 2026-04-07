package com.gta.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 공통 에러 응답 DTO
 */
@Getter
@Setter
public class ErrorResponse {
	private int status;
    private String message;
}
