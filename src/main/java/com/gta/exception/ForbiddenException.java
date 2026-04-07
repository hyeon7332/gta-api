package com.gta.exception;

/**
 * 권한 없음 예외
 */
public class ForbiddenException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public ForbiddenException(String message) {
        super(message);
    }
}
