package com.gta.exception;

/**
 * 인증 실패 예외
 */
public class UnauthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) {
        super(message);
    }
}